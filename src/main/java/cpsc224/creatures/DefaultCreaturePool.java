package cpsc224.creatures;

import java.util.Random;

public class DefaultCreaturePool implements CreaturePool {

    @Override
    public Creature getCreature() {
        Random rand = new Random();

        int randValue = rand.nextInt(100);

        if (randValue < 25) {
            return CreatureFactory.createRat();
        } else if (randValue < 50) {
            return CreatureFactory.createGoblin();
        } else if (randValue < 70) {
            return CreatureFactory.createSkeleton();
        } else if (randValue < 80) {
            return CreatureFactory.createWitch();
        } else if (randValue < 90) {
            return CreatureFactory.createGoblin();
        } else if (randValue < 100) {
            return CreatureFactory.createGuardian();
        }

        // should never reach this
        return CreatureFactory.createRat();
    }

}
