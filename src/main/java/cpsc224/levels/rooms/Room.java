package cpsc224.levels.rooms;

import cpsc224.items.Item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cpsc224.creatures.Creature;

public class Room {

    protected Creature creature;
    protected List<Item> items = new ArrayList<>();
    protected List<Creature> bossPool;
    protected boolean isDiscovered;
    protected boolean isExplored;

    public Room() {
        this(null);
    }

    public Room(Creature creature) {
        this.creature = creature;
        isDiscovered = false;
        isExplored = false;
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

    public void removeCreature() {
        creature = null;
    }

    public void removeItem(Item i) {
        items.remove(i);
    }

    public boolean hasItems() {
        return !items.isEmpty();
    }

    public void clearItems() {
        items.clear();
    }

    public boolean isDiscovered() {
        return isDiscovered;
    }

    public void setDiscovered(boolean isDiscovered) {
        this.isDiscovered = isDiscovered;
    }

    public boolean isExplored() {
        return isExplored;
    }

    public void setExplored(boolean isExplored) {
        this.isExplored = isExplored;
        setDiscovered(true);
    }

}
