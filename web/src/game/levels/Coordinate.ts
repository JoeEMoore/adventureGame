export class Coordinate {
  private row: number;
  private col: number;

  constructor(row: number, col: number) {
    this.row = row;
    this.col = col;
  }

  getRow(): number {
    return this.row;
  }

  getCol(): number {
    return this.col;
  }

  equals(other: Coordinate | null | undefined): boolean {
    if (!other) return false;
    return this.row === other.row && this.col === other.col;
  }

  isAdjacent(other: Coordinate): boolean {
    const dr = Math.abs(this.row - other.row);
    const dc = Math.abs(this.col - other.col);
    return (dr === 1 && dc === 0) || (dr === 0 && dc === 1);
  }

  toKey(): string {
    return `${this.row},${this.col}`;
  }
}
