package cpsc224.pools;

import cpsc224.creatures.Creature;
import cpsc224.creatures.CreatureFactory;

public class BossCreaturePool extends Pool<Creature> {
        
    public BossCreaturePool() {
        addObjectCreator(CreatureFactory::createIceGolem, 2);
        addObjectCreator(CreatureFactory::createFireGolem, 2);
        addObjectCreator(CreatureFactory::createRockGolem, 2);
    }
}
