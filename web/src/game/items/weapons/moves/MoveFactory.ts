import { DamageType } from '../../../damagetypes/DamageType';
import { HealEffect, PoisonEffect } from '../../../effects/effects';
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
};
