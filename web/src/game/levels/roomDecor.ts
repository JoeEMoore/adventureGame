import type { RoomDecoration, RoomDecorationKind, RoomVine } from './rooms/Room';

const KINDS: RoomDecorationKind[] = ['skull', 'bones'];

/** ~45% of rooms get 1–3 scattered skull/bone props. */
export function generateRoomDecorations(): RoomDecoration[] {
  if (Math.random() > 0.45) return [];

  const count = 1 + Math.floor(Math.random() * 3);
  const decorations: RoomDecoration[] = [];

  for (let i = 0; i < count; i++) {
    decorations.push({
      kind: KINDS[Math.floor(Math.random() * KINDS.length)],
      x: 18 + Math.random() * 64,
      y: 22 + Math.random() * 56,
      rotation: Math.floor(Math.random() * 360),
      scale: 0.7 + Math.random() * 0.55,
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
