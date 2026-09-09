import { CreatureFactory } from '../creatures/CreatureFactory';
import type { Creature } from '../creatures/Creature';
import { Pool } from './Pool';

export class DefaultCreaturePool extends Pool<Creature> {
  constructor() {
    super();
    this.addObjectCreator(() => CreatureFactory.createRat(), 3);
    this.addObjectCreator(() => CreatureFactory.createGoblin(), 2);
    this.addObjectCreator(() => CreatureFactory.createSlime(), 2);
    this.addObjectCreator(() => CreatureFactory.createMushroom(), 2);
    this.addObjectCreator(() => CreatureFactory.createSkeleton(), 1.5);
    this.addObjectCreator(() => CreatureFactory.createSorcerer(), 1);
    this.addObjectCreator(() => CreatureFactory.createTroll(), 1);
    this.addObjectCreator(() => CreatureFactory.createGuardian(), 1);
  }
}

export class BossCreaturePool extends Pool<Creature> {
  constructor() {
    super();
    this.addObjectCreator(() => CreatureFactory.createIceGolem(), 2);
    this.addObjectCreator(() => CreatureFactory.createFireGolem(), 2);
    this.addObjectCreator(() => CreatureFactory.createRockGolem(), 2);
  }
}
