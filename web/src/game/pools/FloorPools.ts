import { CreatureFactory } from '../creatures/CreatureFactory';
import type { Creature } from '../creatures/Creature';
import { WeaponFactory } from '../items/weapons/WeaponFactory';
import { ConsumableFactory } from '../items/consumables/ConsumableFactory';
import { AccessoryFactory } from '../items/accessories/AccessoryFactory';
import type { Item } from '../items/Item';
import { Pool } from './Pool';

/** Early floor — learn resists & basic kits. */
export class Floor0CreaturePool extends Pool<Creature> {
  constructor() {
    super();
    this.addObjectCreator(() => CreatureFactory.createRat(), 3);
    this.addObjectCreator(() => CreatureFactory.createSlime(), 2.5);
    this.addObjectCreator(() => CreatureFactory.createBat(), 2);
    this.addObjectCreator(() => CreatureFactory.createMushroom(), 2);
    this.addObjectCreator(() => CreatureFactory.createGoblin(), 1.5);
    this.addObjectCreator(() => CreatureFactory.createBird(), 1);
  }
}

/** Mid — status pressure & ammo weapons. */
export class Floor1CreaturePool extends Pool<Creature> {
  constructor() {
    super();
    this.addObjectCreator(() => CreatureFactory.createGoblin(), 2);
    this.addObjectCreator(() => CreatureFactory.createSkeleton(), 2);
    this.addObjectCreator(() => CreatureFactory.createSporekin(), 2);
    this.addObjectCreator(() => CreatureFactory.createImp(), 2);
    this.addObjectCreator(() => CreatureFactory.createBandit(), 2);
    this.addObjectCreator(() => CreatureFactory.createScout(), 1.5);
    this.addObjectCreator(() => CreatureFactory.createSorcerer(), 1.2);
  }
}

/** Deep — mixed resists, healers, brutes. */
export class Floor2CreaturePool extends Pool<Creature> {
  constructor() {
    super();
    this.addObjectCreator(() => CreatureFactory.createWraith(), 2);
    this.addObjectCreator(() => CreatureFactory.createBrute(), 2);
    this.addObjectCreator(() => CreatureFactory.createCultist(), 2);
    this.addObjectCreator(() => CreatureFactory.createGuardian(), 1.5);
    this.addObjectCreator(() => CreatureFactory.createTroll(), 1.5);
    this.addObjectCreator(() => CreatureFactory.createSorcerer(), 1.5);
    this.addObjectCreator(() => CreatureFactory.createBandit(), 1);
    this.addObjectCreator(() => CreatureFactory.createScout(), 1);
  }
}

export class Floor0BossPool extends Pool<Creature> {
  constructor() {
    super();
    this.addObjectCreator(() => CreatureFactory.createPurpleGolem(), 1);
  }
}

export class Floor1BossPool extends Pool<Creature> {
  constructor() {
    super();
    this.addObjectCreator(() => CreatureFactory.createFireGolem(), 1);
    this.addObjectCreator(() => CreatureFactory.createRockGolem(), 1);
  }
}

export class Floor2BossPool extends Pool<Creature> {
  constructor() {
    super();
    this.addObjectCreator(() => CreatureFactory.createIceGolem(), 1);
    this.addObjectCreator(() => CreatureFactory.createFireGolem(), 1);
    this.addObjectCreator(() => CreatureFactory.createRockGolem(), 1);
  }
}

/** Loot shifts: early refills plentiful; deep favors status tools & scarce ammo weapons. */
export class FloorItemPool extends Pool<Item> {
  constructor(floorIndex: number) {
    super();
    if (floorIndex <= 0) {
      this.addObjectCreator(() => WeaponFactory.createSteelSword(), 2);
      this.addObjectCreator(() => WeaponFactory.createSteelMace(), 2);
      this.addObjectCreator(() => WeaponFactory.createLongBow(), 1.5);
      this.addObjectCreator(() => WeaponFactory.createCleaver(), 1.5);
      this.addObjectCreator(() => ConsumableFactory.createSmallHealthPotion(), 3);
      this.addObjectCreator(() => ConsumableFactory.createWeaponRefillPotion(), 8);
      this.addObjectCreator(() => ConsumableFactory.createSmallPoisonPotion(), 1.5);
      this.addObjectCreator(() => ConsumableFactory.createAntidote(), 2);
      this.addObjectCreator(() => ConsumableFactory.createStrengthPotion(), 2);
    } else if (floorIndex === 1) {
      this.addObjectCreator(() => WeaponFactory.createJavelin(), 2);
      this.addObjectCreator(() => WeaponFactory.createBoneBow(), 2);
      this.addObjectCreator(() => WeaponFactory.createFrostWand(), 1.5);
      this.addObjectCreator(() => WeaponFactory.createStunMace(), 1.5);
      this.addObjectCreator(() => WeaponFactory.createEmberStaff(), 1.5);
      this.addObjectCreator(() => ConsumableFactory.createMediumHealthPotion(), 2.5);
      this.addObjectCreator(() => ConsumableFactory.createWeaponRefillPotion(), 4);
      this.addObjectCreator(() => ConsumableFactory.createMediumPoisonPotion(), 2);
      this.addObjectCreator(() => ConsumableFactory.createAntidote(), 2.5);
      this.addObjectCreator(() => ConsumableFactory.createResistancePotion(), 2);
      this.addObjectCreator(() => AccessoryFactory.createVenomFlask(), 1);
    } else {
      this.addObjectCreator(() => WeaponFactory.createCrossbow(), 1.5);
      this.addObjectCreator(() => WeaponFactory.createStaffOfPower(), 1.5);
      this.addObjectCreator(() => WeaponFactory.createToxicStaff(), 2);
      this.addObjectCreator(() => WeaponFactory.createDrainWand(), 1.5);
      this.addObjectCreator(() => WeaponFactory.createRoyalSword(), 1.2);
      this.addObjectCreator(() => ConsumableFactory.createLargeHealthPotion(), 2);
      this.addObjectCreator(() => ConsumableFactory.createWeaponRefillPotion(), 2.5);
      this.addObjectCreator(() => ConsumableFactory.createLargePoisonPotion(), 2);
      this.addObjectCreator(() => ConsumableFactory.createAntidote(), 2);
      this.addObjectCreator(() => ConsumableFactory.createStrengthPotion(), 2);
      this.addObjectCreator(() => AccessoryFactory.createIronBand(), 1);
      this.addObjectCreator(() => AccessoryFactory.createScrapPouch(), 1);
    }
  }
}

export function creaturePoolForFloor(floorIndex: number): Pool<Creature> {
  if (floorIndex <= 0) return new Floor0CreaturePool();
  if (floorIndex === 1) return new Floor1CreaturePool();
  return new Floor2CreaturePool();
}

export function bossPoolForFloor(floorIndex: number): Pool<Creature> {
  if (floorIndex <= 0) return new Floor0BossPool();
  if (floorIndex === 1) return new Floor1BossPool();
  return new Floor2BossPool();
}

/** Keep legacy names for any leftover imports. */
export { Floor0CreaturePool as DefaultCreaturePool };
export { Floor2BossPool as BossCreaturePool };
