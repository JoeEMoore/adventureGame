package cpsc224.items;

import javax.swing.*;
import java.awt.*;

public abstract class Item {

    protected String name;
    protected int tier;
    protected ImageIcon icon;

    public Item(String name, int tier, ImageIcon icon) {
        this.name = name;
        this.tier = tier;
        this.icon = icon;
    }

    public Item(String name, int tier) {
        this(name, tier, null);
    }

    /**
     * Gets the icon.
     * @return the icon
     */
    public ImageIcon getIcon() { return icon; }


    /**
     * Maps item tier to its multiplier value.
     * @param tier the item tier
     * @return the multiplier
     */
    public static double mapTierToMultiplier(int tier) {
        return switch (tier) {
            case 2 -> 1.5;
            case 3 -> 2.0;
            default -> 1.0;
        };
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

    public abstract String getToolTipText();
}
