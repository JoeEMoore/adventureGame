import type { Item } from './Item';
import { Weapon } from './weapons/Weapon';
import { Consumable } from './consumables/Consumable';
import { Accessory } from './accessories/Accessory';

export class Inventory {
  private maxWeapons: number;
  private maxConsumables: number;
  /** Max simultaneously equipped accessories (carry bag is unlimited). */
  private maxAccessories: number;
  private weapons: Weapon[] = [];
  private consumables: Consumable[] = [];
  /** All owned accessories (unlimited). */
  private accessories: Accessory[] = [];
  /** Equipped subset of `accessories` (by reference). */
  private equippedAccessories: Accessory[] = [];

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

  /** All carried accessories (unlimited bag). */
  getAccessories(): Accessory[] {
    return this.accessories;
  }

  /** Currently equipped accessories (combat-active). */
  getEquippedAccessories(): Accessory[] {
    return this.equippedAccessories;
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

  isAccessoryEquipped(accessory: Accessory): boolean {
    return this.equippedAccessories.includes(accessory);
  }

  isAccessoryEquippedAt(bagIndex: number): boolean {
    const a = this.accessories[bagIndex];
    return !!a && this.isAccessoryEquipped(a);
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
    if (item instanceof Accessory) {
      this.accessories.push(item);
      // Auto-equip into a free slot when available.
      if (this.equippedAccessories.length < this.maxAccessories) {
        this.equippedAccessories.push(item);
      }
      return true;
    }
    return false;
  }

  /** Equip a carried accessory into an open slot. */
  equipAccessory(bagIndex: number): boolean {
    const a = this.accessories[bagIndex];
    if (!a) return false;
    if (this.isAccessoryEquipped(a)) return false;
    if (this.equippedAccessories.length >= this.maxAccessories) return false;
    this.equippedAccessories.push(a);
    return true;
  }

  /** Unequip a carried accessory (stays in bag). */
  unequipAccessory(bagIndex: number): boolean {
    const a = this.accessories[bagIndex];
    if (!a) return false;
    const eqIdx = this.equippedAccessories.indexOf(a);
    if (eqIdx < 0) return false;
    this.equippedAccessories.splice(eqIdx, 1);
    return true;
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
    const [removed] = this.accessories.splice(index, 1);
    const eqIdx = this.equippedAccessories.indexOf(removed);
    if (eqIdx >= 0) this.equippedAccessories.splice(eqIdx, 1);
    return removed;
  }
}
