export type IconRef =
  | { type: 'image'; path: string; flip?: boolean }
  | { type: 'sprite'; sheet: string; row: number; col: number; tile?: number; scale?: number }
  | null;

export const ITEMS_SHEET = '/assets/sprites/items/items.png';
export const POTIONS_SHEET = '/assets/sprites/items/potions.png';

export function creatureIcon(name: string, flip = true): IconRef {
  return { type: 'image', path: `/assets/images/creatures/${name}.png`, flip };
}

export function weaponSprite(row: number, col: number): IconRef {
  return { type: 'sprite', sheet: ITEMS_SHEET, row, col, tile: 32, scale: 64 };
}

export function weaponNamed(name: string): IconRef {
  return { type: 'image', path: `/assets/images/Weapons/${name}.png` };
}

export function potionSprite(row: number, col: number): IconRef {
  return { type: 'sprite', sheet: POTIONS_SHEET, row, col, tile: 32, scale: 64 };
}
