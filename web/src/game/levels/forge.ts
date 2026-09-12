import type { Weapon } from '../items/weapons/Weapon';
import { Item } from '../items/Item';

/** Shop / forge price for a weapon at its current tier. */
export function weaponGoldCost(weapon: Weapon): number {
  return weapon.getTier() * 60;
}

export const MAX_WEAPON_TIER = 3;

export function canUpgradeWeaponTier(weapon: Weapon): boolean {
  return weapon.getTier() < MAX_WEAPON_TIER;
}

/** Raise weapon (and its move) by one tier. */
export function upgradeWeaponOneTier(weapon: Weapon): void {
  const next = Math.min(MAX_WEAPON_TIER, weapon.getTier() + 1);
  weapon.setTier(next);
  weapon.getMove().setTier(next);
}

export function tierDamageMultiplier(tier: number): number {
  return Item.mapTierToMultiplier(tier);
}
