import { create } from 'zustand';
import { Level } from '../game/levels/Level';
import { DefaultLevelInitializer } from '../game/levels/DefaultLevelInitializer';
import { DefaultShopInitializer, ShopRoom, type ShopEntry } from '../game/levels/rooms/shop/Shop';
import { BossRoom, Room } from '../game/levels/rooms/Room';
import {
  bossPoolForFloor,
  creaturePoolForFloor,
  FloorItemPool,
} from '../game/pools/FloorPools';
import { ShopWeaponPool, ShopConsumablePool, ShopAccessoryPool } from '../game/pools/ItemPools';
import { CreatureFactory } from '../game/creatures/CreatureFactory';
import type { Player } from '../game/creatures/Player';
import type { PlayerClass } from '../game/creatures/playerClass';
import { Coordinate } from '../game/levels/Coordinate';
import { Fight, type MoveResult } from '../game/Fight';
import type { Creature } from '../game/creatures/Creature';
import type { Weapon } from '../game/items/weapons/Weapon';
import {
  applyScrapPouchOnFightStart,
  countEquipped,
  lockpickGoldMultiplier,
  pirateCoinGoldMultiplier,
  rollAccessoryDrop,
  syncVampiricFangAfterInventoryChange,
} from '../game/items/accessories/accessoryCombat';
import { eliteHasDraining, taxPlayerAmmo } from '../game/levels/eliteModifiers';
import { getFloorConfig, isFinalFloor, TOTAL_FLOORS } from '../game/levels/floorConfig';
import {
  applyDescendBuff,
  getDescendBuffOption,
  type DescendBuffId,
} from '../game/creatures/descendBuffs';
import { ForgeRoom } from '../game/levels/rooms/ForgeRoom';
import {
  canUpgradeWeaponTier,
  upgradeWeaponOneTier,
  weaponGoldCost,
  weaponSellPrice,
} from '../game/levels/forge';
import type { AccessoryKind } from '../game/items/accessories/Accessory';
import { AccessoryFactory } from '../game/items/accessories/AccessoryFactory';
import { nextFxId, type CombatFx } from '../fx/combatFx';
import { sfx } from '../fx/sounds';

export type Screen = 'splash' | 'select' | 'map' | 'fight' | 'win' | 'death';
export type { PlayerClass } from '../game/creatures/playerClass';
export type ModalKind = 'none' | 'shop' | 'inventory' | 'roomItems' | 'descend' | 'forge';

interface FightState {
  fight: Fight;
  room: Room;
  enemy: Creature;
  isPlayersTurn: boolean;
  log: string;
}

interface GameState {
  screen: Screen;
  modal: ModalKind;
  player: Player | null;
  level: Level | null;
  floorIndex: number;
  mapMessage: string | null;
  fightState: FightState | null;
  combatFx: CombatFx | null;
  deathCause: string | null;
  tick: number;
  bump: () => void;
  clearCombatFx: () => void;
  clearMapMessage: () => void;
  startNewGame: () => void;
  selectClass: (cls: PlayerClass) => void;
  exploreRoom: (pos: Coordinate) => void;
  closeModal: () => void;
  openInventory: () => void;
  openRoomItems: () => void;
  performPlayerMove: (weaponIndex: number) => void;
  finishEnemyTurn: () => void;
  useConsumable: (index: number) => void;
  dropWeapon: (index: number) => void;
  dropConsumable: (index: number) => void;
  dropAccessory: (index: number) => void;
  equipAccessory: (index: number) => void;
  unequipAccessory: (index: number) => void;
  pickupItem: (index: number) => void;
  buyEntry: (entry: ShopEntry) => void;
  sellWeapon: (weaponIndex: number) => void;
  exitToSplash: () => void;
  winFight: () => void;
  loseFight: (cause: string) => void;
  descendFloor: (buffId: DescendBuffId) => void;
  openForge: () => void;
  forgeUpgradeWeapon: (weaponIndex: number) => void;
  forgeCombineAccessories: (bagIndices: number[], resultKind: AccessoryKind) => void;
}

function createLevel(floorIndex: number): Level {
  const cfg = getFloorConfig(floorIndex);
  const shopInit = new DefaultShopInitializer(
    new ShopWeaponPool(),
    new ShopConsumablePool(),
    new ShopAccessoryPool(),
    3,
    1,
    3,
    3,
    2,
    1,
    60 + floorIndex * 20,
  );
  const levelInit = new DefaultLevelInitializer(
    cfg.numRooms,
    cfg.roomLength,
    creaturePoolForFloor(floorIndex),
    new FloorItemPool(floorIndex),
    bossPoolForFloor(floorIndex),
    shopInit,
    floorIndex,
  );
  return new Level(levelInit, floorIndex);
}

function createPlayer(cls: PlayerClass): Player {
  let player: Player;
  switch (cls) {
    case 'knight':
      player = CreatureFactory.createSlashPlayer();
      break;
    case 'mage':
      player = CreatureFactory.createMagePlayer();
      break;
    case 'ranger':
      player = CreatureFactory.createRangePlayer();
      break;
    case 'barbarian':
      player = CreatureFactory.createBluntPlayer();
      break;
  }
  player.setPlayerClass(cls);
  return player;
}

function fxFromMove(result: MoveResult): CombatFx {
  return {
    id: nextFxId(),
    missed: result.missed,
    damageDealt: result.damageDealt,
    healed: result.healed,
    targetSide: result.targetIsPlayer ? 'player' : 'enemy',
    sourceSide: result.sourceIsPlayer ? 'player' : 'enemy',
    weaponIndex: result.weaponIndex,
    shake: !result.missed && result.damageDealt > 0,
    procs: result.procs,
  };
}

function playMoveSfx(result: MoveResult): void {
  if (result.missed) sfx.miss();
  else if (result.damageDealt > 0) sfx.hit();
}

function tryUnlock(
  player: Player,
  room: Room,
  floorIndex: number,
): { ok: boolean; message: string } {
  const lock = room.getLock();
  if (!lock) return { ok: true, message: '' };

  if (lock.kind === 'key') {
    if (player.spendKey()) {
      room.clearLock();
      return { ok: true, message: 'Used a key. Door unlocked.' };
    }
    // Fallback so a missing key never softlocks optional rooms.
    const goldCost = 50 + floorIndex * 30;
    if (player.getGold() >= goldCost) {
      player.subractGold(goldCost);
      room.clearLock();
      return { ok: true, message: `No key — bribed the lock for ${goldCost} gold.` };
    }
    return {
      ok: false,
      message: `Locked — need a key (or ${goldCost} gold). Explore other rooms for a key.`,
    };
  }

  if (lock.kind === 'gold') {
    const cost = Math.max(1, Math.floor(lock.amount * lockpickGoldMultiplier(player)));
    if (player.getGold() < cost) {
      return { ok: false, message: `Locked — need ${cost} gold.` };
    }
    player.subractGold(cost);
    room.clearLock();
    return { ok: true, message: `Paid ${cost} gold. Door unlocked.` };
  }

  // hp
  if (player.getHealth() <= lock.amount) {
    return { ok: false, message: `Blood lock — need more than ${lock.amount} HP to force it.` };
  }
  player.setHealth(player.getHealth() - lock.amount);
  room.clearLock();
  return { ok: true, message: `Forced the door — lost ${lock.amount} HP.` };
}

function beginFight(player: Player, room: Room, enemy: Creature, extraLog: string[] = []): void {
  const fight = new Fight(player, enemy);
  const logs = [...extraLog, `Start of fight between ${player.getName()} and ${enemy.getName()}`];

  const scrap = applyScrapPouchOnFightStart(player);
  if (scrap) logs.push(scrap);

  if (room.getRole() === 'elite' && eliteHasDraining(room.getEliteTags())) {
    const tax = taxPlayerAmmo(player);
    if (tax) logs.push(tax);
  }

  if (room.getRole() === 'trap') {
    room.setTrapRevealed(true);
    logs.unshift('Ambush! The sanctuary was a trap.');
  }

  if (room instanceof BossRoom) {
    sfx.bossSting();
  }

  useGameStore.setState({
    screen: 'fight',
    fightState: {
      fight,
      room,
      enemy,
      isPlayersTurn: true,
      log: logs.filter(Boolean).join(' | '),
    },
    modal: 'none',
    combatFx: null,
  });
}

export const useGameStore = create<GameState>((set, get) => ({
  screen: 'splash',
  modal: 'none',
  player: null,
  level: null,
  floorIndex: 0,
  mapMessage: null,
  fightState: null,
  combatFx: null,
  deathCause: null,
  tick: 0,

  bump: () => set((s) => ({ tick: s.tick + 1 })),
  clearCombatFx: () => set({ combatFx: null }),
  clearMapMessage: () => set({ mapMessage: null }),

  startNewGame: () => {
    set({
      screen: 'select',
      modal: 'none',
      player: null,
      level: createLevel(0),
      floorIndex: 0,
      mapMessage: null,
      fightState: null,
      combatFx: null,
      deathCause: null,
    });
  },

  selectClass: (cls) => {
    const { level } = get();
    if (!level) return;
    const player = createPlayer(cls);
    player.setCurrentPosition(level.getStartPosition());
    const cfg = getFloorConfig(0);
    set({
      player,
      screen: 'map',
      modal: 'none',
      mapMessage: `${cfg.displayName} (Floor 1/${TOTAL_FLOORS}) — explore wisely.`,
    });
  },

  exploreRoom: (pos) => {
    const { player, level, floorIndex } = get();
    if (!player || !level) return;
    const room = level.getRoom(pos);
    if (!room) return;

    if (room.isLocked()) {
      const unlock = tryUnlock(player, room, floorIndex);
      if (!unlock.ok) {
        set({ mapMessage: unlock.message });
        get().bump();
        return;
      }
      set({ mapMessage: unlock.message });
    }

    player.setCurrentPosition(pos);
    level.exploreRoom(pos);
    sfx.doorEnter();

    const notes: string[] = [];

    if (room.takeKeyPickup()) {
      player.addKeys(1);
      notes.push('Found a key.');
    }

    if (room.getRole() === 'rest' && !room.isRestUsed()) {
      const heal = Math.max(1, Math.floor(player.getMaxHealth() * 0.15));
      const gained = player.addHealth(heal);
      room.setRestUsed(true);
      notes.push(`Rest site — recovered ${Math.round(gained)} HP.`);
    }

    if (room.getRole() === 'hazard') {
      notes.push('Looks dangerous… but it is quiet. A cache waits.');
    }

    if (notes.length) {
      set({ mapMessage: notes.join(' ') });
    }

    if (room instanceof ShopRoom) {
      set({ modal: 'shop', mapMessage: get().mapMessage ?? 'A shop — spend carefully, then push on.' });
      get().bump();
      return;
    }

    if (room instanceof ForgeRoom) {
      set({
        modal: 'forge',
        mapMessage: get().mapMessage ?? 'A forge — temper steel or fuse relics.',
      });
      get().bump();
      return;
    }

    if (room.hasCreature()) {
      beginFight(player, room, room.getCreature()!, notes);
      get().bump();
      return;
    }

    // Boss already cleared — offer stairs again after dismissing "Stay a moment"
    if (room instanceof BossRoom && !isFinalFloor(floorIndex)) {
      set({
        modal: 'descend',
        mapMessage: get().mapMessage ?? 'The stairway still waits.',
      });
      get().bump();
      return;
    }

    get().bump();
  },

  closeModal: () => set({ modal: 'none' }),
  openInventory: () => set({ modal: 'inventory' }),
  openRoomItems: () => set({ modal: 'roomItems' }),
  openForge: () => {
    const room = getCurrentRoom();
    if (!(room instanceof ForgeRoom)) return;
    set({ modal: 'forge' });
  },

  forgeUpgradeWeapon: (weaponIndex) => {
    const { player } = get();
    const room = getCurrentRoom();
    if (!player || !(room instanceof ForgeRoom)) return;
    const weapon = player.getInventory().getWeapon(weaponIndex);
    if (!weapon) return;
    if (room.hasUpgradedWeapon(weapon)) return;
    if (!canUpgradeWeaponTier(weapon)) return;
    const cost = weaponGoldCost(weapon);
    if (player.getGold() < cost) return;
    player.subractGold(cost);
    upgradeWeaponOneTier(weapon);
    room.markWeaponUpgraded(weapon);
    sfx.shopBuy();
    get().bump();
  },

  forgeCombineAccessories: (bagIndices, resultKind) => {
    const { player } = get();
    const room = getCurrentRoom();
    if (!player || !(room instanceof ForgeRoom)) return;
    const unique = [...new Set(bagIndices)];
    if (unique.length !== 3) return;
    const inv = player.getInventory();
    if (unique.some((i) => i < 0 || i >= inv.getAccessories().length)) return;

    const fangsBefore = countEquipped(player, 'vampiricFang');
    const sorted = [...unique].sort((a, b) => b - a);
    for (const i of sorted) {
      inv.removeAccessory(i);
    }
    syncVampiricFangAfterInventoryChange(player, fangsBefore);

    const fangsBeforeAdd = countEquipped(player, 'vampiricFang');
    inv.addItem(AccessoryFactory.create(resultKind));
    syncVampiricFangAfterInventoryChange(player, fangsBeforeAdd);

    sfx.shopBuy();
    get().bump();
  },

  performPlayerMove: (weaponIndex) => {
    const { player, fightState } = get();
    if (!player || !fightState || !fightState.isPlayersTurn) return;

    const weapon = player.getInventory().getWeapon(weaponIndex) as Weapon | null;
    if (!weapon) return;
    if (weapon.getMove().getUses() === 0) return;

    const target = weapon.getMove().targetsAllies() ? player : fightState.enemy;
    const result = fightState.fight.performMove(player, target, weapon, weaponIndex);
    playMoveSfx(result);

    if (!fightState.enemy.isAlive()) {
      set({ combatFx: fxFromMove(result) });
      get().winFight();
      return;
    }
    if (!player.isAlive()) {
      set({ combatFx: fxFromMove(result) });
      get().loseFight(result.message || 'You fell in battle.');
      return;
    }

    set({
      fightState: { ...fightState, isPlayersTurn: false, log: result.message },
      combatFx: fxFromMove(result),
    });
    get().bump();
  },

  finishEnemyTurn: () => {
    const { player, fightState } = get();
    if (!player || !fightState || fightState.isPlayersTurn) return;

    let log = fightState.enemy.calculateEffects();
    if (!fightState.enemy.isAlive()) {
      get().winFight();
      return;
    }

    const mods = fightState.enemy.getTurnModifiers();
    let result: MoveResult;
    if (mods.isActionBlocked()) {
      result = {
        message: `${fightState.enemy.getName()} is knocked out and skips their turn!`,
        missed: true,
        damageDealt: 0,
        healed: false,
        sourceIsPlayer: false,
        targetIsPlayer: false,
        weaponIndex: 0,
        procs: ['KO'],
      };
    } else if (Math.random() < mods.getActionFailChance()) {
      result = {
        message: `${fightState.enemy.getName()} is shocked and fails to attack!`,
        missed: true,
        damageDealt: 0,
        healed: false,
        sourceIsPlayer: false,
        targetIsPlayer: false,
        weaponIndex: 0,
        procs: ['SHOCK'],
      };
    } else {
      result = fightState.fight.creatureTurn(fightState.enemy, player);
      playMoveSfx(result);
    }

    log = [log, result.message].filter(Boolean).join(' | ');

    if (!player.isAlive()) {
      set({ fightState: { ...fightState, log }, combatFx: fxFromMove(result) });
      get().loseFight(
        result.damageDealt > 0
          ? `${fightState.enemy.getName()} defeated you. ${result.message}`
          : result.message || `${fightState.enemy.getName()} defeated you.`,
      );
      return;
    }

    const playerFx = player.calculateEffects();
    if (playerFx) log = `${log} | ${playerFx}`;

    if (!player.isAlive()) {
      set({ fightState: { ...fightState, log }, combatFx: fxFromMove(result) });
      get().loseFight(playerFx || 'Status effects finished you off.');
      return;
    }

    set({
      fightState: { ...fightState, isPlayersTurn: true, log },
      combatFx: fxFromMove(result),
    });
    get().bump();
  },

  useConsumable: (index) => {
    const { player, fightState, screen } = get();
    if (!player) return;
    const inv = player.getInventory();
    const item = inv.getConsumable(index);
    if (!item) return;

    const inFight = screen === 'fight' && !!fightState;
    if (inFight && !fightState!.isPlayersTurn) return;

    let target: Creature = player;
    if (!item.getAffectsSelf() && inFight) {
      target = fightState!.enemy;
    }

    const healthBefore = target.getHealth();
    const msg = item.applyEffects(target);
    inv.removeConsumable(index);
    const healthAfter = target.getHealth();

    if (!inFight || !fightState) {
      get().bump();
      return;
    }

    const procs: string[] = [];
    const healed = item.getAffectsSelf() && healthAfter > healthBefore;
    if (!item.getAffectsSelf()) procs.push('ITEM');

    const fx: CombatFx = {
      id: nextFxId(),
      missed: false,
      damageDealt: Math.max(0, healthBefore - healthAfter),
      healed,
      targetSide: target === player ? 'player' : 'enemy',
      sourceSide: 'player',
      weaponIndex: 0,
      shake: healthAfter < healthBefore,
      procs,
    };

    if (!fightState.enemy.isAlive()) {
      set({ combatFx: fx, modal: 'none', fightState: { ...fightState, log: msg || fightState.log } });
      get().winFight();
      return;
    }
    if (!player.isAlive()) {
      set({ combatFx: fx, modal: 'none', fightState: { ...fightState, log: msg || fightState.log } });
      get().loseFight(msg || `The ${item.getName()} killed you.`);
      return;
    }

    set({
      modal: 'none',
      fightState: {
        ...fightState,
        isPlayersTurn: false,
        log: msg ? `${msg} (used your turn)` : `Used ${item.getName()} (used your turn)`,
      },
      combatFx: fx,
    });
    get().bump();
  },

  dropWeapon: (index) => {
    const { player, level } = get();
    if (!player || !level) return;
    const pos = player.getCurrentPosition();
    if (!pos) return;
    const room = level.getRoom(pos);
    if (!room) return;
    const w = player.getInventory().removeWeapon(index);
    if (w) room.addItem(w);
    get().bump();
  },

  dropConsumable: (index) => {
    const { player, level } = get();
    if (!player || !level) return;
    const pos = player.getCurrentPosition();
    if (!pos) return;
    const room = level.getRoom(pos);
    if (!room) return;
    const c = player.getInventory().removeConsumable(index);
    if (c) room.addItem(c);
    get().bump();
  },

  dropAccessory: (index) => {
    const { player, level } = get();
    if (!player || !level) return;
    const pos = player.getCurrentPosition();
    if (!pos) return;
    const room = level.getRoom(pos);
    if (!room) return;
    const fangsBefore = countEquipped(player, 'vampiricFang');
    const a = player.getInventory().removeAccessory(index);
    if (a) {
      room.addItem(a);
      syncVampiricFangAfterInventoryChange(player, fangsBefore);
    }
    get().bump();
  },

  equipAccessory: (index) => {
    const { player } = get();
    if (!player) return;
    const fangsBefore = countEquipped(player, 'vampiricFang');
    if (!player.getInventory().equipAccessory(index)) return;
    syncVampiricFangAfterInventoryChange(player, fangsBefore);
    get().bump();
  },

  unequipAccessory: (index) => {
    const { player } = get();
    if (!player) return;
    const fangsBefore = countEquipped(player, 'vampiricFang');
    if (!player.getInventory().unequipAccessory(index)) return;
    syncVampiricFangAfterInventoryChange(player, fangsBefore);
    get().bump();
  },

  pickupItem: (index) => {
    const { player, level } = get();
    if (!player || !level) return;
    const pos = player.getCurrentPosition();
    if (!pos) return;
    const room = level.getRoom(pos);
    if (!room) return;
    const item = room.getItems()[index];
    if (!item) return;
    const fangsBefore = countEquipped(player, 'vampiricFang');
    if (player.getInventory().addItem(item)) {
      room.removeItem(item);
      syncVampiricFangAfterInventoryChange(player, fangsBefore);
    }
    get().bump();
  },

  buyEntry: (entry) => {
    const { player } = get();
    if (!player) return;
    if (!(getCurrentRoom() instanceof ShopRoom)) return;
    if (entry.getQuantity() <= 0) return;
    if (player.getGold() < entry.getPrice()) return;
    const item = entry.getItem();
    const fangsBefore = countEquipped(player, 'vampiricFang');
    if (!player.getInventory().addItem(item)) return;
    syncVampiricFangAfterInventoryChange(player, fangsBefore);
    player.subractGold(entry.getPrice());
    entry.decreaseQuantity();
    sfx.shopBuy();
    get().bump();
  },

  sellWeapon: (weaponIndex) => {
    const { player } = get();
    if (!player) return;
    if (!(getCurrentRoom() instanceof ShopRoom)) return;
    const weapon = player.getInventory().getWeapon(weaponIndex);
    if (!weapon) return;
    const price = weaponSellPrice(weapon);
    const removed = player.getInventory().removeWeapon(weaponIndex);
    if (!removed) return;
    player.addGold(price);
    sfx.shopBuy();
    get().bump();
  },

  exitToSplash: () => {
    set({
      screen: 'splash',
      modal: 'none',
      player: null,
      level: null,
      floorIndex: 0,
      mapMessage: null,
      fightState: null,
      combatFx: null,
      deathCause: null,
    });
  },

  winFight: () => {
    const { player, fightState, floorIndex } = get();
    if (!player || !fightState) return;

    const baseGold = Math.trunc(fightState.enemy.getMaxHealth());
    player.addGold(Math.trunc(baseGold * pirateCoinGoldMultiplier(player)));
    player.clearEffects();
    fightState.room.removeCreature();

    if (fightState.room.getRole() === 'elite' || fightState.room.getRole() === 'trap') {
      player.addKeys(1);
      set({ mapMessage: 'Found a key!' });
    }

    const drop = rollAccessoryDrop();
    if (drop) {
      fightState.room.addItem(drop);
    }

    if (fightState.room instanceof BossRoom) {
      if (isFinalFloor(floorIndex)) {
        set({ screen: 'win', fightState: null, modal: 'none', combatFx: null });
      } else {
        set({
          screen: 'map',
          fightState: null,
          modal: 'descend',
          combatFx: null,
          mapMessage: 'Floor boss defeated. A stairway opens.',
        });
      }
    } else {
      set({ screen: 'map', fightState: null, modal: 'none', combatFx: null });
    }
    get().bump();
  },

  loseFight: (cause) => {
    set({
      screen: 'death',
      deathCause: cause,
      modal: 'none',
      fightState: null,
      combatFx: null,
    });
  },

  descendFloor: (buffId) => {
    const { player, floorIndex } = get();
    if (!player || isFinalFloor(floorIndex)) return;

    applyDescendBuff(player, buffId);
    const buffLabel = getDescendBuffOption(buffId).label;

    const next = floorIndex + 1;
    const heal = Math.max(1, Math.floor(player.getMaxHealth() * 0.25));
    player.addHealth(heal);
    player.clearEffects();
    player.getInventory().incrementMaxAccessories();
    const accessorySlots = player.getInventory().getMaxAccessories();

    const level = createLevel(next);
    player.setCurrentPosition(level.getStartPosition());
    const cfg = getFloorConfig(next);

    set({
      level,
      floorIndex: next,
      screen: 'map',
      modal: 'none',
      fightState: null,
      combatFx: null,
      mapMessage: `Descended to ${cfg.displayName} (Floor ${next + 1}/${TOTAL_FLOORS}). Chose ${buffLabel}. Recovered ${heal} HP. Equip slots: ${accessorySlots}.`,
    });
    get().bump();
  },
}));

export function getCurrentRoom(): Room | null {
  const { player, level } = useGameStore.getState();
  if (!player || !level) return null;
  const pos = player.getCurrentPosition();
  if (!pos) return null;
  return level.getRoom(pos);
}
