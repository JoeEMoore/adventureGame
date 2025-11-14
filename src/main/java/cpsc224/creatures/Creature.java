package cpsc224.creatures;

import java.security.cert.CertPath;

public abstract class Creature {
    private int health;
    private CreatureModifiers modifiers;
    private CreatureModifiers turnModifiers;

    public
    
      public Creature(CreatureModifiers modifiers, int health){
        this.modifiers = modifiers;
        this.health = health;
        
    }


    public int getHealth(){
        return health;
    }

    
}
