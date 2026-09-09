import type { Creature } from './Creature';

export class CreatureAI {
  private creature: Creature;
  private weaponWeights: number[];
  private totalWeight: number;

  constructor(creature: Creature) {
    this.creature = creature;
    this.weaponWeights = [...creature.getWeaponWeights()];
    this.totalWeight = this.weaponWeights.reduce((a, b) => a + b, 0);
  }

  calculateMove(): number {
    const weapons = this.creature.getInventory().getWeapons();
    let total = this.totalWeight;
    const weights = [...this.weaponWeights];

    for (let i = 0; i < weapons.length && i < weights.length; i++) {
      if (weapons[i].getMove().getUses() === 0) {
        total -= weights[i];
        weights[i] = 0;
      }
    }

    if (total <= 0) return 0;

    let roll = Math.random() * total;
    for (let i = 0; i < weights.length; i++) {
      roll -= weights[i];
      if (roll <= 0) return i;
    }
    return 0;
  }
}
