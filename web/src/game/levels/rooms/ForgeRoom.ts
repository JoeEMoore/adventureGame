import { Room } from './Room';
import type { Weapon } from '../../items/weapons/Weapon';

/** One per floor — upgrade weapons and fuse accessories. */
export class ForgeRoom extends Room {
  private upgradedWeapons = new Set<Weapon>();

  constructor() {
    super();
    this.setRole('forge');
  }

  hasUpgradedWeapon(weapon: Weapon): boolean {
    return this.upgradedWeapons.has(weapon);
  }

  markWeaponUpgraded(weapon: Weapon): void {
    this.upgradedWeapons.add(weapon);
  }
}
