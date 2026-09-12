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
  generateAccessoryEntries(): ShopEntry[];
}

export class DefaultShopInitializer implements ShopInitializer {
  private weaponPool: Pool<Item>;
  private consumablePool: Pool<Item>;
  private accessoryPool: Pool<Item>;
  private numWeapons: number;
  private quantityWeapons: number;
  private numConsumables: number;
  private quantityConsumables: number;
  private numAccessories: number;
  private quantityAccessories: number;
  private accessoryPrice: number;

  constructor(
    weaponPool: Pool<Item>,
    consumablePool: Pool<Item>,
    accessoryPool: Pool<Item>,
    numWeapons: number,
    quantityWeapons: number,
    numConsumables: number,
    quantityConsumables: number,
    numAccessories = 2,
    quantityAccessories = 1,
    accessoryPrice = 60,
  ) {
    this.weaponPool = weaponPool;
    this.consumablePool = consumablePool;
    this.accessoryPool = accessoryPool;
    this.numWeapons = numWeapons;
    this.quantityWeapons = quantityWeapons;
    this.numConsumables = numConsumables;
    this.quantityConsumables = quantityConsumables;
    this.numAccessories = numAccessories;
    this.quantityAccessories = quantityAccessories;
    this.accessoryPrice = accessoryPrice;
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

  generateAccessoryEntries(): ShopEntry[] {
    const entries: ShopEntry[] = [];
    for (let i = 0; i < this.numAccessories; i++) {
      const creator = this.accessoryPool.getCreator();
      entries.push(new ShopEntry(creator, this.quantityAccessories, this.accessoryPrice));
    }
    return entries;
  }
}

export class ShopRoom extends Room {
  private weaponEntries: ShopEntry[];
  private consumableEntries: ShopEntry[];
  private accessoryEntries: ShopEntry[];

  constructor(shopInitializer: ShopInitializer) {
    super();
    this.role = 'shop';
    this.weaponEntries = shopInitializer.generateWeaponEntries();
    this.consumableEntries = shopInitializer.generateConsumableEntries();
    this.accessoryEntries = shopInitializer.generateAccessoryEntries();
  }

  getWeaponEntries(): ShopEntry[] {
    return this.weaponEntries;
  }

  getConsumableEntries(): ShopEntry[] {
    return this.consumableEntries;
  }

  getAccessoryEntries(): ShopEntry[] {
    return this.accessoryEntries;
  }
}
