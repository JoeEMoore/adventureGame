import type { Creature } from './creatures/Creature';
import type { Player } from './creatures/Player';
import { CreatureAI } from './creatures/CreatureAI';
import type { Weapon } from './items/weapons/Weapon';
import { roundDouble } from './utils/DoubleUtils';

export interface MoveResult {
  message: string;
  missed: boolean;
  damageDealt: number;
  healed: boolean;
  sourceIsPlayer: boolean;
  targetIsPlayer: boolean;
  weaponIndex: number;
}

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

    if (move.getUses() > 0) {
      move.decrementUses();
    }

    if (source !== target) {
      const hitChance = (1 - target.getTurnModifiers().getEvasion()) * move.getAccuracy();
      if (Math.random() > hitChance) {
        return {
          message: `${source.getName()} used ${move.getName()} on ${target.getName()}. They Missed!`,
          missed: true,
          damageDealt: 0,
          healed: false,
          sourceIsPlayer,
          targetIsPlayer,
          weaponIndex,
        };
      }
    }

    const damage = move.getDamage() * source.getTurnModifiers().getDamage();
    const damageDealt = target.applyDamage(damage, move.getDamageType());
    const healed = move.targetsAllies() && source === target;

    let result = `${source.getName()} used ${move.getName()} on ${target.getName()}.`;
    if (damageDealt > 0) {
      result += ` Dealt ${roundDouble(damageDealt)} damage.`;
    }

    const effectMsgs: string[] = [];
    for (const e of move.createEffects()) {
      effectMsgs.push(target.addEffect(e));
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
    };
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
      };
    }
    const target = weapon.getMove().targetsAllies() ? creature : enemy;
    return this.performMove(creature, target, weapon, index);
  }
}
