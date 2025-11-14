package cpsc224;

import cpsc224.creatures.Creature;
import cpsc224.creatures.Player;
import cpsc224.items.weapons.Weapon;

public class Fight {

    Player player;
    Creature enemy;
    private boolean isPlayersTurn;
    
    public Fight(Player player, Creature enemy) {
        this.player = player;
        this.enemy = enemy;
    }

    public void performAttack(Creature source, Creature target, Weapon weapon) {
        
    }
}
