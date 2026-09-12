import { Coordinate } from './Coordinate';
import type { LevelInitializer } from './Level';
import { BossRoom, Room } from './rooms/Room';
import type { ShopInitializer } from './rooms/shop/Shop';
import { ShopRoom } from './rooms/shop/Shop';
import type { Pool } from '../pools/Pool';
import type { Creature } from '../creatures/Creature';
import type { Item } from '../items/Item';
import { generateRoomDecorations, generateRoomVines } from './roomDecor';
import { applyEliteTags, pickEliteTags } from './eliteModifiers';
import type { RoomRole } from './roomMeta';
import { getFloorConfig } from './floorConfig';
import { ConsumableFactory } from '../items/consumables/ConsumableFactory';
import { AccessoryFactory } from '../items/accessories/AccessoryFactory';
import { WeaponFactory } from '../items/weapons/WeaponFactory';

/**
 * Grow topology, then assign roles for a tension wave:
 * early combat cluster → elite mini-peak → shop/rest breather → ramp → boss.
 * Optional locked treasure / trap / false-danger rooms teach map prediction.
 */
export class DefaultLevelInitializer implements LevelInitializer {
  private numRooms: number;
  private roomLength: number;
  private creaturePool: Pool<Creature>;
  private roomItemPool: Pool<Item>;
  private bossPool: Pool<Creature>;
  private shopInitializer: ShopInitializer;
  private floorIndex: number;

  private rooms: (Room | null)[][] = [];
  private validPositions = new Map<string, Coordinate>();
  private startRoom = new Coordinate(0, 0);
  private shopPos: Coordinate | null = null;

  constructor(
    numRooms: number,
    roomLength: number,
    creaturePool: Pool<Creature>,
    roomItemPool: Pool<Item>,
    bossPool: Pool<Creature>,
    shopInitializer: ShopInitializer,
    floorIndex = 0,
  ) {
    this.numRooms = numRooms;
    this.roomLength = roomLength;
    this.creaturePool = creaturePool;
    this.roomItemPool = roomItemPool;
    this.bossPool = bossPool;
    this.shopInitializer = shopInitializer;
    this.floorIndex = floorIndex;
  }

  getNumRooms(): number {
    return this.numRooms;
  }

  getStartRoom(): Coordinate {
    return this.startRoom;
  }

  initializeLevel(): (Room | null)[][] {
    this.rooms = Array.from({ length: this.roomLength }, () =>
      Array.from({ length: this.roomLength }, () => null),
    );
    this.validPositions.clear();
    this.shopPos = null;

    const seed = new Coordinate(
      Math.floor(Math.random() * this.roomLength),
      Math.floor(Math.random() * this.roomLength),
    );
    this.validPositions.set(seed.toKey(), seed);

    const start = new Room();
    start.setRole('start');
    this.startRoom = this.addRoom(start);

    // Grow empty shells — content assigned after topology + pacing roles.
    for (let i = 0; i < this.numRooms - 1; i++) {
      this.addRoom(new Room());
    }

    this.shopPos = this.pickMidDistanceFrontier();
    this.addRoomAt(new ShopRoom(this.shopInitializer), this.shopPos);

    const boss = new BossRoom();
    boss.setCreature(this.bossPool.createNew());
    boss.setRole('boss');
    this.addRoomAt(boss, this.farthestLeafFromStart());

    this.assignDepthsAndRoles();
    this.fillRoomContents();

    return this.rooms;
  }

  private assignDepthsAndRoles(): void {
    const placed = this.allRoomPositions();
    const dist = this.bfsDistances(this.startRoom);
    let maxDist = 1;
    for (const p of placed) {
      maxDist = Math.max(maxDist, dist.get(p.toKey()) ?? 0);
    }

    const assignable: Coordinate[] = [];
    for (const p of placed) {
      const room = this.rooms[p.getRow()][p.getCol()]!;
      const d = dist.get(p.toKey()) ?? 0;
      room.setDepth(d);
      if (room.getRole() === 'start' || room instanceof ShopRoom || room instanceof BossRoom) {
        continue;
      }
      assignable.push(p);
    }

    assignable.sort((a, b) => (dist.get(a.toKey()) ?? 0) - (dist.get(b.toKey()) ?? 0));

    const n = assignable.length;
    const eliteIdx = Math.min(n - 1, Math.max(1, Math.floor(n * 0.35)));
    const lateEliteIdx = Math.min(n - 1, Math.max(eliteIdx + 1, Math.floor(n * 0.75)));

    // Prefer a neighbor of the shop for true rest (breather).
    const floorCfg = getFloorConfig(this.floorIndex);
    let sanctuaryBudget = floorCfg.maxSanctuaries;
    let treasureBudget = floorCfg.maxTreasureRooms;

    let restPos: Coordinate | null = null;
    if (sanctuaryBudget > 0 && this.shopPos) {
      for (const npos of this.neighbors(this.shopPos)) {
        const r = this.getRoomAt(npos);
        if (r && r.getRole() === 'combat') {
          restPos = npos;
          break;
        }
      }
    }
    if (sanctuaryBudget > 0 && !restPos && assignable.length > 2) {
      restPos = assignable[Math.min(assignable.length - 1, Math.floor(n * 0.45))];
    }
    if (restPos) sanctuaryBudget--;

    // Trap: framed as sanctuary — only if sanctuary budget remains.
    let trapPos: Coordinate | null = null;
    if (sanctuaryBudget > 0) {
      for (const p of assignable) {
        if (restPos && p.equals(restPos)) continue;
        const d = (dist.get(p.toKey()) ?? 0) / maxDist;
        if (d > 0.25 && d < 0.55) {
          trapPos = p;
          sanctuaryBudget--;
          break;
        }
      }
    }

    // Hazard: framed as danger, actually loot cache.
    let hazardPos: Coordinate | null = null;
    for (let i = assignable.length - 1; i >= 0; i--) {
      const p = assignable[i];
      if (restPos?.equals(p) || trapPos?.equals(p)) continue;
      hazardPos = p;
      break;
    }

    // Locked treasure rooms (up to floor cap).
    const treasurePositions: Coordinate[] = [];
    const taken = (p: Coordinate) =>
      restPos?.equals(p) ||
      trapPos?.equals(p) ||
      hazardPos?.equals(p) ||
      treasurePositions.some((t) => t.equals(p));

    const treasureCandidates = [
      ...assignable.filter((p) => !taken(p) && this.countAdjacentRooms(p) === 1),
      ...assignable.filter((p) => !taken(p) && this.countAdjacentRooms(p) !== 1),
    ];
    for (const p of treasureCandidates) {
      if (treasureBudget <= 0) break;
      if (taken(p)) continue;
      treasurePositions.push(p);
      treasureBudget--;
    }

    const setRole = (pos: Coordinate | null, role: RoomRole) => {
      if (!pos) return;
      const room = this.getRoomAt(pos);
      if (!room || room instanceof ShopRoom || room instanceof BossRoom) return;
      room.setRole(role);
    };

    setRole(restPos, 'rest');
    setRole(trapPos, 'trap');
    setRole(hazardPos, 'hazard');
    for (const tp of treasurePositions) setRole(tp, 'treasure');

    if (assignable[eliteIdx]) {
      const p = assignable[eliteIdx];
      const room = this.getRoomAt(p);
      if (room && room.getRole() === 'combat') room.setRole('elite');
    }
    if (this.floorIndex >= 1 && assignable[lateEliteIdx]) {
      const p = assignable[lateEliteIdx];
      const room = this.getRoomAt(p);
      if (room && room.getRole() === 'combat') room.setRole('elite');
    }

    // Locks: optional side content only — never gold/HP/key gates on the boss path.
    for (const treasurePos of treasurePositions) {
      const room = this.getRoomAt(treasurePos)!;
      const roll = Math.random();
      if (roll < 0.45) room.setLock({ kind: 'key' });
      else if (roll < 0.75) {
        room.setLock({ kind: 'gold', amount: 40 + this.floorIndex * 35 });
      } else {
        room.setLock({ kind: 'hp', amount: 20 + this.floorIndex * 15 });
      }
    }
    for (const p of assignable) {
      const room = this.getRoomAt(p);
      if (!room || room.getRole() !== 'elite') continue;
      if (room.isLocked()) continue;
      if (Math.random() < 0.35 + this.floorIndex * 0.1) {
        room.setLock(
          Math.random() < 0.5
            ? { kind: 'key' }
            : { kind: 'gold', amount: 30 + this.floorIndex * 25 },
        );
      }
    }

    this.clearLocksOnCriticalPath();
    this.seedKeysForLocks();
  }

  /** Boss route must stay completable without keys/gold/HP locks. */
  private clearLocksOnCriticalPath(): void {
    const bossPos = this.findBossPos();
    if (!bossPos) return;
    const path = this.shortestPath(this.startRoom, bossPos);
    for (const p of path) {
      const room = this.getRoomAt(p);
      if (room?.isLocked()) room.clearLock();
    }
    // Shop should also stay reachable
    if (this.shopPos) {
      for (const p of this.shortestPath(this.startRoom, this.shopPos)) {
        const room = this.getRoomAt(p);
        if (room?.isLocked()) room.clearLock();
      }
    }
  }

  /** One free key per key-lock, in unlocked rooms you can reach without a key. */
  private seedKeysForLocks(): void {
    let needed = 0;
    for (const p of this.allRoomPositions()) {
      const lock = this.getRoomAt(p)?.getLock();
      if (lock?.kind === 'key') needed++;
    }
    if (needed === 0) return;

    const donors = this.allRoomPositions().filter((p) => {
      const r = this.getRoomAt(p)!;
      if (r.isLocked()) return false;
      if (r instanceof BossRoom || r instanceof ShopRoom) return false;
      if (r.getRole() === 'start') return false;
      return true;
    });

    // Prefer rest / hazard / combat without creatures later filled — any unlocked room works.
    donors.sort((a, b) => {
      const rank = (p: Coordinate) => {
        const role = this.getRoomAt(p)!.getRole();
        if (role === 'rest') return 0;
        if (role === 'hazard') return 1;
        if (role === 'combat') return 2;
        return 3;
      };
      return rank(a) - rank(b);
    });

    for (let i = 0; i < needed && i < donors.length; i++) {
      this.getRoomAt(donors[i])!.setKeyPickup(true);
    }
  }

  private findBossPos(): Coordinate | null {
    for (const p of this.allRoomPositions()) {
      if (this.getRoomAt(p) instanceof BossRoom) return p;
    }
    return null;
  }

  private shortestPath(from: Coordinate, to: Coordinate): Coordinate[] {
    const prev = new Map<string, Coordinate | null>();
    const q: Coordinate[] = [from];
    prev.set(from.toKey(), null);
    while (q.length) {
      const cur = q.shift()!;
      if (cur.equals(to)) break;
      for (const n of this.neighbors(cur)) {
        if (!this.getRoomAt(n) || prev.has(n.toKey())) continue;
        prev.set(n.toKey(), cur);
        q.push(n);
      }
    }
    if (!prev.has(to.toKey())) return [];
    const path: Coordinate[] = [];
    let walk: Coordinate | null = to;
    while (walk) {
      path.push(walk);
      walk = prev.get(walk.toKey()) ?? null;
    }
    return path.reverse();
  }

  private fillRoomContents(): void {
    const dist = this.bfsDistances(this.startRoom);
    let maxDist = 1;
    for (const p of this.allRoomPositions()) {
      maxDist = Math.max(maxDist, dist.get(p.toKey()) ?? 0);
    }

    for (const p of this.allRoomPositions()) {
      const room = this.getRoomAt(p)!;
      if (room instanceof ShopRoom || room instanceof BossRoom || room.getRole() === 'start') {
        continue;
      }

      const depthRatio = (dist.get(p.toKey()) ?? 0) / maxDist;
      const band = depthRatio < 0.34 ? 0 : depthRatio < 0.67 ? 1 : 2;

      switch (room.getRole()) {
        case 'rest':
          // True breather — no fight, light refill loot.
          if (Math.random() < 0.7) room.addItem(ConsumableFactory.createWeaponRefillPotion());
          if (Math.random() < 0.4) room.addItem(ConsumableFactory.createSmallHealthPotion());
          break;
        case 'hazard':
          // False danger — good loot, no creature.
          room.addItem(this.roomItemPool.createNew());
          room.addItem(this.roomItemPool.createNew());
          if (Math.random() < 0.5) room.addItem(AccessoryFactory.createRandom());
          break;
        case 'treasure':
          room.addItem(this.betterLoot());
          room.addItem(AccessoryFactory.createRandom());
          if (Math.random() < 0.5) room.addItem(ConsumableFactory.createLargeHealthPotion());
          break;
        case 'trap': {
          const enemy = this.creaturePool.createNew();
          room.setCreature(enemy);
          break;
        }
        case 'elite': {
          const enemy = this.creaturePool.createNew();
          const tags = pickEliteTags(band + this.floorIndex);
          applyEliteTags(enemy, tags);
          room.setEliteTags(tags);
          room.setCreature(enemy);
          if (Math.random() < 0.55) room.addItem(this.betterLoot());
          break;
        }
        case 'combat':
        default: {
          // Spiral: deeper = more likely fight + scarcer refills in loot.
          const fightChance = 0.55 + band * 0.15 + this.floorIndex * 0.05;
          if (Math.random() < fightChance) {
            room.setCreature(this.creaturePool.createNew());
          }
          const lootChance = band === 0 ? 0.45 : band === 1 ? 0.35 : 0.28;
          if (Math.random() < lootChance) {
            room.addItem(this.roomItemPool.createNew());
          }
          // Status pressure rooms deeper: chance of antidote lying around early, poison tools late.
          if (band >= 1 && Math.random() < 0.2) {
            room.addItem(ConsumableFactory.createAntidote());
          }
          break;
        }
      }
    }
  }

  private betterLoot(): Item {
    const roll = Math.random();
    if (roll < 0.34) return WeaponFactory.createStunMace();
    if (roll < 0.5) return WeaponFactory.createFrostWand();
    if (roll < 0.66) return WeaponFactory.createCleaver();
    if (roll < 0.82) return ConsumableFactory.createLargeHealthPotion();
    return AccessoryFactory.createRandom();
  }

  private addRoom(room: Room): Coordinate {
    return this.addRoomAt(room, this.randomValidPosition());
  }

  private addRoomAt(room: Room, pos: Coordinate): Coordinate {
    room.setDecorations(generateRoomDecorations());
    const vines = generateRoomVines();
    if (room instanceof ShopRoom) {
      vines.unshift({ kind: 'shop', flipX: false });
    }
    room.setVines(vines);
    this.rooms[pos.getRow()][pos.getCol()] = room;
    this.updateValidPositions(pos.getRow(), pos.getCol());
    return pos;
  }

  private getRoomAt(pos: Coordinate): Room | null {
    return this.rooms[pos.getRow()]?.[pos.getCol()] ?? null;
  }

  private randomValidPosition(): Coordinate {
    const values = [...this.validPositions.values()];
    return values[Math.floor(Math.random() * values.length)];
  }

  private pickMidDistanceFrontier(): Coordinate {
    const values = [...this.validPositions.values()];
    if (values.length === 0) throw new Error('No frontier for shop');
    let best = values[0];
    let bestScore = Infinity;
    const target = 2 + this.floorIndex;
    for (const pos of values) {
      const dist =
        Math.abs(pos.getRow() - this.startRoom.getRow()) +
        Math.abs(pos.getCol() - this.startRoom.getCol());
      const score = Math.abs(dist - target);
      if (score < bestScore) {
        bestScore = score;
        best = pos;
      }
    }
    return best;
  }

  private countAdjacentRooms(pos: Coordinate): number {
    return this.neighbors(pos).filter((n) => this.getRoomAt(n)).length;
  }

  private neighbors(pos: Coordinate): Coordinate[] {
    const r = pos.getRow();
    const c = pos.getCol();
    const out: Coordinate[] = [];
    for (const [nr, nc] of [
      [r - 1, c],
      [r + 1, c],
      [r, c - 1],
      [r, c + 1],
    ]) {
      if (nr < 0 || nc < 0 || nr >= this.roomLength || nc >= this.roomLength) continue;
      out.push(new Coordinate(nr, nc));
    }
    return out;
  }

  private farthestLeafFromStart(): Coordinate {
    const values = [...this.validPositions.values()];
    if (values.length === 0) throw new Error('No valid position left for boss room');
    const leaves = values.filter((pos) => this.countAdjacentRooms(pos) === 1);
    const pool = leaves.length > 0 ? leaves : values;

    let best = pool[0];
    let bestDist = -1;
    for (const pos of pool) {
      const dist =
        Math.abs(pos.getRow() - this.startRoom.getRow()) +
        Math.abs(pos.getCol() - this.startRoom.getCol());
      if (dist > bestDist) {
        bestDist = dist;
        best = pos;
      }
    }
    return best;
  }

  private allRoomPositions(): Coordinate[] {
    const out: Coordinate[] = [];
    for (let r = 0; r < this.roomLength; r++) {
      for (let c = 0; c < this.roomLength; c++) {
        if (this.rooms[r][c]) out.push(new Coordinate(r, c));
      }
    }
    return out;
  }

  private bfsDistances(start: Coordinate): Map<string, number> {
    const dist = new Map<string, number>();
    const q: Coordinate[] = [start];
    dist.set(start.toKey(), 0);
    while (q.length) {
      const cur = q.shift()!;
      const d = dist.get(cur.toKey())!;
      for (const n of this.neighbors(cur)) {
        if (!this.getRoomAt(n)) continue;
        if (dist.has(n.toKey())) continue;
        dist.set(n.toKey(), d + 1);
        q.push(n);
      }
    }
    return dist;
  }

  private updateValidPositions(row: number, col: number): void {
    this.validPositions.delete(new Coordinate(row, col).toKey());
    const neighbors = [
      [row - 1, col],
      [row + 1, col],
      [row, col - 1],
      [row, col + 1],
    ];
    for (const [nr, nc] of neighbors) {
      if (nr < 0 || nc < 0 || nr >= this.roomLength || nc >= this.roomLength) continue;
      if (this.rooms[nr][nc] === null) {
        const c = new Coordinate(nr, nc);
        this.validPositions.set(c.toKey(), c);
      }
    }
  }
}
