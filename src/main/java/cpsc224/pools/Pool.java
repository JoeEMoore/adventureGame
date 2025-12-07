package cpsc224.pools;

import java.util.*;

/**
 * A class to represent a pool of possible objects that can be created.
 * Each object is assigned a weight that determines its chance of being selected.
 * @param <T> the type of objects in the pool
 */
public abstract class Pool<T> {

    Random rand = new Random();
    protected HashMap<ObjectCreator<T>, Double> objectCreators = new HashMap<>();
    protected double totalWeight;

    /**
     * Creates a new pool.
     */
    public Pool() {
        totalWeight = 0.0;
    }

    /**
     * Adds an object creator to the pool.
     * @param objectCreator the object creator
     * @param weight determines the chance of being chosen
     */
    public void addObjectCreator(ObjectCreator<T> objectCreator, double weight) {
        Double prevWeight = objectCreators.put(objectCreator, weight);

        // subtract the prev weight if this object creator already mapped to a value
        if (prevWeight != null)
            totalWeight -= prevWeight;

        // increase total weight
        totalWeight += weight;
    }

    /**
     * Creates a new object from the pool.
     * @return the new object
     */
    public T createNew() {
        double randValue = rand.nextDouble() * totalWeight;
        double cumulativeWeight = 0.0;

        for (ObjectCreator<T> itemCreator : objectCreators.keySet()) {
            cumulativeWeight += objectCreators.get(itemCreator);
            if (randValue <= cumulativeWeight) {
                return itemCreator.createItem();
            }
        }
        return null;
    }
}
