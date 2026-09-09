import type { Item } from '../../../items/Item';
import type { ObjectCreator, Pool } from '../../../pools/Pool';
import { Room } from '../Room';

export class ShopEntry {
  private itemCreator: ObjectCreator<Item>;
  private quantity: number;
  private price: number;

  constructor(itemCreator: ObjectCreator<Item>, quantity: number, price: number) {
    this.itemCreator = itemCreator;
    this.quantity = quantity;
    this.price = price;
  }

  getPrice(): number {
    return this.price;
  }

  getQuantity(): number {
    return this.quantity;
  }

  decreaseQuantity(): void {
    if (this.quantity > 0) this.quantity--;
  }

  getItem(): Item {
    return this.itemCreator();
  }

  getPreview(): Item {
    return this.itemCreator();
  }
}

export interface ShopInitializer {
  generateWeaponEntries(): ShopEntry[];
  generateConsumableEntries(): ShopEntry[];
}

export class DefaultShopInitializer implements ShopInitializer {
  private weaponPool: Pool<Item>;
  private consumablePool: Pool<Item>;
  private numWeapons: number;
  private quantityWeapons: number;
  private numConsumables: number;
  private quantityConsumables: number;

  constructor(
    weaponPool: Pool<Item>,
    consumablePool: Pool<Item>,
    numWeapons: number,
    quantityWeapons: number,
    numConsumables: number,
    quantityConsumables: number,
  ) {
    this.weaponPool = weaponPool;
    this.consumablePool = consumablePool;
    this.numWeapons = numWeapons;
    this.quantityWeapons = quantityWeapons;
    this.numConsumables = numConsumables;
    this.quantityConsumables = quantityConsumables;
  }

  generateWeaponEntries(): ShopEntry[] {
    const entries: ShopEntry[] = [];
    for (let i = 0; i < this.numWeapons; i++) {
      const creator = this.weaponPool.getCreator();
      const sample = creator();
      entries.push(new ShopEntry(creator, this.quantityWeapons, sample.getTier() * 60));
    }
    return entries;
  }

  generateConsumableEntries(): ShopEntry[] {
    const entries: ShopEntry[] = [];
    for (let i = 0; i < this.numConsumables; i++) {
      const creator = this.consumablePool.getCreator();
      const sample = creator();
      entries.push(new ShopEntry(creator, this.quantityConsumables, sample.getTier() * 20));
    }
    return entries;
  }
}

export class ShopRoom extends Room {
  private weaponEntries: ShopEntry[];
  private consumableEntries: ShopEntry[];

  constructor(shopInitializer: ShopInitializer) {
    super();
    this.weaponEntries = shopInitializer.generateWeaponEntries();
    this.consumableEntries = shopInitializer.generateConsumableEntries();
  }

  getWeaponEntries(): ShopEntry[] {
    return this.weaponEntries;
  }

  getConsumableEntries(): ShopEntry[] {
    return this.consumableEntries;
  }
}
