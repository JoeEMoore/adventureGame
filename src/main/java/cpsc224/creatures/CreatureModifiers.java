package cpsc224.creatures;

import java.util.HashMap;

import cpsc224.DamageType;

public class CreatureModifiers {

    private double damage;
    private double evasion;
    private HashMap<DamageType, Double> resistances = new HashMap<>();;


    public CreatureModifiers(double damage, double evasion, double[] resistanceArr) {
        this.damage = damage;
        this.evasion = evasion;

        // Sets each damage type resistance amount to its respective value
        // in resistanceArr. Defaults to 1.0 if there are less values in
        // resistanceArr than there are damage types
        for (int i = 0; i < DamageType.values().length; i++) {
            if (i < resistanceArr.length)
                resistances.put(DamageType.values()[i], resistanceArr[i]);
            else
                resistances.put(DamageType.values()[i], 1.0);
        }
    }

    public double getDamage() {
        return damage;
    }

    public double getEvasion() {
        return evasion;
    }

    public double getResistance(DamageType dt) {
        return resistances.get(dt);
    }

    public void addDamage(double amount) {
        damage += amount;
    }

    public void multiplyDamage(double amount) {
        damage *= amount;
    }

    public void addEvasion(double amount) {
        evasion += amount;
    }

    public void multiplyEvasion(double amount) {
        evasion *= amount;
    }

    public void addResistance(DamageType dt, double amount) {
        double newAmount = resistances.get(dt) + amount;
        resistances.put(dt, newAmount);
    }

    public void multiplyResistance(DamageType dt, double amount) {
        double newAmount = resistances.get(dt) * amount;
        resistances.put(dt, newAmount);
    }
}
