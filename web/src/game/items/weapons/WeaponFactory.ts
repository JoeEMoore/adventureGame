import { weaponNamed, weaponSprite } from '../../utils/icons';
import { MoveFactory } from './moves/MoveFactory';
import { Weapon } from './Weapon';

export const WeaponFactory = {
  createRatClaws(): Weapon {
    return new Weapon('Rat Claws', 1, MoveFactory.createScratchMove(), weaponSprite(2, 4));
  },
  createMushroomCap(): Weapon {
    return new Weapon('Mushroom Cap', 1, MoveFactory.createSporeShotMove(), weaponNamed('Mushroom Cap'));
  },
  createBirdTalons(): Weapon {
    return new Weapon('Bird Talons', 1, MoveFactory.createScratchMove(), null);
  },
  createCrudeBow(): Weapon {
    return new Weapon('Crude Bow', 1, MoveFactory.createShootMove(), weaponSprite(9, 2));
  },
  createLongBow(): Weapon {
    return new Weapon('Long Bow', 2, MoveFactory.createShootMove(), weaponSprite(9, 3));
  },
  createCrossbow(): Weapon {
    return new Weapon('Crossbow', 3, MoveFactory.createShootMove(), weaponSprite(9, 4));
  },
  createToxicStaff(): Weapon {
    return new Weapon('Toxic Staff', 3, MoveFactory.createToxicBoltMove(), weaponSprite(10, 2));
  },
  createHealStaff(): Weapon {
    return new Weapon('Heal Staff', 3, MoveFactory.createHealMove(), weaponSprite(10, 1));
  },
  createRustyDagger(): Weapon {
    return new Weapon('Rusty Dagger', 1, MoveFactory.createSlashMove(), weaponSprite(0, 0));
  },
  createSteelSword(): Weapon {
    return new Weapon('Steel Sword', 2, MoveFactory.createSlashMove(), weaponSprite(0, 2));
  },
  createRoyalSword(): Weapon {
    return new Weapon('Royal Sword', 3, MoveFactory.createSlashMove(), weaponSprite(0, 8));
  },
  createWoodClub(): Weapon {
    return new Weapon('Wood Club', 1, MoveFactory.createSmashMove(), weaponSprite(8, 2));
  },
  createSteelMace(): Weapon {
    return new Weapon('Steel Mace', 2, MoveFactory.createSmashMove(), weaponSprite(5, 0));
  },
  createSteelHammer(): Weapon {
    return new Weapon('Steel Hammer', 3, MoveFactory.createSmashMove(), weaponSprite(4, 4));
  },
  createRudimentaryStaff(): Weapon {
    return new Weapon('Rudimentary Staff', 1, MoveFactory.createMagicMissileMove(), weaponSprite(10, 0));
  },
  createEnchantedStaff(): Weapon {
    return new Weapon('Enchanted Staff', 2, MoveFactory.createMagicMissileMove(), weaponSprite(10, 7));
  },
  createStaffOfPower(): Weapon {
    return new Weapon('Staff Of Power', 3, MoveFactory.createMagicMissileMove(), weaponSprite(10, 6));
  },
  createRockHeal(): Weapon {
    return new Weapon('Rock heal', 1, MoveFactory.createHealMove(), weaponSprite(10, 6));
  },
  createRockSlam(): Weapon {
    return new Weapon('Rock Slam', 3, MoveFactory.createSmashMove(), weaponSprite(10, 6));
  },
  createRockThrow(): Weapon {
    return new Weapon('Rock throw', 3, MoveFactory.createShootMove(), weaponSprite(10, 6));
  },
  createRockCut(): Weapon {
    return new Weapon('Rock Cut', 3, MoveFactory.createSlashMove(), weaponSprite(10, 6));
  },
  creatFrostHeal(): Weapon {
    return new Weapon('Frost heal', 1, MoveFactory.createHealMove(), weaponSprite(10, 6));
  },
  createIceball(): Weapon {
    return new Weapon('Ice ball', 3, MoveFactory.createMagicMissileMove(), weaponSprite(10, 6));
  },
  createIceSpear(): Weapon {
    return new Weapon('Ice spear attack', 3, MoveFactory.createSlashMove(), weaponSprite(10, 6));
  },
  createIceClaws(): Weapon {
    return new Weapon('Ice Claws', 3, MoveFactory.createSlashMove(), weaponSprite(10, 6));
  },
  creatFireHeal(): Weapon {
    return new Weapon('Fire heal', 1, MoveFactory.createHealMove(), weaponSprite(10, 6));
  },
  createFireBall(): Weapon {
    return new Weapon('Fireball', 3, MoveFactory.createMagicMissileMove(), weaponSprite(10, 6));
  },
  createFireSword(): Weapon {
    return new Weapon('Fire sword attack', 3, MoveFactory.createSlashMove(), weaponSprite(10, 6));
  },
  createFireClaws(): Weapon {
    return new Weapon('Fire Claws', 3, MoveFactory.createSlashMove(), weaponSprite(10, 6));
  },
};
