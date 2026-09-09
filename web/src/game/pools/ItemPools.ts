import { WeaponFactory } from '../items/weapons/WeaponFactory';
import { ConsumableFactory } from '../items/consumables/ConsumableFactory';
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
  }
}
