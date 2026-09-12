/** Floor pacing & spiral config — situation changes by depth, not raw HP. */

export const TOTAL_FLOORS = 3;

export interface FloorConfig {
  /** 0-based floor index */
  floorIndex: number;
  /** Normal rooms grown before shop + boss (includes start) */
  numRooms: number;
  roomLength: number;
  displayName: string;
  /** Cap on rooms framed as Sanctuary (rest + trap) */
  maxSanctuaries: number;
  /** Cap on treasure / sealed vault rooms */
  maxTreasureRooms: number;
}

const FLOORS: FloorConfig[] = [
  {
    floorIndex: 0,
    numRooms: 11,
    roomLength: 5,
    displayName: 'Cellar',
    maxSanctuaries: 1,
    maxTreasureRooms: 2,
  },
  {
    floorIndex: 1,
    numRooms: 13,
    roomLength: 6,
    displayName: 'Catacombs',
    maxSanctuaries: 2,
    maxTreasureRooms: 2,
  },
  {
    floorIndex: 2,
    numRooms: 14,
    roomLength: 6,
    displayName: 'Deep Hold',
    maxSanctuaries: 2,
    maxTreasureRooms: 2,
  },
];

export function getFloorConfig(floorIndex: number): FloorConfig {
  const i = Math.max(0, Math.min(floorIndex, FLOORS.length - 1));
  return FLOORS[i];
}

export function isFinalFloor(floorIndex: number): boolean {
  return floorIndex >= TOTAL_FLOORS - 1;
}
