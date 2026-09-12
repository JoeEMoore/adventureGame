import type { DamageType } from '../damagetypes/DamageType';
import type { Effect } from '../effects/Effect';
import { Inventory } from '../items/Inventory';
import type { IconRef } from '../utils/icons';
import { CreatureModifiers } from './CreatureModifiers';
import type { Coordinate } from '../levels/Coordinate';

export class Creature {
  protected name: string;
  protected maxHealth: number;
  protected health: number;
  protected baseModifiers: CreatureModifiers;
  protected turnModifiers: CreatureModifiers;
  protected inventory: Inventory;
  protected icon: IconRef;
  protected weaponWeights: number[];
  protected effects: Effect[] = [];
  protected currentPosition: Coordinate | null = null;

  constructor(
    name: string,
    maxHealth: number,
    baseModifiers: CreatureModifiers,
    inventory: Inventory,
    icon: IconRef = null,
    weaponWeights: number[] = [],
  ) {
    this.name = name;
    this.maxHealth = maxHealth;
    this.health = maxHealth;
    this.baseModifiers = baseModifiers;
    this.turnModifiers = baseModifiers.clone();
    this.inventory = inventory;
    this.icon = icon;
    this.weaponWeights = [...weaponWeights];
  }

  getName(): string {
    return this.name;
  }

  setName(name: string): void {
    this.name = name;
  }

  getHealth(): number {
    return this.health;
  }

  setHealth(health: number): void {
    this.health = Math.max(0, Math.min(this.maxHealth, health));
  }

  getMaxHealth(): number {
    return this.maxHealth;
  }

  setMaxHealth(maxHealth: number): void {
    this.maxHealth = maxHealth;
  }

  /** Re-clone turn mods after mutating base resists/stats. */
  syncTurnModifiersFromBase(): void {
    this.turnModifiers = this.baseModifiers.clone();
  }

  getBaseModifiers(): CreatureModifiers {
    return this.baseModifiers;
  }

  getTurnModifiers(): CreatureModifiers {
    return this.turnModifiers;
  }

  getInventory(): Inventory {
    return this.inventory;
  }

  getIcon(): IconRef {
    return this.icon;
  }

  getWeaponWeights(): number[] {
    return this.weaponWeights;
  }

  getEffects(): Effect[] {
    return this.effects;
  }

  getCurrentPosition(): Coordinate | null {
    return this.currentPosition;
  }

  setCurrentPosition(pos: Coordinate): void {
    this.currentPosition = pos;
  }

  addEffect(e: Effect): string {
    let message = '';
    if (e.isAppliedInstantly) {
      message = e.applyEffect(this);
    } else {
      message = `Added ${e.toString()} to ${this.name}`;
    }
    if (e.getTurns() <= 0) {
      return message;
    }
    this.effects.push(e);
    return message;
  }

  clearEffects(): void {
    this.effects = [];
    this.turnModifiers = this.baseModifiers.clone();
  }

  removeEffectsByName(name: string): number {
    const before = this.effects.length;
    this.effects = this.effects.filter((e) => e.getName() !== name);
    return before - this.effects.length;
  }

  calculateEffects(): string {
    this.turnModifiers = this.baseModifiers.clone();
    const messages: string[] = [];
    const remaining: Effect[] = [];
    for (const e of this.effects) {
      messages.push(e.applyEffect(this));
      if (e.getTurns() > 0) remaining.push(e);
    }
    this.effects = remaining;
    return messages.filter(Boolean).join(', ');
  }

  addHealth(amount: number): number {
    const before = this.health;
    this.health = Math.min(this.maxHealth, this.health + amount);
    return this.health - before;
  }

  applyDamage(dmg: number, type: DamageType): number {
    const dealt = dmg * this.turnModifiers.getResistance(type);
    this.health = Math.max(0, this.health - dealt);
    return dealt;
  }

  applyPercentDamage(pct: number, type: DamageType): number {
    return this.applyDamage(this.maxHealth * pct, type);
  }

  isAlive(): boolean {
    return this.health > 0;
  }
}
