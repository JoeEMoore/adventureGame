import type { Accessory } from './Accessory';
import { AccessoryFactory } from './AccessoryFactory';
import type { AccessoryKind } from './Accessory';
import type { Creature } from '../../creatures/Creature';
import type { Player } from '../../creatures/Player';
import { DamageType } from '../../damagetypes/DamageType';
import {
  BurnEffect,
  IcedEffect,
  PoisonEffect,
  ShockEffect,
} from '../../effects/effects';

const ACCESSORY_DROP_CHANCE = 0.15;
const SHIELD_BLOCK_CHANCE = 0.1;
const WRAPS_BLUNT_BONUS = 0.1;
const GRIPS_ACCURACY_BONUS = 0.1;
const GRIPS_BLUNT_ACCURACY_BONUS = 0.15;
const VENOM_CHANCE = 0.2;
const VENOM_TURNS = 3;
const RING_CHANCE = 0.1;
const RING_TURNS = 3;
const LUCKY_COIN_GOLD_BONUS = 0.2;

export function countEquipped(player: Player, kind: AccessoryKind): number {
  return player.getInventory().getAccessories().filter((a) => a.getKind() === kind).length;
}

/** True if any equipped Shield blocks the incoming hit. */
export function rollAccessoryBlock(player: Player): boolean {
  const shields = countEquipped(player, 'shield');
  for (let i = 0; i < shields; i++) {
    if (Math.random() < SHIELD_BLOCK_CHANCE) return true;
  }
  return false;
}

/** Multiplier for outgoing Blunt damage from Wraps. */
export function wrapsBluntMultiplier(player: Player): number {
  return 1 + WRAPS_BLUNT_BONUS * countEquipped(player, 'wraps');
}

/** Flat accuracy bonus from Grips (+10% all; +15% Blunt). Stacks per equipped. */
export function gripsAccuracyBonus(player: Player, damageType: DamageType): number {
  const n = countEquipped(player, 'grips');
  if (n <= 0) return 0;
  const per = isBluntDamage(damageType) ? GRIPS_BLUNT_ACCURACY_BONUS : GRIPS_ACCURACY_BONUS;
  return per * n;
}

/** Incoming Blunt taken multiplier from Iron Band. */
export function ironBandBluntTakenMultiplier(player: Player): number {
  const n = countEquipped(player, 'ironBand');
  return Math.max(0.4, 1 - 0.2 * n);
}

/** Gold lock discount from Lockpick accessories. */
export function lockpickGoldMultiplier(player: Player): number {
  const n = countEquipped(player, 'lockpick');
  return Math.max(0.25, 1 - 0.25 * n);
}

/** Restore one use on a limited weapon at fight start. */
export function applyScrapPouchOnFightStart(player: Player): string | null {
  if (countEquipped(player, 'scrapPouch') <= 0) return null;
  const weapons = player.getInventory().getWeapons();
  const candidates = weapons.filter((w) => {
    const m = w.getMove();
    return m.getMaxUses() > 0 && m.getUses() < m.getMaxUses();
  });
  if (candidates.length === 0) return null;
  const w = candidates[Math.floor(Math.random() * candidates.length)];
  w.getMove().addUses(1);
  return `Scrap Pouch restored a use on ${w.getName()}.`;
}

/** Gold multiplier from Pirate Coin on kill. */
export function pirateCoinGoldMultiplier(player: Player): number {
  return 1 + LUCKY_COIN_GOLD_BONUS * countEquipped(player, 'pirateCoin');
}

/** On-hit accessory procs (venom + elemental rings). */
export function applyAccessoryOnHit(
  player: Player,
  target: Creature,
  procs: string[],
): string[] {
  const msgs: string[] = [];

  for (let i = 0; i < countEquipped(player, 'venomFlask'); i++) {
    if (Math.random() < VENOM_CHANCE) {
      msgs.push(target.addEffect(new PoisonEffect(VENOM_TURNS)));
      procs.push('POISON');
    }
  }

  for (let i = 0; i < countEquipped(player, 'ringOfFire'); i++) {
    if (Math.random() < RING_CHANCE) {
      msgs.push(target.addEffect(new BurnEffect(RING_TURNS, 10)));
      procs.push('BURN');
    }
  }

  for (let i = 0; i < countEquipped(player, 'ringOfIce'); i++) {
    if (Math.random() < RING_CHANCE) {
      msgs.push(target.addEffect(new IcedEffect(RING_TURNS, 0.25)));
      procs.push('ICED');
    }
  }

  for (let i = 0; i < countEquipped(player, 'ringOfElectricity'); i++) {
    if (Math.random() < RING_CHANCE) {
      msgs.push(target.addEffect(new ShockEffect(RING_TURNS, 0.5)));
      procs.push('SHOCK');
    }
  }

  return msgs;
}

/** 15% chance to drop a random accessory (equal odds). */
export function rollAccessoryDrop(): Accessory | null {
  if (Math.random() >= ACCESSORY_DROP_CHANCE) return null;
  return AccessoryFactory.createRandom();
}

export function isBluntDamage(damageType: DamageType): boolean {
  return damageType === DamageType.Blunt;
}
