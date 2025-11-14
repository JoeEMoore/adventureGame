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

    public void increaseMaxInventory(int increaseAmt){
        this.maxInventory+= increaseAmt;
    }

    public void decreaseMaxInventory(int decreaseAmt){
        this.maxInventory-= decreaseAmt;
    }

    public void increaseMaxConsumables(int increaseAmt){
        this.maxConsumables+= increaseAmt;
    }

    public void decreaseMaxConsumables(int decreaseAmt){
        this.maxConsumables-= decreaseAmt;
    }

}
