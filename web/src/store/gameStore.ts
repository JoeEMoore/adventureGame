import { create } from 'zustand';
import { Level } from '../game/levels/Level';
import { DefaultLevelInitializer } from '../game/levels/DefaultLevelInitializer';
import { DefaultShopInitializer, ShopRoom, type ShopEntry } from '../game/levels/rooms/shop/Shop';
import { BossRoom, Room } from '../game/levels/rooms/Room';
import { DefaultCreaturePool, BossCreaturePool } from '../game/pools/CreaturePools';
import { DefaultItemPool, ShopWeaponPool, ShopConsumablePool } from '../game/pools/ItemPools';
import { CreatureFactory } from '../game/creatures/CreatureFactory';
import type { Player } from '../game/creatures/Player';
import { Coordinate } from '../game/levels/Coordinate';
import { Fight, type MoveResult } from '../game/Fight';
import type { Creature } from '../game/creatures/Creature';
import type { Weapon } from '../game/items/weapons/Weapon';
import { nextFxId, type CombatFx } from '../fx/combatFx';
import { sfx } from '../fx/sounds';

export type Screen = 'splash' | 'select' | 'map' | 'fight' | 'win';
export type PlayerClass = 'knight' | 'mage' | 'ranger' | 'barbarian';
export type ModalKind = 'none' | 'shop' | 'inventory' | 'roomItems';

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
  fightState: FightState | null;
  combatFx: CombatFx | null;
  tick: number;
  bump: () => void;
  clearCombatFx: () => void;
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
  pickupItem: (index: number) => void;
  buyEntry: (entry: ShopEntry) => void;
  exitToSplash: () => void;
  winFight: () => void;
  loseFight: () => void;
}

function createLevel(): Level {
  const shopInit = new DefaultShopInitializer(
    new ShopWeaponPool(),
    new ShopConsumablePool(),
    3,
    1,
    3,
    3,
  );
  const levelInit = new DefaultLevelInitializer(
    15,
    6,
    new DefaultCreaturePool(),
    new DefaultItemPool(),
    new BossCreaturePool(),
    shopInit,
  );
  return new Level(levelInit);
}

function createPlayer(cls: PlayerClass): Player {
  switch (cls) {
    case 'knight':
      return CreatureFactory.createSlashPlayer();
    case 'mage':
      return CreatureFactory.createMagePlayer();
    case 'ranger':
      return CreatureFactory.createRangePlayer();
    case 'barbarian':
      return CreatureFactory.createBluntPlayer();
  }
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
  };
}

function playMoveSfx(result: MoveResult): void {
  if (result.missed) sfx.miss();
  else if (result.damageDealt > 0) sfx.hit();
}

export const useGameStore = create<GameState>((set, get) => ({
  screen: 'splash',
  modal: 'none',
  player: null,
  level: null,
  fightState: null,
  combatFx: null,
  tick: 0,

  bump: () => set((s) => ({ tick: s.tick + 1 })),
  clearCombatFx: () => set({ combatFx: null }),

  startNewGame: () => {
    set({
      screen: 'select',
      modal: 'none',
      player: null,
      level: createLevel(),
      fightState: null,
      combatFx: null,
    });
  },

  selectClass: (cls) => {
    const { level } = get();
    if (!level) return;
    const player = createPlayer(cls);
    player.setCurrentPosition(level.getStartPosition());
    set({ player, screen: 'map', modal: 'none' });
  },

  exploreRoom: (pos) => {
    const { player, level } = get();
    if (!player || !level) return;
    const room = level.getRoom(pos);
    if (!room) return;

    player.setCurrentPosition(pos);
    level.exploreRoom(pos);
    sfx.doorEnter();

    if (room instanceof ShopRoom) {
      set({ modal: 'shop' });
      get().bump();
      return;
    }

    if (room.hasCreature()) {
      const enemy = room.getCreature()!;
      const fight = new Fight(player, enemy);
      if (room instanceof BossRoom) {
        sfx.bossSting();
      }
      set({
        screen: 'fight',
        fightState: {
          fight,
          room,
          enemy,
          isPlayersTurn: true,
          log: `Start of fight between ${player.getName()} and ${enemy.getName()}`,
        },
        modal: 'none',
        combatFx: null,
      });
      get().bump();
      return;
    }

    get().bump();
  },

  closeModal: () => set({ modal: 'none' }),
  openInventory: () => set({ modal: 'inventory' }),
  openRoomItems: () => set({ modal: 'roomItems' }),

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
      get().loseFight();
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

    const result = fightState.fight.creatureTurn(fightState.enemy, player);
    playMoveSfx(result);
    log = [log, result.message].filter(Boolean).join(' | ');

    if (!player.isAlive()) {
      set({ fightState: { ...fightState, log }, combatFx: fxFromMove(result) });
      get().loseFight();
      return;
    }

    const playerFx = player.calculateEffects();
    if (playerFx) log = `${log} | ${playerFx}`;

    if (!player.isAlive()) {
      set({ fightState: { ...fightState, log }, combatFx: fxFromMove(result) });
      get().loseFight();
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

    let target: Creature = player;
    if (!item.getAffectsSelf() && screen === 'fight' && fightState) {
      target = fightState.enemy;
    }

    const msg = item.applyEffects(target);
    inv.removeConsumable(index);

    if (fightState) {
      set({ fightState: { ...fightState, log: msg || fightState.log } });
      if (!fightState.enemy.isAlive()) {
        get().winFight();
        return;
      }
      if (!player.isAlive()) {
        get().loseFight();
        return;
      }
    }
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

  pickupItem: (index) => {
    const { player, level } = get();
    if (!player || !level) return;
    const pos = player.getCurrentPosition();
    if (!pos) return;
    const room = level.getRoom(pos);
    if (!room) return;
    const item = room.getItems()[index];
    if (!item) return;
    if (player.getInventory().addItem(item)) {
      room.removeItem(item);
    }
    get().bump();
  },

  buyEntry: (entry) => {
    const { player } = get();
    if (!player) return;
    if (entry.getQuantity() <= 0) return;
    if (player.getGold() < entry.getPrice()) return;
    const item = entry.getItem();
    if (!player.getInventory().addItem(item)) return;
    player.subractGold(entry.getPrice());
    entry.decreaseQuantity();
    sfx.shopBuy();
    get().bump();
  },

  exitToSplash: () => {
    set({
      screen: 'splash',
      modal: 'none',
      player: null,
      level: null,
      fightState: null,
      combatFx: null,
    });
  },

  winFight: () => {
    const { player, fightState } = get();
    if (!player || !fightState) return;

    player.addGold(Math.trunc(fightState.enemy.getMaxHealth()));
    player.clearEffects();
    fightState.room.removeCreature();

    if (fightState.room instanceof BossRoom) {
      set({ screen: 'win', fightState: null, modal: 'none', combatFx: null });
    } else {
      set({ screen: 'map', fightState: null, modal: 'none', combatFx: null });
    }
    get().bump();
  },

  loseFight: () => {
    set({
      screen: 'splash',
      modal: 'none',
      player: null,
      level: null,
      fightState: null,
      combatFx: null,
    });
  },
}));

export function getCurrentRoom(): Room | null {
  const { player, level } = useGameStore.getState();
  if (!player || !level) return null;
  const pos = player.getCurrentPosition();
  if (!pos) return null;
  return level.getRoom(pos);
}
