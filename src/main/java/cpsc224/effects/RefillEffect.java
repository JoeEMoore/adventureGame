package cpsc224.effects;

import cpsc224.creatures.Creature;

import javax.naming.OperationNotSupportedException;

public class RefillEffect extends Effect {

    public RefillEffect(int turns) {
        super(turns, true);
    }

    /**
     * Has no effect on RefillEffect.
     * @param multiplier the multiplier
     */
    @Override
    public void multiplyEffect(double multiplier) {
        // do nothing
    }

    @Override
    public String apply(Creature creature ){
        for (int i = 0; i < creature.getInventory().getWeapons().size(); i++){
            creature.getInventory().getWeapon(i).getMove().setUsesToMax();
        }
        return creature.getName() + " refilled all of their weapon uses!";
    }
}
