import { DamageType } from '../../../damagetypes/DamageType';
import { Effect, type EffectsFactory } from '../../../effects/Effect';
import { Item } from '../../Item';

export class Move {
  private name: string;
  private damage: number;
  private damageType: DamageType;
  private maxUses: number;
  private uses: number;
  private accuracy: number;
  private targetAllies: boolean;
  private tier = 1;
  private effectsFactory: EffectsFactory = () => [];

  constructor(
    name: string,
    damage: number,
    damageType: DamageType,
    maxUses: number,
    accuracy: number,
    targetAllies: boolean,
  ) {
    this.name = name;
    this.damage = damage;
    this.damageType = damageType;
    this.maxUses = maxUses;
    this.uses = maxUses;
    this.accuracy = accuracy;
    this.targetAllies = targetAllies;
  }

  getName(): string {
    return this.name;
  }

  getDamage(): number {
    return this.damage * Item.mapTierToMultiplier(this.tier);
  }

  getDamageType(): DamageType {
    return this.damageType;
  }

  getMaxUses(): number {
    return this.maxUses;
  }

  getUses(): number {
    return this.uses;
  }

  getAccuracy(): number {
    return this.accuracy;
  }

  targetsAllies(): boolean {
    return this.targetAllies;
  }

  getTier(): number {
    return this.tier;
  }

  setTier(tier: number): void {
    this.tier = tier;
  }

  decrementUses(): void {
    if (this.uses > 0) this.uses--;
  }

  addUses(n: number): void {
    if (this.maxUses <= 0) return;
    this.uses = Math.min(this.maxUses, this.uses + n);
  }

  resetUses(): void {
    this.uses = this.maxUses;
  }

  setEffects(factory: EffectsFactory): void {
    this.effectsFactory = factory;
  }

  createEffects(): Effect[] {
    const effects = this.effectsFactory();
    const mult = Item.mapTierToMultiplier(this.tier);
    for (const e of effects) {
      e.multiplyEffect(mult);
    }
    return effects;
  }
}
