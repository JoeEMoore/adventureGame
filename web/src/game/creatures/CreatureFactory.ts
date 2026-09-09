import { CreatureModifiers } from './CreatureModifiers';
import { Creature } from './Creature';
import { Player } from './Player';
import { Inventory } from '../items/Inventory';
import { WeaponFactory } from '../items/weapons/WeaponFactory';
import { ConsumableFactory } from '../items/consumables/ConsumableFactory';
import { creatureIcon } from '../utils/icons';

function mods(damage: number, evasion: number, resists: number[]): CreatureModifiers {
  return new CreatureModifiers(damage, evasion, resists);
}

export const CreatureFactory = {
  createPlayer(): Player {
    const inv = new Inventory(4, 4);
    inv.addItem(WeaponFactory.createRustyDagger());
    inv.addItem(ConsumableFactory.createSmallHealthPotion());
    inv.addItem(ConsumableFactory.createWeaponRefillPotion());
    return new Player('Player', 200, mods(1, 0.2, [1, 1, 1, 1]), inv, null);
  },

  createMagePlayer(): Player {
    const inv = new Inventory(4, 4);
    inv.addItem(WeaponFactory.createEnchantedStaff());
    inv.addItem(WeaponFactory.createRustyDagger());
    inv.addItem(ConsumableFactory.createSmallHealthPotion());
    inv.addItem(ConsumableFactory.createWeaponRefillPotion());
    return new Player('Mage', 200, mods(1, 0.2, [1, 1, 1, 0.8]), inv, creatureIcon('mage2', false));
  },

  createBluntPlayer(): Player {
    const inv = new Inventory(4, 4);
    inv.addItem(WeaponFactory.createSteelMace());
    inv.addItem(WeaponFactory.createCrudeBow());
    inv.addItem(ConsumableFactory.createSmallHealthPotion());
    inv.addItem(ConsumableFactory.createWeaponRefillPotion());
    return new Player('Barbarian', 200, mods(1, 0.2, [0.8, 1, 1, 1]), inv, creatureIcon('Barb2', false));
  },

  createSlashPlayer(): Player {
    const inv = new Inventory(4, 4);
    inv.addItem(WeaponFactory.createSteelSword());
    inv.addItem(WeaponFactory.createWoodClub());
    inv.addItem(ConsumableFactory.createSmallHealthPotion());
    inv.addItem(ConsumableFactory.createWeaponRefillPotion());
    return new Player('Knight', 200, mods(1, 0.2, [1, 0.8, 1, 1]), inv, creatureIcon('knight2', false));
  },

  createRangePlayer(): Player {
    const inv = new Inventory(4, 4);
    inv.addItem(WeaponFactory.createLongBow());
    inv.addItem(WeaponFactory.createRudimentaryStaff());
    inv.addItem(ConsumableFactory.createSmallHealthPotion());
    inv.addItem(ConsumableFactory.createWeaponRefillPotion());
    return new Player('Ranger', 200, mods(1, 0.2, [1, 1, 0.8, 1]), inv, creatureIcon('Ranger', false));
  },

  createRat(): Creature {
    const inv = new Inventory(4, 0);
    inv.addItem(WeaponFactory.createRatClaws());
    return new Creature('Rat', 10, mods(1, 0.15, [1, 1, 1, 1]), inv, creatureIcon('Rat', true), [1]);
  },

  createTroll(): Creature {
    const inv = new Inventory(4, 0);
    inv.addItem(WeaponFactory.createWoodClub());
    return new Creature('Troll', 150, mods(2, 0.05, [0.7, 1.5, 0.8, 2.0]), inv, null, [1]);
  },

  createSlime(): Creature {
    const inv = new Inventory(4, 0);
    inv.addItem(WeaponFactory.createRatClaws());
    return new Creature('Slime', 25, mods(0.8, 0.25, [0.8, 1.2, 0.8, 1.0]), inv, creatureIcon('Slime', false), [1]);
  },

  createBird(): Creature {
    const inv = new Inventory(4, 0);
    inv.addItem(WeaponFactory.createBirdTalons());
    return new Creature('Bird', 30, mods(3, 0.2, [0.5, 0.7, 2.0, 1.4]), inv, null, [1]);
  },

  createSorcerer(): Creature {
    const inv = new Inventory(4, 0);
    inv.addItem(WeaponFactory.createToxicStaff());
    inv.addItem(WeaponFactory.createEnchantedStaff());
    return new Creature('Sorcerer', 75, mods(1.5, 0.1, [1.2, 1.4, 0.8, 0.7]), inv, creatureIcon('Sorcerer', true), [2, 3]);
  },

  createMushroom(): Creature {
    const inv = new Inventory(4, 0);
    inv.addItem(WeaponFactory.createMushroomCap());
    return new Creature('Mushroom', 40, mods(1, 0.1, [1.2, 1, 1, 0.8]), inv, creatureIcon('Mushroom', false), [1]);
  },

  createGuardian(): Creature {
    const inv = new Inventory(4, 0);
    inv.addItem(WeaponFactory.createRoyalSword());
    inv.addItem(WeaponFactory.createSteelHammer());
    return new Creature('Guardian', 105, mods(1.5, 0.1, [0.5, 0.8, 1.1, 2.0]), inv, null, [2, 1]);
  },

  createGoblin(): Creature {
    const inv = new Inventory(4, 0);
    inv.addItem(WeaponFactory.createRustyDagger());
    inv.addItem(WeaponFactory.createWoodClub());
    return new Creature('Goblin', 50, mods(1, 0.1, [1, 0.9, 1.2, 1.4]), inv, creatureIcon('Goblin', true), [1.5, 1]);
  },

  createSkeleton(): Creature {
    const inv = new Inventory(4, 0);
    inv.addItem(WeaponFactory.createLongBow());
    inv.addItem(WeaponFactory.createRustyDagger());
    return new Creature('Skeleton', 50, mods(1, 0.1, [1.5, 0.5, 0.5, 2.0]), inv, creatureIcon('Skeleton', true), [3, 1]);
  },

  createFireGolem(): Creature {
    const inv = new Inventory(4, 0);
    inv.addItem(WeaponFactory.creatFireHeal());
    inv.addItem(WeaponFactory.createFireBall());
    inv.addItem(WeaponFactory.createFireSword());
    inv.addItem(WeaponFactory.createFireClaws());
    return new Creature(
      'Fire golem',
      200,
      mods(1.5, 0.05, [1, 1, 1.5, 0.5]),
      inv,
      creatureIcon('FireGolemBoss', true),
      [1, 2, 2, 1.5],
    );
  },

  createRockGolem(): Creature {
    const inv = new Inventory(4, 0);
    inv.addItem(WeaponFactory.createRockHeal());
    inv.addItem(WeaponFactory.createRockSlam());
    inv.addItem(WeaponFactory.createRockThrow());
    inv.addItem(WeaponFactory.createRockCut());
    return new Creature(
      'Rock golem',
      200,
      mods(1, 0.05, [0.5, 0.5, 0.5, 2.0]),
      inv,
      creatureIcon('RockGolemBoss', true),
      [1, 2, 2, 1.5],
    );
  },

  createIceGolem(): Creature {
    const inv = new Inventory(4, 0);
    inv.addItem(WeaponFactory.creatFrostHeal());
    inv.addItem(WeaponFactory.createIceball());
    inv.addItem(WeaponFactory.createIceSpear());
    inv.addItem(WeaponFactory.createIceClaws());
    return new Creature(
      'Ice golem',
      200,
      mods(1.5, 0.05, [1.5, 1.5, 1, 0.5]),
      inv,
      creatureIcon('IceGolemBoss', true),
      [1, 2, 2, 1.5],
    );
  },
};
