export type FightBoxId =
  | 'playerStage'
  | 'playerPlate'
  | 'playerExtras'
  | 'enemyStage'
  | 'enemyPlate'
  | 'enemyExtras'
  | 'fightMessage'
  | 'fightCommands';

/** Percent of the fight canvas (0–100). */
export type BoxPos = { x: number; y: number };

export type FightLayout = Record<FightBoxId, BoxPos>;

export const FIGHT_LAYOUT_STORAGE_KEY = 'adventureGame.fightUiLayout.v1';

/** Default / shipped fight UI layout. Edit mode can override via localStorage. */
export const FIGHT_LAYOUT: FightLayout = {
  playerStage: { x: 12.12, y: 36.37 },
  playerPlate: { x: 9.8, y: 64.05 },
  playerExtras: { x: 7.12, y: 11.27 },
  enemyStage: { x: 66.98, y: 35.13 },
  enemyPlate: { x: 64.5, y: 64.45 },
  enemyExtras: { x: 61.88, y: 17.93 },
  fightMessage: { x: 52.82, y: 82.21 },
  fightCommands: { x: 4.07, y: 81.86 },
};

export const FIGHT_BOX_LABELS: Record<FightBoxId, string> = {
  playerStage: 'Player sprite',
  playerPlate: 'Player HP',
  playerExtras: 'Player status',
  enemyStage: 'Enemy sprite',
  enemyPlate: 'Enemy HP',
  enemyExtras: 'Enemy status',
  fightMessage: 'Message box',
  fightCommands: 'Commands',
};

export function clampPos(pos: BoxPos): BoxPos {
  return {
    x: Math.max(0, Math.min(92, pos.x)),
    y: Math.max(0, Math.min(92, pos.y)),
  };
}

export function loadFightLayout(): FightLayout {
  try {
    const raw = localStorage.getItem(FIGHT_LAYOUT_STORAGE_KEY);
    if (!raw) return { ...FIGHT_LAYOUT };
    const parsed = JSON.parse(raw) as Partial<FightLayout>;
    return { ...FIGHT_LAYOUT, ...parsed };
  } catch {
    return { ...FIGHT_LAYOUT };
  }
}

export function saveFightLayout(layout: FightLayout): void {
  localStorage.setItem(FIGHT_LAYOUT_STORAGE_KEY, JSON.stringify(layout));
  (window as unknown as { __FIGHT_LAYOUT__?: FightLayout }).__FIGHT_LAYOUT__ = layout;
}

export function resetFightLayout(): FightLayout {
  localStorage.removeItem(FIGHT_LAYOUT_STORAGE_KEY);
  const layout = { ...FIGHT_LAYOUT };
  (window as unknown as { __FIGHT_LAYOUT__?: FightLayout }).__FIGHT_LAYOUT__ = layout;
  return layout;
}
