package cpsc224.creatures;

public abstract class Creature {
    private int health;
    private CreatureModifiers modifiers;
    private CreatureModifiers turnModifiers;

    public Creature(CreatureModifiers modifiers, int health) {
        this.modifiers = modifiers;
        this.health = health;

        
        this.turnModifiers = new CreatureModifiers(0, 0, new double[0]);
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
}
