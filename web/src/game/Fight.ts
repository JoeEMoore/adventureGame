import type { Creature } from './creatures/Creature';
import type { Player } from './creatures/Player';
import { CreatureAI } from './creatures/CreatureAI';
import type { Weapon } from './items/weapons/Weapon';
import { DamageType } from './damagetypes/DamageType';
import {
  BleedEffect,
  BurnEffect,
  IcedEffect,
  KnockoutEffect,
  ShockEffect,
} from './effects/effects';
import {
  applyAccessoryOnHit,
  gripsAccuracyBonus,
  ironBandBluntTakenMultiplier,
  isBluntDamage,
  rollAccessoryBlock,
  wrapsBluntMultiplier,
} from './items/accessories/accessoryCombat';
import { roundDouble } from './utils/DoubleUtils';

export interface MoveResult {
  message: string;
  missed: boolean;
  damageDealt: number;
  healed: boolean;
  sourceIsPlayer: boolean;
  targetIsPlayer: boolean;
  weaponIndex: number;
  /** Short labels for combat floaters, e.g. BLEED, BURN */
  procs: string[];
}

const RANGER_PROJECTILE_ACCURACY_BONUS = 0.15;
const KNIGHT_BLEED_CHANCE = 0.5;
const KNIGHT_BLEED_TURNS = 3;
const BARBARIAN_KNOCKOUT_CHANCE = 0.2;
const BARBARIAN_KNOCKOUT_TURNS = 1;
const MAGE_ELEMENT_CHANCE = 0.1;
const MAGE_BURN_TURNS = 3;
const MAGE_SHOCK_TURNS = 2;
const MAGE_ICED_TURNS = 3;

export class Fight {
  player: Player;
  enemy: Creature;

  constructor(player: Player, enemy: Creature) {
    this.player = player;
    this.enemy = enemy;
  }

  performMove(
    source: Creature,
    target: Creature,
    weapon: Weapon,
    weaponIndex = 0,
  ): MoveResult {
    const move = weapon.getMove();
    const sourceIsPlayer = source === this.player;
    const targetIsPlayer = target === this.player;
    const procs: string[] = [];

    if (move.getUses() > 0) {
      move.decrementUses();
    }

    if (source !== target) {
      let accuracy = move.getAccuracy();
      if (
        sourceIsPlayer &&
        this.player.getPlayerClass() === 'ranger' &&
        move.getDamageType() === DamageType.Projectile
      ) {
        accuracy = Math.min(1, accuracy + RANGER_PROJECTILE_ACCURACY_BONUS);
      }
      if (sourceIsPlayer) {
        accuracy = Math.min(1, accuracy + gripsAccuracyBonus(this.player, move.getDamageType()));
      }

      const hitChance =
        (1 - target.getTurnModifiers().getEvasion()) * accuracy -
        source.getTurnModifiers().getMissChanceBonus();

      if (Math.random() > hitChance) {
        if (sourceIsPlayer) this.player.resetConsecutiveSlashHits();
        return {
          message: `${source.getName()} used ${move.getName()} on ${target.getName()}. They Missed!`,
          missed: true,
          damageDealt: 0,
          healed: false,
          sourceIsPlayer,
          targetIsPlayer,
          weaponIndex,
          procs,
        };
      }
    }

    // Shield: block incoming damage to the player
    if (source !== target && targetIsPlayer && rollAccessoryBlock(this.player)) {
      procs.push('BLOCK');
      return {
        message: `${source.getName()} used ${move.getName()} on ${target.getName()}. Blocked by Shield!`,
        missed: false,
        damageDealt: 0,
        healed: false,
        sourceIsPlayer,
        targetIsPlayer,
        weaponIndex,
        procs,
      };
    }

    let damage = move.getDamage() * source.getTurnModifiers().getDamage();
    if (sourceIsPlayer && isBluntDamage(move.getDamageType())) {
      damage *= wrapsBluntMultiplier(this.player);
    }

    const resistMult = target.getTurnModifiers().getResistance(move.getDamageType());
    if (source !== target && damage > 0) {
      if (resistMult < 0.95) procs.push('RESIST');
      else if (resistMult > 1.05) procs.push('WEAK');
    }

    if (
      source !== target &&
      targetIsPlayer &&
      isBluntDamage(move.getDamageType())
    ) {
      damage *= ironBandBluntTakenMultiplier(this.player);
    }

    const damageDealt = target.applyDamage(damage, move.getDamageType());
    const healed = move.targetsAllies() && source === target;

    let result = `${source.getName()} used ${move.getName()} on ${target.getName()}.`;
    if (damageDealt > 0) {
      result += ` Dealt ${roundDouble(damageDealt)} damage.`;
      if (resistMult < 0.95) result += ` (${move.getDamageType()} resisted)`;
      else if (resistMult > 1.05) result += ` (${move.getDamageType()} weak)`;
    }

    const effectMsgs: string[] = [];
    for (const e of move.createEffects()) {
      effectMsgs.push(target.addEffect(e));
    }

    if (sourceIsPlayer && source !== target && damageDealt > 0) {
      const passiveMsgs = this.applyClassPassives(move.getDamageType(), target, procs);
      effectMsgs.push(...passiveMsgs);
      effectMsgs.push(...applyAccessoryOnHit(this.player, target, procs));
    } else if (sourceIsPlayer && move.getDamageType() !== DamageType.Slice) {
      this.player.resetConsecutiveSlashHits();
    }

    if (effectMsgs.length) {
      result += ' ' + effectMsgs.filter(Boolean).join(', ');
    }

    return {
      message: result,
      missed: false,
      damageDealt,
      healed,
      sourceIsPlayer,
      targetIsPlayer,
      weaponIndex,
      procs,
    };
  }

  /** Knight / Barbarian / Mage on-hit passives. Ranger is passive stats only. */
  private applyClassPassives(
    damageType: DamageType,
    target: Creature,
    procs: string[],
  ): string[] {
    const cls = this.player.getPlayerClass();
    const msgs: string[] = [];

    if (cls === 'knight') {
      if (damageType === DamageType.Slice) {
        this.player.setConsecutiveSlashHits(this.player.getConsecutiveSlashHits() + 1);
        const streak = this.player.getConsecutiveSlashHits();

        if (streak === 2) {
          if (Math.random() < KNIGHT_BLEED_CHANCE) {
            msgs.push(target.addEffect(new BleedEffect(KNIGHT_BLEED_TURNS)));
            procs.push('BLEED');
            this.player.setSlashBleedProcced(true);
          }
        } else if (streak >= 3) {
          if (!this.player.hasSlashBleedProcced()) {
            msgs.push(target.addEffect(new BleedEffect(KNIGHT_BLEED_TURNS)));
            procs.push('BLEED');
          }
          this.player.resetConsecutiveSlashHits();
        }
      } else {
        this.player.resetConsecutiveSlashHits();
      }
    } else {
      this.player.resetConsecutiveSlashHits();
    }

    if (cls === 'barbarian' && damageType === DamageType.Blunt) {
      if (Math.random() < BARBARIAN_KNOCKOUT_CHANCE) {
        msgs.push(target.addEffect(new KnockoutEffect(BARBARIAN_KNOCKOUT_TURNS)));
        procs.push('KO');
      }
    }

    if (cls === 'mage' && damageType === DamageType.Magic) {
      if (Math.random() < MAGE_ELEMENT_CHANCE) {
        msgs.push(target.addEffect(new BurnEffect(MAGE_BURN_TURNS, 10)));
        procs.push('BURN');
      }
      if (Math.random() < MAGE_ELEMENT_CHANCE) {
        msgs.push(target.addEffect(new ShockEffect(MAGE_SHOCK_TURNS, 0.5)));
        procs.push('SHOCK');
      }
      if (Math.random() < MAGE_ELEMENT_CHANCE) {
        msgs.push(target.addEffect(new IcedEffect(MAGE_ICED_TURNS, 0.25)));
        procs.push('ICED');
      }
    }

    return msgs;
  }

  creatureTurn(creature: Creature, enemy: Creature): MoveResult {
    const ai = new CreatureAI(creature);
    const index = ai.calculateMove();
    const weapon = creature.getInventory().getWeapon(index);
    if (!weapon) {
      return {
        message: `${creature.getName()} has no weapons!`,
        missed: false,
        damageDealt: 0,
        healed: false,
        sourceIsPlayer: creature === this.player,
        targetIsPlayer: true,
        weaponIndex: 0,
        procs: [],
      };
    }
    const target = weapon.getMove().targetsAllies() ? creature : enemy;
    return this.performMove(creature, target, weapon, index);
  }
}
