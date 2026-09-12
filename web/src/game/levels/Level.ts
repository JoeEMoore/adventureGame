import { Coordinate } from './Coordinate';
import type { Room } from './rooms/Room';

export interface LevelInitializer {
  initializeLevel(): (Room | null)[][];
  getStartRoom(): Coordinate;
  getNumRooms(): number;
}

export class Level {
  private rooms: (Room | null)[][];
  private numRooms: number;
  private startPosition: Coordinate;
  private floorIndex: number;

  constructor(initializer: LevelInitializer, floorIndex = 0) {
    this.rooms = initializer.initializeLevel();
    this.numRooms = initializer.getNumRooms() + 2;
    this.startPosition = initializer.getStartRoom();
    this.floorIndex = floorIndex;
    this.exploreRoom(this.startPosition);
  }

  getFloorIndex(): number {
    return this.floorIndex;
  }

  getRooms(): (Room | null)[][] {
    return this.rooms;
  }

  getNumRooms(): number {
    return this.numRooms;
  }

  getStartPosition(): Coordinate {
    return this.startPosition;
  }

  getRoom(pos: Coordinate): Room | null {
    return this.rooms[pos.getRow()]?.[pos.getCol()] ?? null;
  }

  exploreRoom(pos: Coordinate): void {
    const room = this.getRoom(pos);
    if (!room) return;
    room.setExplored(true);

    const r = pos.getRow();
    const c = pos.getCol();
    const neighbors = [
      new Coordinate(r - 1, c),
      new Coordinate(r + 1, c),
      new Coordinate(r, c - 1),
      new Coordinate(r, c + 1),
    ];
    for (const n of neighbors) {
      const nr = this.getRoom(n);
      if (nr) nr.setDiscovered(true);
    }
  }
}
