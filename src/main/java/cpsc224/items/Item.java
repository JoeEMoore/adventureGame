package cpsc224.items;

public abstract class Item {

    protected String name;

    private int tier;

    public Item(String name, int tier) {
        this.name = name;
        this.tier = tier;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTier() {
        return tier;
    }

    public void setTier(int tier){
        this.tier = tier;
    }
}
