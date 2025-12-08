package cpsc224.pools;

import cpsc224.items.weapons.Weapon;

public class TestWeaponPool extends Pool<Weapon> {

    Weapon w;

    public TestWeaponPool(Weapon w) {
        this.w = w;
    }

    @Override
    public Weapon createNew() {
        return w;
    }
}
