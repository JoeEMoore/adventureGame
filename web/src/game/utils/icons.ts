import { asset } from '../../utils/asset';

export type IconRef =
  | { type: 'image'; path: string; flip?: boolean }
  | { type: 'sprite'; sheet: string; row: number; col: number; tile?: number; scale?: number }
  | null;

export const ITEMS_SHEET = asset('assets/sprites/items/items.png');
export const POTIONS_SHEET = asset('assets/sprites/items/potions.png');
export const POT_EFFECTS_SHEET = asset('assets/sprites/items/PotEffects.png');

export function creatureIcon(name: string, flip = true): IconRef {
  return { type: 'image', path: asset(`assets/images/creatures/${name}.png`), flip };
}

export function weaponSprite(row: number, col: number): IconRef {
  return { type: 'sprite', sheet: ITEMS_SHEET, row, col, tile: 32, scale: 64 };
}

export function weaponNamed(name: string): IconRef {
  return { type: 'image', path: asset(`assets/images/Weapons/${name}.png`) };
}

export function potionSprite(row: number, col: number): IconRef {
  return { type: 'sprite', sheet: POTIONS_SHEET, row, col, tile: 32, scale: 64 };
}

/** 1-based index along the PotEffects strip (16px-wide cells). */
export function potEffectSprite(oneBasedIndex: number): IconRef {
  return {
    type: 'sprite',
    sheet: POT_EFFECTS_SHEET,
    row: 0,
    col: oneBasedIndex - 1,
    tile: 16,
    scale: 32,
  };
}
