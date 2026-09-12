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
    const inv = new Inventory(4, 4, 3);
    inv.addItem(WeaponFactory.createRustyDagger());
    inv.addItem(ConsumableFactory.createSmallHealthPotion());
    inv.addItem(ConsumableFactory.createWeaponRefillPotion());
    return new Player('Player', 200, mods(1, 0.2, [1, 1, 1, 1]), inv, null);
  },

  createMagePlayer(): Player {
    const inv = new Inventory(4, 4, 3);
    inv.addItem(WeaponFactory.createEnchantedStaff());
    inv.addItem(WeaponFactory.createRustyDagger());
    inv.addItem(ConsumableFactory.createSmallHealthPotion());
    inv.addItem(ConsumableFactory.createWeaponRefillPotion());
    return new Player('Mage', 200, mods(1, 0.2, [1, 1, 1, 0.8]), inv, creatureIcon('mage2', false));
  },

  createBluntPlayer(): Player {
    const inv = new Inventory(4, 4, 3);
    inv.addItem(WeaponFactory.createSteelMace());
    inv.addItem(WeaponFactory.createCrudeBow());
    inv.addItem(ConsumableFactory.createSmallHealthPotion());
    inv.addItem(ConsumableFactory.createWeaponRefillPotion());
    return new Player('Barbarian', 200, mods(1, 0.2, [0.8, 1, 1, 1]), inv, creatureIcon('Barb2', false));
  },

  createSlashPlayer(): Player {
    const inv = new Inventory(4, 4, 3);
    inv.addItem(WeaponFactory.createSteelSword());
    inv.addItem(WeaponFactory.createWoodClub());
    inv.addItem(ConsumableFactory.createSmallHealthPotion());
    inv.addItem(ConsumableFactory.createWeaponRefillPotion());
    return new Player('Knight', 200, mods(1, 0.2, [1, 0.8, 1, 1]), inv, creatureIcon('knight2', false));
  },

  createRangePlayer(): Player {
    const inv = new Inventory(4, 4, 3);
    inv.addItem(WeaponFactory.createLongBow());
    inv.addItem(WeaponFactory.createRudimentaryStaff());
    inv.addItem(ConsumableFactory.createSmallHealthPotion());
    inv.addItem(ConsumableFactory.createWeaponRefillPotion());
    // +15% dodge (evasion 0.35); projectile accuracy bonus applied in Fight
    return new Player('Ranger', 200, mods(1, 0.35, [1, 1, 0.8, 1]), inv, creatureIcon('Ranger', false));
  },

  createRat(): Creature {
    const inv = new Inventory(4, 0);
    inv.addItem(WeaponFactory.createRatClaws());
    return new Creature('Rat', 10, mods(1, 0.15, [1, 1, 1, 1]), inv, creatureIcon('Rat', true), [1]);
  },

  createSlime(): Creature {
    const inv = new Inventory(4, 0);
    inv.addItem(WeaponFactory.createRatClaws());
    return new Creature('Slime', 25, mods(0.8, 0.25, [0.8, 1.2, 0.8, 1.0]), inv, creatureIcon('Slime', false), [1]);
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

  createGuardian(): Creature {
    const inv = new Inventory(4, 0);
    inv.addItem(WeaponFactory.createRoyalSword());
    inv.addItem(WeaponFactory.createSteelHammer());
    return new Creature('Guardian', 105, mods(1.5, 0.1, [0.5, 0.8, 1.1, 2.0]), inv, creatureIcon('PurpleGolemBoss', true), [2, 1]);
  },

  createBat(): Creature {
    const inv = new Inventory(4, 0);
    inv.addItem(WeaponFactory.createBirdTalons());
    inv.addItem(WeaponFactory.createWebCaster());
    return new Creature('Bat', 28, mods(1.1, 0.35, [1.1, 1, 0.7, 1.2]), inv, creatureIcon('Rat', true), [2, 1]);
  },

  createImp(): Creature {
    const inv = new Inventory(4, 0);
    inv.addItem(WeaponFactory.createEmberStaff());
    inv.addItem(WeaponFactory.createSparkRod());
    return new Creature('Imp', 45, mods(1.2, 0.2, [1.2, 1.1, 1, 0.7]), inv, creatureIcon('Sorcerer', true), [2, 1]);
  },

  createBandit(): Creature {
    const inv = new Inventory(4, 0);
    inv.addItem(WeaponFactory.createCleaver());
    inv.addItem(WeaponFactory.createJavelin());
    return new Creature('Bandit', 60, mods(1.15, 0.18, [1, 0.85, 1.1, 1.2]), inv, creatureIcon('Goblin', true), [2, 1.5]);
  },

  createWraith(): Creature {
    const inv = new Inventory(4, 0);
    inv.addItem(WeaponFactory.createFrostWand());
    inv.addItem(WeaponFactory.createDrainWand());
    return new Creature('Wraith', 70, mods(1.3, 0.25, [1.4, 1.3, 0.9, 0.55]), inv, creatureIcon('Skeleton', true), [2, 1]);
  },

  createBrute(): Creature {
    const inv = new Inventory(4, 0);
    inv.addItem(WeaponFactory.createStunMace());
    inv.addItem(WeaponFactory.createWoodClub());
    return new Creature('Brute', 90, mods(1.4, 0.05, [0.65, 1.2, 1, 1.5]), inv, creatureIcon('Barb2', false), [2, 1]);
  },

  createCultist(): Creature {
    const inv = new Inventory(4, 0);
    inv.addItem(WeaponFactory.createToxicStaff());
    inv.addItem(WeaponFactory.createHealStaff());
    return new Creature('Cultist', 80, mods(1.25, 0.12, [1.15, 1.2, 0.85, 0.65]), inv, creatureIcon('mage2', false), [2, 1]);
  },

  createSporekin(): Creature {
    const inv = new Inventory(4, 0);
    inv.addItem(WeaponFactory.createMushroomCap());
    inv.addItem(WeaponFactory.createVenomFang());
    return new Creature('Sporekin', 55, mods(1, 0.15, [1.1, 1, 1.1, 0.75]), inv, creatureIcon('Mushroom', false), [2, 1]);
  },

  createScout(): Creature {
    const inv = new Inventory(4, 0);
    inv.addItem(WeaponFactory.createBoneBow());
    inv.addItem(WeaponFactory.createRustyDagger());
    return new Creature('Scout', 48, mods(1.1, 0.3, [1, 1, 0.75, 1.15]), inv, creatureIcon('Ranger', false), [3, 1]);
  },

  createTroll(): Creature {
    const inv = new Inventory(4, 0);
    inv.addItem(WeaponFactory.createWoodClub());
    inv.addItem(WeaponFactory.createStunMace());
    return new Creature('Troll', 150, mods(2, 0.05, [0.7, 1.5, 0.8, 2.0]), inv, creatureIcon('Barb2', false), [2, 1]);
  },

  createBird(): Creature {
    const inv = new Inventory(4, 0);
    inv.addItem(WeaponFactory.createBirdTalons());
    inv.addItem(WeaponFactory.createWebCaster());
    return new Creature('Bird', 30, mods(3, 0.2, [0.5, 0.7, 2.0, 1.4]), inv, creatureIcon('Rat', true), [2, 1]);
  },

  /** Floor 1 boss — multi-kit situation, not a golem wall yet. */
  createPurpleGolem(): Creature {
    const inv = new Inventory(4, 0);
    inv.addItem(WeaponFactory.createStunMace());
    inv.addItem(WeaponFactory.createEmberStaff());
    inv.addItem(WeaponFactory.createCleaver());
    return new Creature(
      'Purple golem',
      140,
      mods(1.3, 0.08, [0.75, 0.85, 1.1, 0.9]),
      inv,
      creatureIcon('PurpleGolemBoss', true),
      [2, 2, 1.5],
    );
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
