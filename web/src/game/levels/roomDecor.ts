import type { RoomDecoration, RoomDecorationKind, RoomVine } from './rooms/Room';

const KINDS: RoomDecorationKind[] = ['skull', 'bones', 'rock', 'rock2', 'barrel'];

/** ~45% of rooms get 1–3 scattered floor props (skull, bones, rocks, barrel). */
export function generateRoomDecorations(): RoomDecoration[] {
  if (Math.random() > 0.45) return [];

  const count = 1 + Math.floor(Math.random() * 3);
  const decorations: RoomDecoration[] = [];

  for (let i = 0; i < count; i++) {
    const kind = KINDS[Math.floor(Math.random() * KINDS.length)];
    const upright = kind === 'barrel' || kind === 'rock' || kind === 'rock2';
    decorations.push({
      kind,
      x: 18 + Math.random() * 64,
      y: 22 + Math.random() * 56,
      // Rocks & barrels stay right-side up; skulls/bones can tumble
      rotation: upright ? 0 : Math.floor(Math.random() * 360),
      // Barrel & rock2 ~30% smaller than other debris
      scale:
        kind === 'barrel' || kind === 'rock2'
          ? 0.55 + Math.random() * 0.2
          : 0.7 + Math.random() * 0.55,
      flipX: upright ? Math.random() < 0.5 : false,
    });
  }

  return decorations;
}

/**
 * Back-wall vine overlays authored on a full room canvas.
 * No rotation — optional flip across the vertical axis (scaleX) for variety.
 */
export function generateRoomVines(): RoomVine[] {
  const vines: RoomVine[] = [];

  // ~50% small vine, ~40% big vine (can stack)
  if (Math.random() < 0.5) {
    vines.push({ kind: 'smallVine', flipX: Math.random() < 0.5 });
  }
  if (Math.random() < 0.4) {
    vines.push({ kind: 'bigVine', flipX: Math.random() < 0.5 });
  }

  return vines;
}
