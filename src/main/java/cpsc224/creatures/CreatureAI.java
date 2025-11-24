package cpsc224.creatures;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class CreatureAI {
    
    private List<Double> weaponWeights;
    private double totalWeight;
    
    /**
     * Instantiates a creatureAI
     * @param creature the creature
     * @param weaponWeights the weapon weights
     */
    public CreatureAI(Creature creature){
        weaponWeights = new ArrayList<Double>();
        totalWeight = 0.0;
        for (int i = 0; i < creature.getInventory().getWeapons().size(); i++){
            weaponWeights.add(1.0);
            totalWeight+=1;
        }     
    }

    public int calculateMove() {
        Random rand = new Random();
        double num = rand.nextDouble() * totalWeight;
        int index = 0;
        for (Double weaponWeight: weaponWeights){
            num -= weaponWeight;
            if (num <= 0){
                return index;
            }
            index++;
        }
        return 0;
    }
}

