import { Creature } from './Creature';
import { CreatureModifiers } from './CreatureModifiers';
import { Inventory } from '../items/Inventory';
import type { IconRef } from '../utils/icons';
import { Coordinate } from '../levels/Coordinate';
import type { PlayerClass } from './playerClass';
import type { DamageType } from '../damagetypes/DamageType';
import {
  emptyTypeDamageBonuses,
  type DescendBuffId,
} from './descendBuffs';

export class Player extends Creature {
  private gold = 0;
  private keys = 0;
  private playerClass: PlayerClass | null = null;
  /** Knight passive: consecutive landed Slice hits. */
  private consecutiveSlashHits = 0;
  /** Knight passive: whether Bleed already procced on this slash streak. */
  private slashBleedProcced = false;
  /** Permanent run accuracy from descend choices (flat 0–1). */
  private descendAccuracyBonus = 0;
  /** Permanent run per-type damage bonuses from descend (additive, e.g. 0.1 = +10%). */
  private descendTypeDamageBonus = emptyTypeDamageBonuses();
  private descendBuffsChosen: DescendBuffId[] = [];
  /** Max-HP cuts applied by each equipped Vampiric Fang (LIFO). */
  private vampiricMaxHpCuts: number[] = [];

  constructor(
    name: string,
    maxHealth: number,
    modifiers: CreatureModifiers,
    inventory: Inventory,
    icon: IconRef = null,
  ) {
    super(name, maxHealth, modifiers, inventory, icon, []);
    this.currentPosition = new Coordinate(0, 0);
  }

  getGold(): number {
    return this.gold;
  }

  addGold(n: number): void {
    this.gold += n;
  }

  getKeys(): number {
    return this.keys;
  }

  addKeys(n: number): void {
    this.keys += n;
  }

  /** Returns false if not enough keys. */
  spendKey(): boolean {
    if (this.keys <= 0) return false;
    this.keys -= 1;
    return true;
  }

  /** Typo preserved from Java Player.subractGold */
  subractGold(n: number): void {
    this.gold -= n;
  }

  getPlayerClass(): PlayerClass | null {
    return this.playerClass;
  }

  setPlayerClass(cls: PlayerClass): void {
    this.playerClass = cls;
  }

  getConsecutiveSlashHits(): number {
    return this.consecutiveSlashHits;
  }

  setConsecutiveSlashHits(n: number): void {
    this.consecutiveSlashHits = Math.max(0, n);
  }

  hasSlashBleedProcced(): boolean {
    return this.slashBleedProcced;
  }

  setSlashBleedProcced(v: boolean): void {
    this.slashBleedProcced = v;
  }

  resetConsecutiveSlashHits(): void {
    this.consecutiveSlashHits = 0;
    this.slashBleedProcced = false;
  }

  getDescendAccuracyBonus(): number {
    return this.descendAccuracyBonus;
  }

  addDescendAccuracyBonus(n: number): void {
    this.descendAccuracyBonus += n;
  }

  getDescendTypeDamageMultiplier(dt: DamageType): number {
    return 1 + (this.descendTypeDamageBonus.get(dt) ?? 0);
  }

  addDescendTypeDamageBonus(dt: DamageType, n: number): void {
    const prev = this.descendTypeDamageBonus.get(dt) ?? 0;
    this.descendTypeDamageBonus.set(dt, prev + n);
  }

  getDescendBuffsChosen(): DescendBuffId[] {
    return [...this.descendBuffsChosen];
  }

  recordDescendBuff(id: DescendBuffId): void {
    this.descendBuffsChosen.push(id);
  }

  pushVampiricMaxHpCut(cut: number): void {
    this.vampiricMaxHpCuts.push(cut);
  }

  popVampiricMaxHpCut(): number {
    return this.vampiricMaxHpCuts.pop() ?? 0;
  }
}
