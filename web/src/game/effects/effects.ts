import { Effect } from './Effect';
import type { Creature } from '../creatures/Creature';
import { DamageType } from '../damagetypes/DamageType';
import { getDamageTypes } from '../damagetypes/DamageType';
import { roundDouble } from '../utils/DoubleUtils';

export class HealEffect extends Effect {
  healAmount: number;

  constructor(healAmountOrTurns: number, healAmount?: number) {
    if (healAmount === undefined) {
      super(1, true);
      this.healAmount = healAmountOrTurns;
    } else {
      super(healAmountOrTurns, true);
      this.healAmount = healAmount;
    }
    this.name = 'Heal';
  }

  multiplyEffect(multiplier: number): void {
    this.healAmount *= multiplier;
  }

  protected apply(creature: Creature): string {
    const gained = creature.addHealth(Math.trunc(this.healAmount));
    return `${creature.getName()} gained ${roundDouble(gained)} health`;
  }
}

export class DamageEffect extends Effect {
  damageAmount: number;

  constructor(turns: number, damageAmount: number) {
    super(turns, true);
    this.damageAmount = damageAmount;
    this.name = 'Damage';
  }

  multiplyEffect(multiplier: number): void {
    this.damageAmount *= multiplier;
  }

  protected apply(creature: Creature): string {
    const dealt = creature.applyDamage(this.damageAmount, DamageType.Pure);
    return `${creature.getName()} was dealt ${roundDouble(dealt)} damage.`;
  }
}

export class PoisonEffect extends Effect {
  static PERCENT_DAMAGE = 0.05;

  constructor(turns: number) {
    super(turns, false);
    this.name = 'Poison';
  }

  multiplyEffect(multiplier: number): void {
    this.turns = Math.floor(this.turns * multiplier);
  }

  protected apply(creature: Creature): string {
    const dealt = creature.applyPercentDamage(PoisonEffect.PERCENT_DAMAGE, DamageType.Pure);
    return `${creature.getName()} was poisoned for ${roundDouble(dealt)} damage`;
  }
}

export class StrengthEffect extends Effect {
  damageMultiplier: number;

  constructor(turns: number, damageMultiplier: number) {
    super(turns, true);
    this.damageMultiplier = damageMultiplier;
    this.name = 'Strength';
  }

  multiplyEffect(multiplier: number): void {
    this.damageMultiplier *= multiplier;
  }

  protected apply(creature: Creature): string {
    creature.getTurnModifiers().multiplyDamage(this.damageMultiplier);
    return `${creature.getName()} is feeling ${roundDouble(this.damageMultiplier)}x stronger`;
  }
}

export class ResistanceEffect extends Effect {
  resistanceMultiplier: number;

  constructor(turns: number, resistanceMultiplier: number) {
    super(turns, true);
    this.resistanceMultiplier = resistanceMultiplier;
    this.name = 'Damage Resistance';
  }

  multiplyEffect(multiplier: number): void {
    this.resistanceMultiplier *= multiplier;
  }

  protected apply(creature: Creature): string {
    for (const dt of getDamageTypes()) {
      creature.getTurnModifiers().multiplyResistance(dt, this.resistanceMultiplier);
    }
    return `${creature.getName()} is ${roundDouble(1 / this.resistanceMultiplier)}x as resilient.`;
  }
}

export class RefillEffect extends Effect {
  constructor(turns: number) {
    super(turns, true);
    this.name = 'Refill Moves';
  }

  multiplyEffect(_multiplier: number): void {
    throw new Error('UnsupportedOperationException');
  }

  protected apply(creature: Creature): string {
    for (const weapon of creature.getInventory().getWeapons()) {
      weapon.getMove().resetUses();
    }
    return `${creature.getName()} refilled all of their weapon uses!`;
  }
}
