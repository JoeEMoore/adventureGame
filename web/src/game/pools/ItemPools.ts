import { WeaponFactory } from '../items/weapons/WeaponFactory';
import { ConsumableFactory } from '../items/consumables/ConsumableFactory';
import { AccessoryFactory } from '../items/accessories/AccessoryFactory';
import type { Item } from '../items/Item';
import { Pool } from './Pool';

export class DefaultItemPool extends Pool<Item> {
  constructor() {
    super();
    this.addObjectCreator(() => WeaponFactory.createSteelSword(), 2);
    this.addObjectCreator(() => WeaponFactory.createLongBow(), 2);
    this.addObjectCreator(() => WeaponFactory.createSteelMace(), 2);
    this.addObjectCreator(() => WeaponFactory.createEnchantedStaff(), 2);
    this.addObjectCreator(() => WeaponFactory.createRoyalSword(), 1.5);
    this.addObjectCreator(() => WeaponFactory.createCrossbow(), 1.5);
    this.addObjectCreator(() => WeaponFactory.createSteelHammer(), 1.5);
    this.addObjectCreator(() => WeaponFactory.createStaffOfPower(), 1.5);
    this.addObjectCreator(() => WeaponFactory.createToxicStaff(), 1.5);
    this.addObjectCreator(() => WeaponFactory.createHealStaff(), 1.5);
    this.addObjectCreator(() => ConsumableFactory.createSmallHealthPotion(), 3);
    this.addObjectCreator(() => ConsumableFactory.createSmallDamagePotion(), 3);
    this.addObjectCreator(() => ConsumableFactory.createMediumHealthPotion(), 2.5);
    this.addObjectCreator(() => ConsumableFactory.createMediumDamagePotion(), 2.5);
    this.addObjectCreator(() => ConsumableFactory.createLargeHealthPotion(), 2);
    this.addObjectCreator(() => ConsumableFactory.createLargeDamagePotion(), 2);
    this.addObjectCreator(() => ConsumableFactory.createWeaponRefillPotion(), 8);
    this.addObjectCreator(() => ConsumableFactory.createStrengthPotion(), 5);
    this.addObjectCreator(() => ConsumableFactory.createResistancePotion(), 5);
    this.addObjectCreator(() => ConsumableFactory.createAntidote(), 3);
    this.addObjectCreator(() => ConsumableFactory.createSmallPoisonPotion(), 2);
  }
}

export class ShopWeaponPool extends Pool<Item> {
  constructor() {
    super();
    this.addObjectCreator(() => WeaponFactory.createSteelSword(), 2);
    this.addObjectCreator(() => WeaponFactory.createLongBow(), 2);
    this.addObjectCreator(() => WeaponFactory.createSteelMace(), 2);
    this.addObjectCreator(() => WeaponFactory.createEnchantedStaff(), 2);
    this.addObjectCreator(() => WeaponFactory.createRoyalSword(), 1.5);
    this.addObjectCreator(() => WeaponFactory.createCrossbow(), 1.5);
    this.addObjectCreator(() => WeaponFactory.createSteelHammer(), 1.5);
    this.addObjectCreator(() => WeaponFactory.createStaffOfPower(), 1.5);
    this.addObjectCreator(() => WeaponFactory.createToxicStaff(), 1.5);
    this.addObjectCreator(() => WeaponFactory.createHealStaff(), 1.5);
  }
}

export class ShopConsumablePool extends Pool<Item> {
  constructor() {
    super();
    this.addObjectCreator(() => ConsumableFactory.createSmallHealthPotion(), 3);
    this.addObjectCreator(() => ConsumableFactory.createSmallDamagePotion(), 3);
    this.addObjectCreator(() => ConsumableFactory.createMediumHealthPotion(), 2.5);
    this.addObjectCreator(() => ConsumableFactory.createMediumDamagePotion(), 2.5);
    this.addObjectCreator(() => ConsumableFactory.createLargeHealthPotion(), 2);
    this.addObjectCreator(() => ConsumableFactory.createLargeDamagePotion(), 2);
    this.addObjectCreator(() => ConsumableFactory.createWeaponRefillPotion(), 8);
    this.addObjectCreator(() => ConsumableFactory.createStrengthPotion(), 5);
    this.addObjectCreator(() => ConsumableFactory.createResistancePotion(), 5);
    this.addObjectCreator(() => ConsumableFactory.createAntidote(), 3);
    this.addObjectCreator(() => ConsumableFactory.createSmallPoisonPotion(), 2);
  }
}

/** Equal weight for every accessory in the shop pool. */
export class ShopAccessoryPool extends Pool<Item> {
  constructor() {
    super();
    this.addObjectCreator(() => AccessoryFactory.createShield(), 1);
    this.addObjectCreator(() => AccessoryFactory.createWraps(), 1);
    this.addObjectCreator(() => AccessoryFactory.createGrips(), 1);
    this.addObjectCreator(() => AccessoryFactory.createDoubleShot(), 1);
    this.addObjectCreator(() => AccessoryFactory.createVenomFlask(), 1);
    this.addObjectCreator(() => AccessoryFactory.createPirateCoin(), 1);
    this.addObjectCreator(() => AccessoryFactory.createRingOfFire(), 1);
    this.addObjectCreator(() => AccessoryFactory.createRingOfIce(), 1);
    this.addObjectCreator(() => AccessoryFactory.createRingOfElectricity(), 1);
    this.addObjectCreator(() => AccessoryFactory.createIronBand(), 1);
    this.addObjectCreator(() => AccessoryFactory.createScrapPouch(), 1);
    this.addObjectCreator(() => AccessoryFactory.createLockpick(), 1);
    this.addObjectCreator(() => AccessoryFactory.createEchoCharm(), 1);
    this.addObjectCreator(() => AccessoryFactory.createFocusCrystal(), 1);
    this.addObjectCreator(() => AccessoryFactory.createThornCollar(), 1);
    this.addObjectCreator(() => AccessoryFactory.createVampiricFang(), 1);
    this.addObjectCreator(() => AccessoryFactory.createRitualCodex(), 1);
    this.addObjectCreator(() => AccessoryFactory.createOathMedallion(), 1);
    this.addObjectCreator(() => AccessoryFactory.createQuickstepBoots(), 1);
    this.addObjectCreator(() => AccessoryFactory.createEmptyQuiverCord(), 1);
    this.addObjectCreator(() => AccessoryFactory.createGlassDice(), 1);
    this.addObjectCreator(() => AccessoryFactory.createSecondWindBandana(), 1);
  }
}
