import { Coordinate } from './Coordinate';
import type { LevelInitializer } from './Level';
import { BossRoom, Room } from './rooms/Room';
import type { ShopInitializer } from './rooms/shop/Shop';
import { ShopRoom } from './rooms/shop/Shop';
import type { Pool } from '../pools/Pool';
import type { Creature } from '../creatures/Creature';
import type { Item } from '../items/Item';
import { generateRoomDecorations, generateRoomVines } from './roomDecor';

export class DefaultLevelInitializer implements LevelInitializer {
  private numRooms: number;
  private roomLength: number;
  private creaturePool: Pool<Creature>;
  private roomItemPool: Pool<Item>;
  private bossPool: Pool<Creature>;
  private shopInitializer: ShopInitializer;

  private rooms: (Room | null)[][] = [];
  private validPositions = new Map<string, Coordinate>();
  private startRoom = new Coordinate(0, 0);

  constructor(
    numRooms: number,
    roomLength: number,
    creaturePool: Pool<Creature>,
    roomItemPool: Pool<Item>,
    bossPool: Pool<Creature>,
    shopInitializer: ShopInitializer,
  ) {
    this.numRooms = numRooms;
    this.roomLength = roomLength;
    this.creaturePool = creaturePool;
    this.roomItemPool = roomItemPool;
    this.bossPool = bossPool;
    this.shopInitializer = shopInitializer;
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

    const seed = new Coordinate(
      Math.floor(Math.random() * this.roomLength),
      Math.floor(Math.random() * this.roomLength),
    );
    this.validPositions.set(seed.toKey(), seed);

    const boss = new BossRoom();
    boss.setCreature(this.bossPool.createNew());
    this.addRoom(boss);

    for (let i = 0; i < this.numRooms - 1; i++) {
      const room = new Room();
      if (Math.floor(Math.random() * 100) < 80) {
        room.setCreature(this.creaturePool.createNew());
      }
      if (Math.floor(Math.random() * 100) < 40) {
        room.addItem(this.roomItemPool.createNew());
      }
      this.addRoom(room);
    }

    this.startRoom = this.addRoom(new Room());
    this.addRoom(new ShopRoom(this.shopInitializer));
    return this.rooms;
  }

  private addRoom(room: Room): Coordinate {
    const pos = this.randomValidPosition();
    room.setDecorations(generateRoomDecorations());
    const vines = generateRoomVines();
    // Shop stall overlay on every shop (full-room texture, no flip)
    if (room instanceof ShopRoom) {
      vines.unshift({ kind: 'shop', flipX: false });
    }
    room.setVines(vines);
    this.rooms[pos.getRow()][pos.getCol()] = room;
    this.updateValidPositions(pos.getRow(), pos.getCol());
    return pos;
  }

  private randomValidPosition(): Coordinate {
    const values = [...this.validPositions.values()];
    return values[Math.floor(Math.random() * values.length)];
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
