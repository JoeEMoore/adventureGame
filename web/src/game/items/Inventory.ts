import type { Item } from './Item';
import { Weapon } from './weapons/Weapon';
import { Consumable } from './consumables/Consumable';

export class Inventory {
  private maxWeapons: number;
  private maxConsumables: number;
  private weapons: Weapon[] = [];
  private consumables: Consumable[] = [];

  constructor(maxWeapons = 1, maxConsumables = 0) {
    this.maxWeapons = maxWeapons;
    this.maxConsumables = maxConsumables;
  }

  getMaxWeapons(): number {
    return this.maxWeapons;
  }

  getMaxConsumables(): number {
    return this.maxConsumables;
  }

  incrementMaxWeapons(): void {
    this.maxWeapons++;
  }

  incrementMaxConsumables(): void {
    this.maxConsumables++;
  }

  getWeapons(): Weapon[] {
    return this.weapons;
  }

  getConsumables(): Consumable[] {
    return this.consumables;
  }

  getWeapon(slot: number): Weapon | null {
    return this.weapons[slot] ?? null;
  }

  getConsumable(slot: number): Consumable | null {
    return this.consumables[slot] ?? null;
  }

  setWeapon(slot: number, w: Weapon): Weapon | null {
    if (slot >= this.maxWeapons) return w;
    if (slot < this.weapons.length) {
      const old = this.weapons[slot];
      this.weapons[slot] = w;
      return old;
    }
    this.weapons.push(w);
    return null;
  }

  setConsumable(slot: number, c: Consumable): Consumable | null {
    if (slot >= this.maxConsumables) return c;
    if (slot < this.consumables.length) {
      const old = this.consumables[slot];
      this.consumables[slot] = c;
      return old;
    }
    this.consumables.push(c);
    return null;
  }

  addItem(item: Item): boolean {
    if (item instanceof Weapon) {
      this.weapons.push(item);
      if (this.weapons.length > this.maxWeapons) {
        this.weapons.pop();
        return false;
      }
      return true;
    }
    if (item instanceof Consumable) {
      this.consumables.push(item);
      if (this.consumables.length > this.maxConsumables) {
        this.consumables.pop();
        return false;
      }
      return true;
    }
    return false;
  }

  removeWeapon(index: number): Weapon | null {
    if (index < 0 || index >= this.weapons.length) return null;
    return this.weapons.splice(index, 1)[0];
  }

  removeConsumable(index: number): Consumable | null {
    if (index < 0 || index >= this.consumables.length) return null;
    return this.consumables.splice(index, 1)[0];
  }
}
