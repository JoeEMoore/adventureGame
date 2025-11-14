package cpsc224.creatures;

import cpsc224.DamageType;

public abstract class Creature {
    private int health;
    private String name;
    private CreatureModifiers modifiers;
    private CreatureModifiers turnModifiers;

    public Creature(CreatureModifiers modifiers, int health) {
        this.modifiers = modifiers;
        this.health = health;

        //creature spawns with no turn modifiers
        this.turnModifiers = new CreatureModifiers(0, 0, new double[0]);
    }

    public void setName(String creatureName){
        name = creatureName;
    }

    public String getName(){
        return name;
    }

    public int getHealth() {
        return health;
    }

    public CreatureModifiers getModifiers() {
        return modifiers;
    }

    public CreatureModifiers getTurnModifiers() {
        return turnModifiers;
    }

    public void addHealth(int amount) {
        this.health += amount;
    }

    public void takeDamage(int amount) {
        this.health -= amount;
    }

    public void setTurnModifiers(double damage, double evasion, DamageType type, double resistance) {
        this.turnModifiers.addDamage(damage);
        this.turnModifiers.addEvasion(evasion);
        this.turnModifiers.addResistance(type, resistance);

    
   
}

    public void resetTurnModifiers(){
        this.turnModifiers = modifiers.clone();
    }
}
