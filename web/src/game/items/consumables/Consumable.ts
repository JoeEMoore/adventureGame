import { Item } from '../Item';
import type { IconRef } from '../../utils/icons';
import type { EffectsFactory, Effect } from '../../effects/Effect';
import type { Creature } from '../../creatures/Creature';

export class Consumable extends Item {
  private effects: EffectsFactory;
  private affectsSelf: boolean;

  constructor(
    name: string,
    tier: number,
    effects: EffectsFactory,
    affectsSelf: boolean,
    icon: IconRef = null,
  ) {
    super(name, tier, icon);
    this.effects = effects;
    this.affectsSelf = affectsSelf;
  }

  getAffectsSelf(): boolean {
    return this.affectsSelf;
  }

  createEffects(): Effect[] {
    return this.effects();
  }

  applyEffects(creature: Creature): string {
    const messages: string[] = [];
    for (const e of this.createEffects()) {
      messages.push(creature.addEffect(e));
    }
    return messages.filter(Boolean).join(', ');
  }

  getToolTipText(): string {
    return `${this.name} (T${this.tier})\nAffects: ${this.affectsSelf ? 'Self' : 'Enemy'}`;
  }
}
