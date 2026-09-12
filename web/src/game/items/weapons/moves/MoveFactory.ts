import { DamageType } from '../../../damagetypes/DamageType';
import {
  BurnEffect,
  HealEffect,
  IcedEffect,
  PoisonEffect,
  ShockEffect,
} from '../../../effects/effects';
import { Move } from './Move';

export const MoveFactory = {
  createSlashMove(): Move {
    return new Move('Slash', 15, DamageType.Slice, -1, 0.8, false);
  },
  createSmashMove(): Move {
    return new Move('Smash', 30, DamageType.Blunt, -1, 0.6, false);
  },
  createShootMove(): Move {
    return new Move('Shoot', 20, DamageType.Projectile, 10, 0.8, false);
  },
  /** Scarce ammo — teaches resource prediction. */
  createJavelinMove(): Move {
    return new Move('Javelin', 22, DamageType.Projectile, 4, 0.85, false);
  },
  createMagicMissileMove(): Move {
    return new Move('Magic Missile', 10, DamageType.Magic, -1, 0.9, false);
  },
  createToxicBoltMove(): Move {
    const move = new Move('Toxic Bolt', 8, DamageType.Magic, 5, 0.85, false);
    move.setEffects(() => [new PoisonEffect(2)]);
    return move;
  },
  createHealMove(): Move {
    const move = new Move('Heal', 0, DamageType.Pure, 3, 1.0, true);
    move.setEffects(() => [new HealEffect(20)]);
    return move;
  },
  createScratchMove(): Move {
    return new Move('Scratch', 5, DamageType.Slice, -1, 0.8, false);
  },
  createSporeShotMove(): Move {
    return new Move('Spore Shot', 10, DamageType.Projectile, -1, 0.95, false);
  },
  createPierceMove(): Move {
    return new Move('Pierce', 14, DamageType.Projectile, 8, 0.92, false);
  },
  createCleaveMove(): Move {
    return new Move('Cleave', 18, DamageType.Slice, -1, 0.75, false);
  },
  createStunBashMove(): Move {
    return new Move('Stun Bash', 18, DamageType.Blunt, -1, 0.72, false);
  },
  createDrainBoltMove(): Move {
    return new Move('Drain Bolt', 12, DamageType.Magic, 6, 0.88, false);
  },
  createFrostShardMove(): Move {
    const move = new Move('Frost Shard', 11, DamageType.Magic, 7, 0.9, false);
    move.setEffects(() => [new IcedEffect(2, 0.2)]);
    return move;
  },
  createSparkMove(): Move {
    const move = new Move('Spark', 9, DamageType.Magic, -1, 0.85, false);
    move.setEffects(() => [new ShockEffect(2, 0.35)]);
    return move;
  },
  createEmberMove(): Move {
    const move = new Move('Ember', 10, DamageType.Magic, 6, 0.85, false);
    move.setEffects(() => [new BurnEffect(2, 8)]);
    return move;
  },
  createVenomBiteMove(): Move {
    const move = new Move('Venom Bite', 7, DamageType.Slice, -1, 0.85, false);
    move.setEffects(() => [new PoisonEffect(2)]);
    return move;
  },
  createWebShotMove(): Move {
    const move = new Move('Web Shot', 6, DamageType.Projectile, 5, 0.9, false);
    move.setEffects(() => [new IcedEffect(2, 0.25)]);
    return move;
  },
};
