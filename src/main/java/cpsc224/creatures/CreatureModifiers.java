package cpsc224.creatures;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import cpsc224.damagetypes.DamageType;
import cpsc224.damagetypes.DamageTypeUtils;

/**
 * A class to represent the damage, evasion, and resistance modifiers of a creature.
 */

public class CreatureModifiers implements Cloneable {

    private double damage;
    private double evasion;
    private HashMap<DamageType, Double> resistances = new HashMap<>();


    /**
     * Creates modifiers with damage, evasion, and resistances
     * @param damage the damage modifier
     * @param evasion the evasion modifier
     * @param resistances the resistance modifiers (Blunt, Slice, Projectile, Magic)
     */
    public CreatureModifiers(double damage, double evasion, Queue<Double> resistances) {
        this.damage = damage;
        this.evasion = evasion;

        // Sets each damage type resistance amount to its respective value
        // in resistanceArr. Defaults to 1.0 if there are less values in
        // resistanceArr than there are damage types.
        Collection<DamageType> types = DamageTypeUtils.getDamageTypes();
        for (DamageType type : types) {
            if (resistances.size() > 0)
                this.resistances.put(type, resistances.poll());
            else
                this.resistances.put(type, 1.0);
        }
    }

    /**
     * Gets the damage modifier.
     * @return damage modifier
     */
    public double getDamage() {
        return damage;
    }

    /**
     * Gets the evasion modifier.
     * @return the evasion modifier
     */
    public double getEvasion() {
        return evasion;
    }

    /**
     * Gets the specified resistance modifier.
     * @param dt the damage type
     * @return the resistance modifier
     */
    public double getResistance(DamageType dt) {
        return resistances.getOrDefault(dt, 1.0);
    }

    /**
     * Adds to the damage modifier.
     * @param amount the amount to add
     */
    public void addDamage(double amount) {
        damage += amount;
    }

    /**
     * Multiplies to the damage modifier.
     * @param amount the amount to multiply
     */
    public void multiplyDamage(double amount) {
        damage *= amount;
    }

    /**
     * Sets the damage modifier.
     * @param damage the new damage modifier
     */
    public void setDamage(double damage) {
        this.damage = damage;
    }

    /**
     * Adds to the evasion modifier.
     * @param amount the amount to add
     */
    public void addEvasion(double amount) {
        evasion += amount;
    }

    /**
     * Multiplies to the evasion modifier.
     * @param amount the amount to multiply
     */
    public void multiplyEvasion(double amount) {
        evasion *= amount;
    }

    /**
     * Sets the evasion modifier.
     * @param evasion the new evasion modifier
     */
    public void setEvasion(double evasion) {
        this.evasion = evasion;
    }

    /**
     * Adds to the specified resistance modifier.
     * @param dt the damage type
     * @param amount the amount to add
     * @return true if the resistance was found
     */
    public boolean addResistance(DamageType dt, double amount) {
        if (resistances.get(dt) == null)
            return false;

        double newAmount = resistances.get(dt) + amount;
        resistances.put(dt, newAmount);
        return true;
    }

    /**
     * Multiplies to the specified resistance modifier.
     * @param dt the damage type
     * @param amount the amount to multiply
     * @return true if the resistance was found
     */
    public boolean multiplyResistance(DamageType dt, double amount) {
        if (resistances.get(dt) == null)
            return false;

        double newAmount = resistances.get(dt) * amount;
        resistances.put(dt, newAmount);
        return true;
    }

    /**
     * Sets the specified resistance type.
     * @param dt the damage type
     * @param resistance the new resistance modifier
     */
    public void setResistance(DamageType dt, double resistance) {
        resistances.put(dt, resistance);
    }

    /**
     * Creates a deep copy of this modifier class.
     * @return a copy of this modifier class.
     */
    @Override
    public CreatureModifiers clone() {
        final CreatureModifiers clone = new CreatureModifiers(damage, evasion, new LinkedList<Double>());
        for (DamageType dt : DamageTypeUtils.getDamageTypes()) {
            clone.setResistance(dt, resistances.get(dt));
        }

        return clone;
    }
}
