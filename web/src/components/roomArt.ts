import type { Room } from '../game/levels/rooms/Room';
import type { Coordinate } from '../game/levels/Coordinate';
import { asset } from '../utils/asset';

function hasNeighbor(rooms: (Room | null)[][], r: number, c: number, dr: number, dc: number): boolean {
  const nr = r + dr;
  const nc = c + dc;
  if (nr < 0 || nc < 0 || nr >= rooms.length || nc >= rooms[0].length) return false;
  return rooms[nr][nc] !== null;
}

export function getDoorFlags(rooms: (Room | null)[][], pos: Coordinate) {
  const row = pos.getRow();
  const col = pos.getCol();
  return {
    up: hasNeighbor(rooms, row, col, -1, 0),
    down: hasNeighbor(rooms, row, col, 1, 0),
    left: hasNeighbor(rooms, row, col, 0, -1),
    right: hasNeighbor(rooms, row, col, 0, 1),
  };
}

/** Deterministic 0–1 from room coords (+ salt). */
function roomRand(row: number, col: number, salt: number): number {
  const n = Math.sin(row * 127.1 + col * 311.7 + salt * 74.3) * 43758.5453;
  return n - Math.floor(n);
}

export interface RoomBirdPlacement {
  /** Horizontal percent along the top wall (feet sit on outer top edge). */
  x: number;
  flipX: boolean;
}

/**
 * Sometimes perch audreyBird2 on the outside top of the back wall,
 * never centered on an upward door opening.
 */
export function getRoomBird(rooms: (Room | null)[][], pos: Coordinate): RoomBirdPlacement | null {
  const row = pos.getRow();
  const col = pos.getCol();
  if (roomRand(row, col, 1) > 0.32) return null;

  const { up } = getDoorFlags(rooms, pos);
  // Wall segments on the outer top edge — skip the door notch when present
  const slots = up ? [16, 22, 78, 84] : [16, 28, 50, 72, 84];
  const slot = slots[Math.floor(roomRand(row, col, 2) * slots.length)];
  return {
    x: slot,
    flipX: roomRand(row, col, 3) < 0.5,
  };
}

/** Pick a room tile image for the door layout. No rotation — uses mirrored assets. */
export function getRoomImage(rooms: (Room | null)[][], pos: Coordinate): string | null {
  const { up, down, left, right } = getDoorFlags(rooms, pos);
  const doors = (up ? 1 : 0) + (down ? 1 : 0) + (left ? 1 : 0) + (right ? 1 : 0);
  const base = asset('assets/images/rooms');

  if (doors === 1) {
    if (up) return `${base}/oneDoor_Up.png`;
    if (down) return `${base}/oneDoor_Down.png`;
    if (right) return `${base}/oneDoor_Right.png`;
    if (left) return `${base}/oneDoor_Left.png`;
  }

  if (doors === 2) {
    if (up && down) return `${base}/twoDoor_Up_Down.png`;
    if (left && right) return `${base}/twoDoor_Left_Right.png`;
    if (up && right) return `${base}/twoDoor_Right_Up.png`;
    if (up && left) return `${base}/twoDoor_Left_Up.png`;
    if (down && right) return `${base}/twoDoor_Right_Down.png`;
    if (down && left) return `${base}/twoDoor_Left_Down.png`;
  }

  if (doors === 3) {
    if (!down) return `${base}/threeDoor_Missing_Down.png`;
    if (!left) return `${base}/threeDoor_Missing_Left.png`;
    if (!up) return `${base}/threeDoor_Missing_Up.png`;
    if (!right) return `${base}/threeDoor_Missing_Right.png`;
  }

  if (doors === 4) return `${base}/fourDoor.png`;

  return null;
}

/** @deprecated use getRoomImage — kept for any leftover callers */
export function getRoomImageAndRotation(
  rooms: (Room | null)[][],
  pos: Coordinate,
): { src: string; rotation: number } | null {
  const src = getRoomImage(rooms, pos);
  return src ? { src, rotation: 0 } : null;
}
