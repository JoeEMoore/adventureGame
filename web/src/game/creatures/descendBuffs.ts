import { DamageType, getDamageTypes } from '../damagetypes/DamageType';
import type { Player } from './Player';

export type DescendBuffId =
  | 'maxHealth'
  | 'damageBlunt'
  | 'damageSlice'
  | 'damageProjectile'
  | 'damageMagic'
  | 'accuracy'
  | 'dodge';

export interface DescendBuffOption {
  id: DescendBuffId;
  label: string;
  description: string;
}

const HEALTH_MULT = 1.1;
const TYPE_DAMAGE_BONUS = 0.1;
const ACCURACY_BONUS = 0.05;
const DODGE_BONUS = 0.05;

export const DESCEND_BUFF_OPTIONS: DescendBuffOption[] = [
  {
    id: 'maxHealth',
    label: '+10% Max Health',
    description: 'Permanently raise your maximum HP by 10%.',
  },
  {
    id: 'damageBlunt',
    label: '+10% Blunt Damage',
    description: 'All Blunt weapons deal 10% more damage.',
  },
  {
    id: 'damageSlice',
    label: '+10% Slice Damage',
    description: 'All Slice weapons deal 10% more damage.',
  },
  {
    id: 'damageProjectile',
    label: '+10% Projectile Damage',
    description: 'All Projectile weapons deal 10% more damage.',
  },
  {
    id: 'damageMagic',
    label: '+10% Magic Damage',
    description: 'All Magic weapons deal 10% more damage.',
  },
  {
    id: 'accuracy',
    label: '+5% Accuracy',
    description: 'Permanently improve hit chance with every weapon.',
  },
  {
    id: 'dodge',
    label: '+5% Dodge',
    description: 'Permanently improve your chance to evade attacks.',
  },
];

const TYPE_BY_BUFF: Partial<Record<DescendBuffId, DamageType>> = {
  damageBlunt: DamageType.Blunt,
  damageSlice: DamageType.Slice,
  damageProjectile: DamageType.Projectile,
  damageMagic: DamageType.Magic,
};

export function getDescendBuffOption(id: DescendBuffId): DescendBuffOption {
  const opt = DESCEND_BUFF_OPTIONS.find((o) => o.id === id);
  if (!opt) throw new Error(`Unknown descend buff: ${id}`);
  return opt;
}

/** Apply a permanent run buff chosen when descending. */
export function applyDescendBuff(player: Player, id: DescendBuffId): void {
  switch (id) {
    case 'maxHealth': {
      const prev = player.getMaxHealth();
      const next = Math.max(prev + 1, Math.floor(prev * HEALTH_MULT));
      player.setMaxHealth(next);
      player.addHealth(next - prev);
      break;
    }
    case 'dodge': {
      player.getBaseModifiers().addEvasion(DODGE_BONUS);
      player.syncTurnModifiersFromBase();
      break;
    }
    case 'accuracy': {
      player.addDescendAccuracyBonus(ACCURACY_BONUS);
      break;
    }
    case 'damageBlunt':
    case 'damageSlice':
    case 'damageProjectile':
    case 'damageMagic': {
      const dt = TYPE_BY_BUFF[id]!;
      player.addDescendTypeDamageBonus(dt, TYPE_DAMAGE_BONUS);
      break;
    }
  }
  player.recordDescendBuff(id);
}

export function emptyTypeDamageBonuses(): Map<DamageType, number> {
  const map = new Map<DamageType, number>();
  for (const dt of getDamageTypes()) {
    map.set(dt, 0);
  }
  return map;
}
