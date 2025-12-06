package cpsc224.items.consumables;

import java.util.Arrays;
import java.util.List;

import cpsc224.effects.*;

/**
 * A factory class to create consumables.
 */
public class ConsumableFactory {

    public static Consumable createSmallHealthPotion() {
        final String name = "Small Health Potion";
        final int tier = 1;
        final EffectsFactory effects = () -> {
            return List.of(new HealEffect(25));
        };

        return new Consumable(name, tier, effects, true);
    }

    public static Consumable createMediumHealthPotion() {
        final String name = "Medium Health Potion";
        final int tier = 2;
        final EffectsFactory effects = () -> {
            return List.of(new HealEffect(50));
        };

        return new Consumable(name, tier, effects, true);
    }

    public static Consumable createLargeHealthPotion() {
        final String name = "Large Health Potion";
        final int tier = 3;
        final EffectsFactory effects = () -> {
            return List.of(new HealEffect(75));
        };

        return new Consumable(name, tier, effects, true);
    }

    public static Consumable createSmallPoisonPotion() {
        final String name = "Small Poison Potion";
        final int tier = 1;
        final EffectsFactory effects = () -> {
            return List.of(new PoisonEffect(2));
        };

        return new Consumable(name, tier, effects, false);
    }

    public static Consumable createMediumPoisonPotion() {
        final String name = "Medium Poison Potion";
        final int tier = 2;
        final EffectsFactory effects = () -> {
            return List.of(new PoisonEffect(3));
        };

        return new Consumable(name, tier, effects, false);
    }

    public static Consumable createLargePoisonPotion() {
        final String name = "Large Poison Potion";
        final int tier = 3;
        final EffectsFactory effects = () -> {
            return List.of(new PoisonEffect(5));
        };

        return new Consumable(name, tier, effects, false);
    }

    public static Consumable createSmallDamagePotion() {
        final String name = "Small Damage Potion";
        final int tier = 1;
        final EffectsFactory effects = () -> {
            return List.of(new DamageEffect(1,10));
        };

        return new Consumable(name, tier, effects, false);
    }

    public static Consumable createMediumDamagePotion() {
        final String name = "Medium Damage Potion";
        final int tier = 2;
        final EffectsFactory effects = () -> {
            return List.of(new DamageEffect(1,15));
        };

        return new Consumable(name, tier, effects, false);
    }

    public static Consumable createLargeDamagePotion() {
        final String name = "Large Damage Potion";
        final int tier = 3;
        final EffectsFactory effects = () -> {
            return List.of(new DamageEffect(1,25));
        };

        return new Consumable(name, tier, effects, false);
    }

    public static Consumable createStrengthPotion() {
        final String name = "Strength Potion";
        final int tier = 3;
        final EffectsFactory effects = () -> {
            return List.of(new StrengthEffect(2,2));
        };

        return new Consumable(name, tier, effects, true);
    }

    public static Consumable createWeaponRefillPotion() {
        final String name = "Weapon Refill Potion";
        final int tier = 2;
        final EffectsFactory effects = () -> {
            return List.of(new RefillEffect(1));
        };

        return new Consumable(name, tier, effects, true);
    }
}
