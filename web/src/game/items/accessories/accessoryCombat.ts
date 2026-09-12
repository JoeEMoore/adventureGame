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
  ResistanceEffect,
  ShockEffect,
} from '../../effects/effects';
import type { Fight } from '../../Fight';
import type { Weapon } from '../weapons/Weapon';
import { roundDouble } from '../../utils/DoubleUtils';

const ACCESSORY_DROP_CHANCE = 0.15;
const SHIELD_BLOCK_CHANCE = 0.1;
const WRAPS_BLUNT_BONUS = 0.1;
const GRIPS_ACCURACY_BONUS = 0.1;
const GRIPS_BLUNT_ACCURACY_BONUS = 0.15;
const DOUBLE_SHOT_DAMAGE_MULT = 2;
const DOUBLE_SHOT_ACCURACY_PENALTY = 0.1;
const VENOM_CHANCE = 0.2;
const VENOM_TURNS = 3;
const RING_CHANCE = 0.1;
const RING_TURNS = 3;
const LUCKY_COIN_GOLD_BONUS = 0.2;

const ECHO_ACCURACY_BONUS = 0.25;
const FOCUS_CRYSTAL_DAMAGE_MULT = 1.4;
const THORN_REFLECT_RATIO = 0.2;
const VAMPIRIC_HEAL_RATIO = 0.1;
const VAMPIRIC_MAX_HP_PENALTY = 0.1;
const RITUAL_EXTEND_CHANCE = 0.15;
const QUICKSTEP_DAMAGE_MULT = 1.25;
const GLASS_DICE_DAMAGE_BONUS = 0.2;
const GLASS_DICE_ACCURACY_PENALTY = 0.1;
const SECOND_WIND_HP_FRACTION = 0.25;
const SECOND_WIND_RESIST_TURNS = 2;
const SECOND_WIND_RESIST_MULT = 0.5;

const RITUAL_STATUS_NAMES = ['Burn', 'Shock', 'Iced'] as const;

export function countEquipped(player: Player, kind: AccessoryKind): number {
  return player.getInventory().getEquippedAccessories().filter((a) => a.getKind() === kind).length;
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

export function isProjectileDamage(damageType: DamageType): boolean {
  return damageType === DamageType.Projectile;
}

/** True if Double Shot should modify this projectile attack. */
export function hasDoubleShot(player: Player): boolean {
  return countEquipped(player, 'doubleShot') > 0;
}

/** Projectile damage multiplier from Double Shot (2× per equipped). */
export function doubleShotDamageMultiplier(player: Player, damageType: DamageType): number {
  if (!isProjectileDamage(damageType)) return 1;
  const n = countEquipped(player, 'doubleShot');
  if (n <= 0) return 1;
  return Math.pow(DOUBLE_SHOT_DAMAGE_MULT, n);
}

/** Accuracy penalty from Double Shot on projectile attacks (−10% each). */
export function doubleShotAccuracyPenalty(player: Player, damageType: DamageType): number {
  if (!isProjectileDamage(damageType)) return 0;
  return DOUBLE_SHOT_ACCURACY_PENALTY * countEquipped(player, 'doubleShot');
}

/** Extra ammo spends from Double Shot (1 extra use per equipped, on limited weapons). */
export function doubleShotExtraUses(player: Player, damageType: DamageType): number {
  if (!isProjectileDamage(damageType)) return 0;
  return countEquipped(player, 'doubleShot');
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

export function glassDiceDamageMultiplier(player: Player): number {
  return 1 + GLASS_DICE_DAMAGE_BONUS * countEquipped(player, 'glassDice');
}

export function glassDiceAccuracyPenalty(player: Player): number {
  return GLASS_DICE_ACCURACY_PENALTY * countEquipped(player, 'glassDice');
}

export function hasOathMedallion(player: Player): boolean {
  return countEquipped(player, 'oathMedallion') > 0;
}

/** Knight streak thresholds: chance hit / guaranteed hit (1-based streak counts). */
export function knightBleedStreakThresholds(player: Player): { chanceAt: number; guaranteeAt: number } {
  if (hasOathMedallion(player)) return { chanceAt: 1, guaranteeAt: 2 };
  return { chanceAt: 2, guaranteeAt: 3 };
}

export function consumeEchoAccuracyBonus(fight: Fight, player: Player): number {
  if (!fight.echoAccuracyPending || countEquipped(player, 'echoCharm') <= 0) return 0;
  fight.echoAccuracyPending = false;
  return ECHO_ACCURACY_BONUS;
}

export function armEchoCharmOnMiss(fight: Fight, player: Player): void {
  if (countEquipped(player, 'echoCharm') > 0) {
    fight.echoAccuracyPending = true;
  }
}

export function focusCrystalDamageMultiplier(fight: Fight, player: Player): number {
  if (fight.focusCrystalUsed || countEquipped(player, 'focusCrystal') <= 0) return 1;
  return FOCUS_CRYSTAL_DAMAGE_MULT;
}

export function markFocusCrystalUsed(fight: Fight, player: Player): void {
  if (countEquipped(player, 'focusCrystal') > 0) {
    fight.focusCrystalUsed = true;
  }
}

export function consumeQuickstepDamageBonus(fight: Fight, player: Player): number {
  if (!fight.quickstepDamagePending || countEquipped(player, 'quickstepBoots') <= 0) return 1;
  fight.quickstepDamagePending = false;
  return QUICKSTEP_DAMAGE_MULT;
}

export function armQuickstepOnEnemyMiss(fight: Fight, player: Player): void {
  if (countEquipped(player, 'quickstepBoots') > 0) {
    fight.quickstepDamagePending = true;
  }
}

export function applyThornCollarReflect(
  player: Player,
  attacker: Creature,
  damageTaken: number,
  procs: string[],
): string | null {
  if (damageTaken <= 0 || countEquipped(player, 'thornCollar') <= 0) return null;
  const n = countEquipped(player, 'thornCollar');
  const reflected = damageTaken * THORN_REFLECT_RATIO * n;
  const dealt = attacker.applyDamage(reflected, DamageType.Pure);
  if (dealt <= 0) return null;
  procs.push('THORN');
  return `Thorn Collar reflected ${roundDouble(dealt)} damage.`;
}

export function applyVampiricHeal(
  player: Player,
  damageDealt: number,
  procs: string[],
): string | null {
  const n = countEquipped(player, 'vampiricFang');
  if (n <= 0 || damageDealt <= 0) return null;
  const heal = Math.max(1, Math.floor(damageDealt * VAMPIRIC_HEAL_RATIO * n));
  const gained = player.addHealth(heal);
  if (gained <= 0) return null;
  procs.push('LEECH');
  return `Vampiric Fang healed ${roundDouble(gained)} HP.`;
}

/** Cut max HP when equipping a Vampiric Fang. */
export function applyVampiricFangEquip(player: Player): void {
  const prev = player.getMaxHealth();
  const cut = Math.max(1, Math.floor(prev * VAMPIRIC_MAX_HP_PENALTY));
  const next = Math.max(1, prev - cut);
  player.setMaxHealth(next);
  if (player.getHealth() > next) player.setHealth(next);
  player.pushVampiricMaxHpCut(cut);
}

/** Restore max HP when unequipping a Vampiric Fang. */
export function applyVampiricFangUnequip(player: Player): void {
  const cut = player.popVampiricMaxHpCut();
  if (cut <= 0) return;
  player.setMaxHealth(player.getMaxHealth() + cut);
}

/** Sync fang HP cuts after inventory add that may have auto-equipped. */
export function syncVampiricFangAfterInventoryChange(
  player: Player,
  equippedBefore: number,
): void {
  const equippedAfter = countEquipped(player, 'vampiricFang');
  for (let i = equippedBefore; i < equippedAfter; i++) {
    applyVampiricFangEquip(player);
  }
  for (let i = equippedAfter; i < equippedBefore; i++) {
    applyVampiricFangUnequip(player);
  }
}

/** Extend Burn/Shock/Iced on Magic hits when already present. */
export function applyRitualCodexOnHit(
  player: Player,
  target: Creature,
  damageType: DamageType,
  procs: string[],
): string[] {
  if (damageType !== DamageType.Magic) return [];
  const n = countEquipped(player, 'ritualCodex');
  if (n <= 0) return [];

  const msgs: string[] = [];
  for (let i = 0; i < n; i++) {
    for (const statusName of RITUAL_STATUS_NAMES) {
      const effect = target.getEffects().find((e) => e.getName() === statusName);
      if (!effect) continue;
      if (Math.random() >= RITUAL_EXTEND_CHANCE) continue;
      effect.turns += 1;
      procs.push('EXTEND');
      msgs.push(`${statusName} extended (+1 turn)`);
    }
  }
  return msgs;
}

/** Once per fight: empty limited weapon restores a use on another spent weapon. */
export function tryEmptyQuiverCord(
  fight: Fight,
  player: Player,
  emptiedWeapon: Weapon,
): string | null {
  if (fight.emptyQuiverUsed || countEquipped(player, 'emptyQuiverCord') <= 0) return null;
  const move = emptiedWeapon.getMove();
  if (move.getMaxUses() <= 0 || move.getUses() > 0) return null;

  const candidates = player
    .getInventory()
    .getWeapons()
    .filter((w) => {
      if (w === emptiedWeapon) return false;
      const m = w.getMove();
      return m.getMaxUses() > 0 && m.getUses() < m.getMaxUses();
    });
  if (candidates.length === 0) return null;

  const w = candidates[Math.floor(Math.random() * candidates.length)];
  w.getMove().addUses(1);
  fight.emptyQuiverUsed = true;
  return `Empty Quiver Cord restored a use on ${w.getName()}.`;
}

/** Once per fight: drop below 25% HP → Resistance. */
export function trySecondWind(
  fight: Fight,
  player: Player,
  procs: string[],
): string | null {
  if (fight.secondWindUsed || countEquipped(player, 'secondWindBandana') <= 0) return null;
  if (player.getHealth() >= player.getMaxHealth() * SECOND_WIND_HP_FRACTION) return null;

  fight.secondWindUsed = true;
  const msg = player.addEffect(
    new ResistanceEffect(SECOND_WIND_RESIST_TURNS, SECOND_WIND_RESIST_MULT),
  );
  procs.push('2ND WIND');
  return msg || 'Second Wind Bandana granted Damage Resistance!';
}

/** On-hit accessory procs (venom + elemental rings + ritual). */
export function applyAccessoryOnHit(
  player: Player,
  target: Creature,
  damageType: DamageType,
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

  msgs.push(...applyRitualCodexOnHit(player, target, damageType, procs));
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
