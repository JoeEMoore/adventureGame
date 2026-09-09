import type { IconRef } from '../utils/icons';

export abstract class Item {
  protected name: string;
  protected tier: number;
  protected icon: IconRef;

  constructor(name: string, tier: number, icon: IconRef = null) {
    this.name = name;
    this.tier = tier;
    this.icon = icon;
  }

  static mapTierToMultiplier(tier: number): number {
    switch (tier) {
      case 2:
        return 1.5;
      case 3:
        return 2.0;
      default:
        return 1.0;
    }
  }

  getIcon(): IconRef {
    return this.icon;
  }

  getName(): string {
    return this.name;
  }

  setName(name: string): void {
    this.name = name;
  }

  getTier(): number {
    return this.tier;
  }

  setTier(tier: number): void {
    this.tier = tier;
  }

  toString(): string {
    return this.name;
  }

  abstract getToolTipText(): string;
}
