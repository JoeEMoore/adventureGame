export const DamageType = {
  Blunt: 'Blunt',
  Slice: 'Slice',
  Projectile: 'Projectile',
  Magic: 'Magic',
  Pure: 'Pure',
} as const;

export type DamageType = (typeof DamageType)[keyof typeof DamageType];

export function getDamageTypes(): DamageType[] {
  return [DamageType.Blunt, DamageType.Slice, DamageType.Projectile, DamageType.Magic];
}
