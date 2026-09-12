import { DamageType, getDamageTypes } from '../damagetypes/DamageType';

export class CreatureModifiers {
  damage: number;
  evasion: number;
  resistances: Map<DamageType, number>;
  /** Guaranteed skip of this creature's action (knockout). */
  actionBlocked = false;
  /** Chance (0–1) to fail the action entirely (shock). */
  actionFailChance = 0;
  /** Extra miss chance when this creature attacks (iced). */
  missChanceBonus = 0;

  constructor(damage: number, evasion: number, resistanceQueue: number[]) {
    this.damage = damage;
    this.evasion = evasion;
    this.resistances = new Map();
    const types = getDamageTypes();
    for (let i = 0; i < types.length; i++) {
      this.resistances.set(types[i], resistanceQueue[i] ?? 1.0);
    }
  }

  getDamage(): number {
    return this.damage;
  }

  getEvasion(): number {
    return this.evasion;
  }

  getResistance(dt: DamageType): number {
    return this.resistances.get(dt) ?? 1.0;
  }

  isActionBlocked(): boolean {
    return this.actionBlocked;
  }

  setActionBlocked(v: boolean): void {
    this.actionBlocked = v;
  }

  getActionFailChance(): number {
    return this.actionFailChance;
  }

  setActionFailChance(n: number): void {
    this.actionFailChance = Math.max(this.actionFailChance, n);
  }

  getMissChanceBonus(): number {
    return this.missChanceBonus;
  }

  addMissChanceBonus(n: number): void {
    this.missChanceBonus += n;
  }

  addDamage(n: number): void {
    this.damage += n;
  }

  multiplyDamage(n: number): void {
    this.damage *= n;
  }

  setDamage(n: number): void {
    this.damage = n;
  }

  addEvasion(n: number): void {
    this.evasion += n;
  }

  multiplyEvasion(n: number): void {
    this.evasion *= n;
  }

  setEvasion(n: number): void {
    this.evasion = n;
  }

  multiplyResistance(dt: DamageType, n: number): boolean {
    if (!this.resistances.has(dt)) return false;
    this.resistances.set(dt, this.getResistance(dt) * n);
    return true;
  }

  setResistance(dt: DamageType, n: number): void {
    this.resistances.set(dt, n);
  }

  clone(): CreatureModifiers {
    const queue = getDamageTypes().map((t) => this.getResistance(t));
    const copy = new CreatureModifiers(this.damage, this.evasion, queue);
    copy.actionBlocked = this.actionBlocked;
    copy.actionFailChance = this.actionFailChance;
    copy.missChanceBonus = this.missChanceBonus;
    return copy;
  }
}
