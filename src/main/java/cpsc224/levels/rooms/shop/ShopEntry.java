package cpsc224.levels.rooms.shop;

import cpsc224.items.Item;

public class ShopEntry {
    private Item item;
    private int quantity;
    private int price;
    
    public ShopEntry(Item item, int quantity, int price) {
        this.item = item;
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
        return item;
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
