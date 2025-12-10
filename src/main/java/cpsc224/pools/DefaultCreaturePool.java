package cpsc224.pools;

import cpsc224.creatures.Creature;
import cpsc224.creatures.CreatureFactory;

public class DefaultCreaturePool extends Pool<Creature> {

    public DefaultCreaturePool() {
        addObjectCreator(CreatureFactory::createRat, 3);
        addObjectCreator(CreatureFactory::createGoblin, 2);
        addObjectCreator(CreatureFactory::createSkeleton, 1.5);
        addObjectCreator(CreatureFactory::createWitch, 1);
        addObjectCreator(CreatureFactory::createTroll, 1);
        addObjectCreator(CreatureFactory::createGuardian, 1);
        addObjectCreator(CreatureFactory::createSlime, 1);
    }
}
