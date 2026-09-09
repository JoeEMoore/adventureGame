import { DamageType, getDamageTypes } from '../damagetypes/DamageType';

export class CreatureModifiers {
  damage: number;
  evasion: number;
  resistances: Map<DamageType, number>;

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
    return new CreatureModifiers(this.damage, this.evasion, queue);
  }
}
