package cpsc224.creatures;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;

import cpsc224.items.weapons.*;

public class CreatureAI {
    
    private List<Double> weaponWeights;
    private double totalWeight;
    
    /**
     * Instantiates a creatureAI
     * @param creature the creature
     * @param weaponWeights the weapon weights
     */
    public CreatureAI(Creature creature){
        weaponWeights = creature.getweaponWeights();
        totalWeight = 0.0;
        for (int i = 0; i < creature.getweaponWeights().size(); i++){
            totalWeight+=creature.getweaponWeights().get(i);
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

