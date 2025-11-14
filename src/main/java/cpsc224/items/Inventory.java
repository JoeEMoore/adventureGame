package cpsc224.items;

public class Inventory {
    private int maxInventory;
    private int maxConsumables;

    public Inventory(int maxInventory, int maxConsumables){
       this.maxConsumables = maxConsumables;
       this.maxInventory = maxInventory;
    }

    public int getMaxInventory(){
        return maxInventory;
    }

    public int getMaxConsumables(){
        return maxConsumables;
    }

}
