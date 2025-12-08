package cpsc224.effects;

import cpsc224.creatures.Creature;

import javax.naming.OperationNotSupportedException;

public class RefillEffect extends Effect {

    public RefillEffect(int turns) {
        super(turns, true);
        name = "Refill Moves";
    }

    /**
     * Not supported for this class
     * @param multiplier the multiplier
     * @throws UnsupportedOperationException this operation is not supported
     */
    @Override
    public void multiplyEffect(double multiplier) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String apply(Creature creature ){
        for (int i = 0; i < creature.getInventory().getWeapons().size(); i++){
            creature.getInventory().getWeapon(i).getMove().resetUses();
        }
        return creature.getName() + " refilled all of their weapon uses!";
    }
}
