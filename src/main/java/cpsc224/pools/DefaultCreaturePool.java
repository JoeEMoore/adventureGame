package cpsc224.pools;

import cpsc224.creatures.Creature;
import cpsc224.creatures.CreatureFactory;

public class DefaultCreaturePool extends Pool<Creature> {

    public DefaultCreaturePool() {
        addObjectCreator(CreatureFactory::createRat, 3);
        addObjectCreator(CreatureFactory::createGoblin, 2);
        addObjectCreator(CreatureFactory::createSlime, 2);
        addObjectCreator(CreatureFactory::createMushroom, 2);
        addObjectCreator(CreatureFactory::createSkeleton, 1.5);
        addObjectCreator(CreatureFactory::createSorcerer, 1);
        addObjectCreator(CreatureFactory::createTroll, 1);
        addObjectCreator(CreatureFactory::createGuardian, 1);
    }
}
