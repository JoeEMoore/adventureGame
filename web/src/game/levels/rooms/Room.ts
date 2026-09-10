import type { Creature } from '../../creatures/Creature';
import type { Item } from '../../items/Item';

export type RoomDecorationKind = 'skull' | 'bones' | 'rock' | 'rock2' | 'barrel';

export interface RoomDecoration {
  kind: RoomDecorationKind;
  /** Percent offsets within the room (0–100) */
  x: number;
  y: number;
  rotation: number;
  scale: number;
  /** Flip across the vertical axis (left/right). */
  flipX: boolean;
}

/** Full-room overlays in `assets/sprites/roomTextures/{kind}.png`. */
export type RoomVineKind = 'smallVine' | 'bigVine' | 'shop';

/** Full-room overlay for the back wall — no rotation, optional horizontal flip. */
export interface RoomVine {
  kind: RoomVineKind;
  flipX: boolean;
}

export class Room {
  protected creature: Creature | null = null;
  protected items: Item[] = [];
  protected discovered = false;
  protected explored = false;
  protected decorations: RoomDecoration[] = [];
  protected vines: RoomVine[] = [];

  constructor(creature: Creature | null = null) {
    this.creature = creature;
  }

  getCreature(): Creature | null {
    return this.creature;
  }

  setCreature(creature: Creature | null): void {
    this.creature = creature;
  }

  removeCreature(): void {
    this.creature = null;
  }

  hasCreature(): boolean {
    return this.creature !== null;
  }

  getItems(): Item[] {
    return this.items;
  }

  addItem(item: Item): void {
    this.items.push(item);
  }

  removeItem(item: Item): void {
    const i = this.items.indexOf(item);
    if (i >= 0) this.items.splice(i, 1);
  }

  removeItemAt(index: number): Item | null {
    if (index < 0 || index >= this.items.length) return null;
    return this.items.splice(index, 1)[0];
  }

  clearItems(): void {
    this.items = [];
  }

  hasItems(): boolean {
    return this.items.length > 0;
  }

  getDecorations(): RoomDecoration[] {
    return this.decorations;
  }

  setDecorations(decorations: RoomDecoration[]): void {
    this.decorations = decorations;
  }

  getVines(): RoomVine[] {
    return this.vines;
  }

  setVines(vines: RoomVine[]): void {
    this.vines = vines;
  }

  isDiscovered(): boolean {
    return this.discovered;
  }

  setDiscovered(v: boolean): void {
    this.discovered = v;
  }

  isExplored(): boolean {
    return this.explored;
  }

  setExplored(v: boolean): void {
    this.explored = v;
    if (v) this.discovered = true;
  }
}

export class BossRoom extends Room {}
