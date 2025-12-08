package cpsc224.pools;
import cpsc224.items.weapons.Weapon;
import cpsc224.items.weapons.WeaponFactory;

public class ShopWeaponPool extends Pool<Weapon> {

    public ShopWeaponPool() {
        addObjectCreator(WeaponFactory::createSteelSword, 2);
        addObjectCreator(WeaponFactory::createLongBow, 2);
        addObjectCreator(WeaponFactory::createSteelMace, 2);
        addObjectCreator(WeaponFactory::createEnchantedStaff, 2);
        addObjectCreator(WeaponFactory::createRoyalSword, 1.5);
        addObjectCreator(WeaponFactory::createCrossbow, 1.5);
        addObjectCreator(WeaponFactory::createSteelHammer, 1.5);
        addObjectCreator(WeaponFactory::createStaffOfPower, 1.5);
        addObjectCreator(WeaponFactory::createToxicStaff, 1.5);
        addObjectCreator(WeaponFactory::createHealStaff, 1.5);
    }
}
