package cpsc224.levels.rooms.shop;

import cpsc224.items.Item;
import cpsc224.pools.ObjectCreator;

public class ShopEntry {
    private ObjectCreator<Item> itemCreator;
    private int quantity;
    private int price;
    
    public ShopEntry(ObjectCreator<Item> itemCreator, int quantity, int price) {
        this.itemCreator = itemCreator;
        this.quantity = quantity;
        this.price = price;
    }

//    private int calculatePrice(Item item) {
//        int tier = item.getTier();
//
//        switch (tier) {
//            case 1: return 10;
//            case 2: return 20;
//            case 3: return 30;
//
//            default:
//                return 9999;
//        }
//    }

    public int getPrice(){
        return price;
    }

    public Item getItem() {
        return itemCreator.createItem();
    }

    public int getQuantity(){
        return quantity;
    }

    public void decreaseQuantity(){
        if(quantity > 0){
            quantity --;
        }
    }

}
