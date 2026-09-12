/** Map room roles, locks, and framing for the exploration sub-game. */

export type RoomRole =
  | 'start'
  | 'combat'
  | 'elite'
  | 'rest'
  | 'trap'
  | 'hazard'
  | 'treasure'
  | 'shop'
  | 'forge'
  | 'boss';

export type EliteTag = 'armored' | 'venomous' | 'swift' | 'volatile' | 'draining';

export type RoomLock =
  | { kind: 'key' }
  | { kind: 'gold'; amount: number }
  | { kind: 'hp'; amount: number };

/** Player-facing label — may intentionally mismatch trap/hazard framing. */
export function frameLabelFor(role: RoomRole, revealed: boolean): string {
  switch (role) {
    case 'start':
      return 'Entrance';
    case 'combat':
      return 'Chamber';
    case 'elite':
      return 'Elite den';
    case 'rest':
      return revealed ? 'Rest site' : 'Sanctuary';
    case 'trap':
      return revealed ? 'Ambush' : 'Sanctuary';
    case 'hazard':
      return revealed ? 'Quiet cache' : 'Danger';
    case 'treasure':
      return 'Sealed vault';
    case 'shop':
      return 'Shop';
    case 'forge':
      return 'Forge';
    case 'boss':
      return 'Boss lair';
  }
}
