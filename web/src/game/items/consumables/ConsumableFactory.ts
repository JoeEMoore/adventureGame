import {
  AntidoteEffect,
  DamageEffect,
  HealEffect,
  PoisonEffect,
  RefillEffect,
  ResistanceEffect,
  StrengthEffect,
} from '../../effects/effects';
import { potionSprite } from '../../utils/icons';
import { Consumable } from './Consumable';

export const ConsumableFactory = {
  createSmallHealthPotion(): Consumable {
    return new Consumable('Small Health Potion', 1, () => [new HealEffect(25)], true, potionSprite(8, 0));
  },
  createMediumHealthPotion(): Consumable {
    return new Consumable('Medium Health Potion', 2, () => [new HealEffect(50)], true, potionSprite(5, 0));
  },
  createLargeHealthPotion(): Consumable {
    return new Consumable('Large Health Potion', 3, () => [new HealEffect(75)], true, potionSprite(2, 0));
  },
  createSmallPoisonPotion(): Consumable {
    return new Consumable('Small Poison Potion', 1, () => [new PoisonEffect(2)], false, potionSprite(8, 3));
  },
  createMediumPoisonPotion(): Consumable {
    return new Consumable('Medium Poison Potion', 2, () => [new PoisonEffect(3)], false, potionSprite(5, 3));
  },
  createLargePoisonPotion(): Consumable {
    return new Consumable('Large Poison Potion', 3, () => [new PoisonEffect(5)], false, potionSprite(2, 3));
  },
  createSmallDamagePotion(): Consumable {
    return new Consumable('Small Damage Potion', 1, () => [new DamageEffect(1, 10)], false, potionSprite(8, 2));
  },
  createMediumDamagePotion(): Consumable {
    return new Consumable('Medium Damage Potion', 2, () => [new DamageEffect(1, 15)], false, potionSprite(5, 2));
  },
  createLargeDamagePotion(): Consumable {
    return new Consumable('Large Damage Potion', 3, () => [new DamageEffect(1, 25)], false, potionSprite(2, 2));
  },
  createStrengthPotion(): Consumable {
    return new Consumable('Strength Potion', 3, () => [new StrengthEffect(2, 2)], true, potionSprite(2, 4));
  },
  createResistancePotion(): Consumable {
    return new Consumable('Resistance Potion', 3, () => [new ResistanceEffect(2, 0.5)], true, potionSprite(2, 7));
  },
  createWeaponRefillPotion(): Consumable {
    return new Consumable('Weapon Refill Potion', 2, () => [new RefillEffect(1)], true, potionSprite(2, 8));
  },
  createAntidote(): Consumable {
    return new Consumable('Antidote', 1, () => [new AntidoteEffect()], true, potionSprite(8, 1));
  },
};
