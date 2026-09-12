import type { Creature } from '../creatures/Creature';
import type { Player } from '../creatures/Player';
import { DamageType } from '../damagetypes/DamageType';
import { WeaponFactory } from '../items/weapons/WeaponFactory';
import type { EliteTag } from './roomMeta';

/**
 * Situation modifiers for elites — change the problem shape, not just HP.
 * armored → physical resists / magic weakness
 * venomous → poison pressure weapon
 * swift → evasion puzzle
 * volatile → glass cannon
 * draining → ammo tax on fight start
 */
export function applyEliteTags(creature: Creature, tags: EliteTag[]): void {
  const mods = creature.getBaseModifiers();
  const name = creature.getName();
  if (!name.startsWith('Elite ')) {
    creature.setName(`Elite ${name}`);
  }

  for (const tag of tags) {
    switch (tag) {
      case 'armored':
        mods.multiplyResistance(DamageType.Blunt, 0.55);
        mods.multiplyResistance(DamageType.Slice, 0.65);
        mods.multiplyResistance(DamageType.Magic, 1.35);
        break;
      case 'venomous':
        creature.getInventory().addItem(WeaponFactory.createVenomFang());
        break;
      case 'swift':
        mods.addEvasion(0.2);
        break;
      case 'volatile':
        creature.setMaxHealth(Math.max(20, Math.floor(creature.getMaxHealth() * 0.65)));
        creature.setHealth(creature.getMaxHealth());
        mods.multiplyDamage(1.45);
        break;
      case 'draining':
        break;
    }
  }
  creature.syncTurnModifiersFromBase();
}

export function pickEliteTags(depthBand: number): EliteTag[] {
  const pool: EliteTag[] =
    depthBand <= 1
      ? ['swift', 'venomous']
      : depthBand === 2
        ? ['armored', 'venomous', 'swift', 'draining']
        : ['armored', 'volatile', 'draining', 'venomous', 'swift'];
  const count = depthBand >= 2 ? 2 : 1;
  const tags: EliteTag[] = [];
  const copy = [...pool];
  for (let i = 0; i < count && copy.length; i++) {
    const idx = Math.floor(Math.random() * copy.length);
    tags.push(copy.splice(idx, 1)[0]);
  }
  return tags;
}

/** Ammo scarcity tax: spend one use on a limited-use weapon if any. */
export function taxPlayerAmmo(player: Player): string | null {
  const weapons = player.getInventory().getWeapons();
  const limited = weapons.filter((w) => {
    const u = w.getMove().getUses();
    return u > 0 && w.getMove().getMaxUses() > 0;
  });
  if (limited.length === 0) return null;
  const w = limited[Math.floor(Math.random() * limited.length)];
  w.getMove().decrementUses();
  return `${w.getName()} lost a use (draining aura).`;
}

export function eliteHasDraining(tags: EliteTag[]): boolean {
  return tags.includes('draining');
}
