import type { Creature } from '../creatures/Creature';

export abstract class Effect {
  turns: number;
  name = '';
  isAppliedInstantly: boolean;

  constructor(turns: number, isAppliedInstantly = false) {
    this.turns = turns;
    this.isAppliedInstantly = isAppliedInstantly;
  }

  getTurns(): number {
    return this.turns;
  }

  getName(): string {
    return this.name;
  }

  applyEffect(creature: Creature): string {
    this.turns--;
    return this.apply(creature);
  }

  toString(): string {
    return `${this.name} (${this.turns})`;
  }

  abstract multiplyEffect(multiplier: number): void;
  protected abstract apply(creature: Creature): string;
}

export type EffectsFactory = () => Effect[];
