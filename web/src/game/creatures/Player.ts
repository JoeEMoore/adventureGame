import { Creature } from './Creature';
import { CreatureModifiers } from './CreatureModifiers';
import { Inventory } from '../items/Inventory';
import type { IconRef } from '../utils/icons';
import { Coordinate } from '../levels/Coordinate';

export class Player extends Creature {
  private gold = 0;

  constructor(
    name: string,
    maxHealth: number,
    modifiers: CreatureModifiers,
    inventory: Inventory,
    icon: IconRef = null,
  ) {
    super(name, maxHealth, modifiers, inventory, icon, []);
    this.currentPosition = new Coordinate(0, 0);
  }

  getGold(): number {
    return this.gold;
  }

  addGold(n: number): void {
    this.gold += n;
  }

  /** Typo preserved from Java Player.subractGold */
  subractGold(n: number): void {
    this.gold -= n;
  }
}
