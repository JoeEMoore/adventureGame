package cpsc224.effects;

import java.text.DecimalFormat;

import cpsc224.creatures.Creature;

/**
 * A class to represent effects that can be applied to creatures.
 */
public abstract class Effect {

    protected int turns;
    protected String name;
    protected boolean isAppliedInstantly;

    /**
     * Creates an effect that lasts the specified number of turns.
     * @param turns the number of turns
     */
    public Effect(int turns) {
        this.turns = turns;
        isAppliedInstantly = false;
    }

    /**
     * Creates an effect that lasts the specified number of turns and may be applied instantly.
     * @param turns the number of turns
     * @param isAppliedInstantly true if the effect should be applied instantly
     */
    public Effect(int turns, boolean isAppliedInstantly) {
        this.turns = turns;
        this.isAppliedInstantly = isAppliedInstantly;
    }

    /**
     * Gets the number of turns left.
     * @return the number of turns
     */
    public int getTurns() {
        return turns;
    }

    /**
     * Gets the name.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Applies the effect to the specified creature and 
     * decrements the number of turns left.
     * @param creature the creature
     * @return the result as a string
     */
    public String applyEffect(Creature creature) {
        turns--;
        return apply(creature);
    }

    /**
     * Predicate for if the effect is applied instantly.
     * @return true if applied instantly
     */
    public boolean isAppliedInstantly() {
        return isAppliedInstantly;
    }

    /**
     * Creats a string of the effect in the form: Name (turns).
     */
    public String toString() {
        return name + " (" + turns + ")";
    }

    /**
     * Rounds a double to one digit after the decimal place.
     * @param d the double
     * @return the rounded value as a string
     */
    protected static String roundDouble(double d) {
        DecimalFormat df = new DecimalFormat("#.#");
        return df.format(d);
    }

    /**
     * Applies the effect to the creature.
     * @param creature the creature
     * @return the result as a string
     */
    protected abstract String apply(Creature creature);

    public abstract String effectMessage(Creature creature);
}
