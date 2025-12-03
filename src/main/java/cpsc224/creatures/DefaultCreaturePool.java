package cpsc224.creatures;

import java.util.Random;

public class DefaultCreaturePool implements CreaturePool {

    @Override
    public Creature getCreature() {
        Random rand = new Random();

        int randValue = rand.nextInt(100);

        if (randValue < 20) {                       
            return CreatureFactory.createRat();         // 20% chance for rat
        } else if (randValue < 40) {
            return CreatureFactory.createGoblin();      // 20% chance for goblin
        } else if (randValue < 55) {
            return CreatureFactory.createSkeleton();    // 15% chance for skeleton
        } else if (randValue < 65) {
            return CreatureFactory.createWitch();       // 10% chance for witch
        } else if (randValue < 75) {
            return CreatureFactory.createGoblin();      // 10% chance for goblin
        } else if (randValue < 85) {
            return CreatureFactory.createGuardian();    // 10% chance for guardian
        } else {
            return null;                                // 15% chance for nothing
        }
    }

}
