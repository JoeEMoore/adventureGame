package cpsc224.creatures;

import java.util.Random;
import java.util.List;

public class CreatureAI {
    
    private Creature creature;
    private List<Double> weaponWeights;
    private double totalWeight;
    
    /**
     * Instantiates a creatureAI
     * @param creature the creature
     * @param weaponWeights the weapon weights
     */
    public CreatureAI(Creature creature){
        this.creature = creature;
        weaponWeights = creature.getweaponWeights();
        totalWeight = 0.0;
        for (int i = 0; i < creature.getweaponWeights().size(); i++){
            totalWeight+=weaponWeights.get(i);
        }     
    }

    public int calculateMove() {
        for (int i = 0; i < creature.getInventory().getWeapons().size(); i++){
            if (creature.getInventory().getWeapon(i).getMove().getUses() == 0){
                totalWeight -= weaponWeights.get(i);
                weaponWeights.set(i,0.0);
            }
        }
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

