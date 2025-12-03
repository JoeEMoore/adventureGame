package cpsc224.items.weapons;
import java.util.*;
public class WeaponPool {

    private final List<Weapon> allWeapons;

    public WeaponPool() {
        allWeapons = List.of(
            WeaponFactory.createDullSword(),
            WeaponFactory.createRatClaws(),
            WeaponFactory.createBirdTalons(),
            WeaponFactory.createWoodClub(),
            WeaponFactory.createToxicStaff(),
            WeaponFactory.createHealStaff(),
            WeaponFactory.createRoyalSword(),
            WeaponFactory.createRustyDagger(),
            WeaponFactory.createSteelHammer(),
            WeaponFactory.createBow()
        );
    }

    public List<Weapon> getAllWeapons(){
        return allWeapons;
    }

    public List<Weapon> getRandomWeapons(int count) {
        List<Weapon> copy = new ArrayList<>(allWeapons);
        Collections.shuffle(copy);
        return copy.subList(0, Math.min(count, copy.size()));
    }

}
