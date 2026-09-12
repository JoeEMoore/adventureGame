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
  applyThornCollarReflect,
  applyVampiricHeal,
  armEchoCharmOnMiss,
  armQuickstepOnEnemyMiss,
  consumeEchoAccuracyBonus,
  consumeQuickstepDamageBonus,
  doubleShotAccuracyPenalty,
  doubleShotDamageMultiplier,
  doubleShotExtraUses,
  focusCrystalDamageMultiplier,
  glassDiceAccuracyPenalty,
  glassDiceDamageMultiplier,
  gripsAccuracyBonus,
  ironBandBluntTakenMultiplier,
  isBluntDamage,
  knightBleedStreakThresholds,
  markFocusCrystalUsed,
  rollAccessoryBlock,
  tryEmptyQuiverCord,
  trySecondWind,
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
  /** Echo Charm: next player attack gets accuracy bonus. */
  echoAccuracyPending = false;
  /** Focus Crystal: first landed hit bonus already spent. */
  focusCrystalUsed = false;
  /** Quickstep Boots: next player attack gets damage bonus. */
  quickstepDamagePending = false;
  /** Empty Quiver Cord: already fired this fight. */
  emptyQuiverUsed = false;
  /** Second Wind Bandana: already fired this fight. */
  secondWindUsed = false;

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
      if (sourceIsPlayer) {
        const extra = doubleShotExtraUses(this.player, move.getDamageType());
        for (let i = 0; i < extra; i++) {
          if (move.getUses() > 0) move.decrementUses();
        }
      }
    }

    let emptyQuiverMsg: string | null = null;
    if (sourceIsPlayer) {
      emptyQuiverMsg = tryEmptyQuiverCord(this, this.player, weapon);
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
        accuracy = Math.min(
          1,
          accuracy +
            gripsAccuracyBonus(this.player, move.getDamageType()) +
            this.player.getDescendAccuracyBonus() +
            consumeEchoAccuracyBonus(this, this.player),
        );
        accuracy = Math.max(
          0,
          accuracy -
            doubleShotAccuracyPenalty(this.player, move.getDamageType()) -
            glassDiceAccuracyPenalty(this.player),
        );
      }

      const hitChance =
        (1 - target.getTurnModifiers().getEvasion()) * accuracy -
        source.getTurnModifiers().getMissChanceBonus();

      if (Math.random() > hitChance) {
        if (sourceIsPlayer) {
          this.player.resetConsecutiveSlashHits();
          armEchoCharmOnMiss(this, this.player);
        } else if (targetIsPlayer) {
          armQuickstepOnEnemyMiss(this, this.player);
        }
        let missMsg = `${source.getName()} used ${move.getName()} on ${target.getName()}. They Missed!`;
        if (emptyQuiverMsg) missMsg += ` ${emptyQuiverMsg}`;
        return {
          message: missMsg,
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
      let blockMsg = `${source.getName()} used ${move.getName()} on ${target.getName()}. Blocked by Shield!`;
      if (emptyQuiverMsg) blockMsg += ` ${emptyQuiverMsg}`;
      return {
        message: blockMsg,
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
    if (sourceIsPlayer) {
      damage *= doubleShotDamageMultiplier(this.player, move.getDamageType());
      damage *= this.player.getDescendTypeDamageMultiplier(move.getDamageType());
      damage *= glassDiceDamageMultiplier(this.player);
      if (source !== target) {
        damage *= consumeQuickstepDamageBonus(this, this.player);
        damage *= focusCrystalDamageMultiplier(this, this.player);
      }
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

    const effectMsgs: string[] = [];

    if (sourceIsPlayer && source !== target && damageDealt > 0) {
      markFocusCrystalUsed(this, this.player);
      const vamp = applyVampiricHeal(this.player, damageDealt, procs);
      if (vamp) effectMsgs.push(vamp);
    }

    if (source !== target && targetIsPlayer && damageDealt > 0) {
      const thorn = applyThornCollarReflect(this.player, source, damageDealt, procs);
      if (thorn) effectMsgs.push(thorn);
      const secondWind = trySecondWind(this, this.player, procs);
      if (secondWind) effectMsgs.push(secondWind);
    }

    let result = `${source.getName()} used ${move.getName()} on ${target.getName()}.`;
    if (damageDealt > 0) {
      result += ` Dealt ${roundDouble(damageDealt)} damage.`;
      if (resistMult < 0.95) result += ` (${move.getDamageType()} resisted)`;
      else if (resistMult > 1.05) result += ` (${move.getDamageType()} weak)`;
    }

    for (const e of move.createEffects()) {
      effectMsgs.push(target.addEffect(e));
    }

    if (sourceIsPlayer && source !== target && damageDealt > 0) {
      const passiveMsgs = this.applyClassPassives(move.getDamageType(), target, procs);
      effectMsgs.push(...passiveMsgs);
      effectMsgs.push(
        ...applyAccessoryOnHit(this.player, target, move.getDamageType(), procs),
      );
    } else if (sourceIsPlayer && move.getDamageType() !== DamageType.Slice) {
      this.player.resetConsecutiveSlashHits();
    }

    if (emptyQuiverMsg) effectMsgs.push(emptyQuiverMsg);

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
        const { chanceAt, guaranteeAt } = knightBleedStreakThresholds(this.player);

        if (streak === chanceAt) {
          if (Math.random() < KNIGHT_BLEED_CHANCE) {
            msgs.push(target.addEffect(new BleedEffect(KNIGHT_BLEED_TURNS)));
            procs.push('BLEED');
            this.player.setSlashBleedProcced(true);
          }
        } else if (streak >= guaranteeAt) {
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
