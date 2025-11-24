package cpsc224.levels;

import cpsc224.items.Item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cpsc224.creatures.Creature;

public class Room {

    protected List<Creature> creatures = new ArrayList<>();
    protected List<Item> items = new ArrayList<>();

    public Room() {
        
    }

    public Collection<Creature> getCreatures() {
        return creatures;
    }

    public Collection<Item> getItems() {
        return items;
    }

    public void addCreature(Creature c) {
        creatures.add(c);
    }

    public void addItem(Item i) {
        items.add(i);
    }

    public void removCreature(Creature c) {
        creatures.remove(c);
    }

    public void removeItem(Item i) {
        items.remove(i);
    }

    public void clearCreatures() {
        creatures.clear();
    }

    public void clearItems() {
        items.clear();
    }

}
