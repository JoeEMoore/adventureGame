import type { Item } from './Item';
import { Weapon } from './weapons/Weapon';
import { Consumable } from './consumables/Consumable';
import { Accessory } from './accessories/Accessory';

export class Inventory {
  private maxWeapons: number;
  private maxConsumables: number;
  private maxAccessories: number;
  private weapons: Weapon[] = [];
  private consumables: Consumable[] = [];
  private accessories: Accessory[] = [];

  constructor(maxWeapons = 1, maxConsumables = 0, maxAccessories = 0) {
    this.maxWeapons = maxWeapons;
    this.maxConsumables = maxConsumables;
    this.maxAccessories = maxAccessories;
  }

  getMaxWeapons(): number {
    return this.maxWeapons;
  }

  getMaxConsumables(): number {
    return this.maxConsumables;
  }

  getMaxAccessories(): number {
    return this.maxAccessories;
  }

  incrementMaxWeapons(): void {
    this.maxWeapons++;
  }

  incrementMaxConsumables(): void {
    this.maxConsumables++;
  }

  incrementMaxAccessories(): void {
    this.maxAccessories++;
  }

  getWeapons(): Weapon[] {
    return this.weapons;
  }

  getConsumables(): Consumable[] {
    return this.consumables;
  }

  getAccessories(): Accessory[] {
    return this.accessories;
  }

  getWeapon(slot: number): Weapon | null {
    return this.weapons[slot] ?? null;
  }

  getConsumable(slot: number): Consumable | null {
    return this.consumables[slot] ?? null;
  }

  getAccessory(slot: number): Accessory | null {
    return this.accessories[slot] ?? null;
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

  setAccessory(slot: number, a: Accessory): Accessory | null {
    if (slot >= this.maxAccessories) return a;
    if (slot < this.accessories.length) {
      const old = this.accessories[slot];
      this.accessories[slot] = a;
      return old;
    }
    this.accessories.push(a);
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
    if (item instanceof Accessory) {
      this.accessories.push(item);
      if (this.accessories.length > this.maxAccessories) {
        this.accessories.pop();
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

  removeAccessory(index: number): Accessory | null {
    if (index < 0 || index >= this.accessories.length) return null;
    return this.accessories.splice(index, 1)[0];
  }
}
