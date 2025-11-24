package cpsc224.levels.rooms;

import cpsc224.items.Item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cpsc224.creatures.Creature;

public class Room {

    protected Creature creature;
    protected List<Item> items = new ArrayList<>();

    public Room() {
        
    }

    public Room(Creature creature) {
        this.creature = creature;
    }

    public Creature getCreature() {
        return creature;
    }

    public boolean hasCreature() {
        return creature != null;
    }

    public Collection<Item> getItems() {
        return items;
    }

    public void setCreature(Creature c) {
        creature = c;
    }

    public void addItem(Item i) {
        items.add(i);
    }

    public void removCreature(Creature c) {
        creature = null;
    }

    public void removeItem(Item i) {
        items.remove(i);
    }

    public void clearItems() {
        items.clear();
    }

}
