export type CombatFx = {
  id: number;
  missed: boolean;
  damageDealt: number;
  healed: boolean;
  /** Who got hit / floater appears over */
  targetSide: 'player' | 'enemy';
  /** Who attacked — for weapon flash */
  sourceSide: 'player' | 'enemy';
  weaponIndex: number;
  shake: boolean;
};

let fxId = 0;

export function nextFxId(): number {
  fxId += 1;
  return fxId;
}
