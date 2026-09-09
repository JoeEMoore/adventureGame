import { Item } from '../Item';
import type { IconRef } from '../../utils/icons';
import type { Move } from './moves/Move';

export class Weapon extends Item {
  private move: Move;

  constructor(name: string, tier: number, move: Move, icon: IconRef = null) {
    super(name, tier, icon);
    this.move = move;
    if (move) move.setTier(tier);
  }

  getMove(): Move {
    return this.move;
  }

  override toString(): string {
    if (this.move.getMaxUses() < 0) return this.name;
    return `${this.name} (${this.move.getUses()})`;
  }

  getToolTipText(): string {
    const m = this.move;
    const uses = m.getMaxUses() < 0 ? '∞' : `${m.getUses()}/${m.getMaxUses()}`;
    return `${this.name} (T${this.tier})\n${m.getName()}: ${m.getDamage()} ${m.getDamageType()} dmg\nAcc: ${m.getAccuracy()} Uses: ${uses}`;
  }
}
