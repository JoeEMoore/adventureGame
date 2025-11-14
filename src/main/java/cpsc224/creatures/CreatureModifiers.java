package cpsc224.creatures;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import cpsc224.damagetypes.DamageType;
import cpsc224.damagetypes.DamageTypeUtils;

public class CreatureModifiers implements Cloneable {

    private double damage;
    private double evasion;
    private HashMap<DamageType, Double> resistances = new HashMap<>();


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

    public double getDamage() {
        return damage;
    }

    public double getEvasion() {
        return evasion;
    }

    public double getResistance(DamageType dt) {
        return resistances.getOrDefault(dt, 1.0);
    }

    public void addDamage(double amount) {
        damage += amount;
    }

    public void multiplyDamage(double amount) {
        damage *= amount;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public void addEvasion(double amount) {
        evasion += amount;
    }

    public void multiplyEvasion(double amount) {
        evasion *= amount;
    }

    public void setEvasion(double evasion) {
        this.evasion = evasion;
    }

    public boolean addResistance(DamageType dt, double amount) {
        if (resistances.get(dt) == null)
            return false;

        double newAmount = resistances.get(dt) + amount;
        resistances.put(dt, newAmount);
        return true;
    }

    public boolean multiplyResistance(DamageType dt, double amount) {
        if (resistances.get(dt) == null)
            return false;

        double newAmount = resistances.get(dt) * amount;
        resistances.put(dt, newAmount);
        return true;
    }

    public void setResistance(DamageType dt, double resistance) {
        resistances.put(dt, resistance);
    }

    @Override
    public CreatureModifiers clone() {
        final CreatureModifiers clone = new CreatureModifiers(damage, evasion, new LinkedList<Double>());
        for (DamageType dt : DamageTypeUtils.getDamageTypes()) {
            clone.setResistance(dt, resistances.get(dt));
        }

        return clone;
    }
}
