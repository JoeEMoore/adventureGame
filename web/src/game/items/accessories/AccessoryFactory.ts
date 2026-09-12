import { Accessory, type AccessoryKind } from './Accessory';
import { potionSprite, weaponSprite, type IconRef } from '../../utils/icons';

const DEFS: Record<AccessoryKind, { name: string; description: string; icon: IconRef }> = {
  shield: {
    name: 'Shield',
    description: '10% chance to block an attack completely.',
    icon: weaponSprite(11, 3),
  },
  wraps: {
    name: 'Wraps',
    description: '+10% Blunt damage.',
    icon: weaponSprite(13, 1),
  },
  grips: {
    name: 'Grips',
    description: '+10% accuracy with all weapons; +15% accuracy with Blunt weapons.',
    icon: weaponSprite(13, 2),
  },
  venomFlask: {
    name: 'Venom Flask',
    description: '20% chance on hit to Poison the enemy for 3 turns.',
    icon: potionSprite(16, 2),
  },
  pirateCoin: {
    name: 'Pirate Coin',
    description: '+20% gold from enemy kills.',
    icon: weaponSprite(24, 0),
  },
  ringOfFire: {
    name: 'Ring of Fire',
    description: '10% chance on hit to Burn the enemy for 3 turns.',
    icon: weaponSprite(17, 3),
  },
  ringOfIce: {
    name: 'Ring of Ice',
    description: '10% chance on hit to Ice the enemy for 3 turns.',
    icon: weaponSprite(18, 5),
  },
  ringOfElectricity: {
    name: 'Ring of Electricity',
    description: '10% chance on hit to Shock the enemy for 3 turns.',
    icon: weaponSprite(18, 4),
  },
  ironBand: {
    name: 'Iron Band',
    description: 'Incoming Blunt damage reduced by 20% per band.',
    icon: weaponSprite(17, 0),
  },
  scrapPouch: {
    name: 'Scrap Pouch',
    description: 'At fight start, restore 1 use to a spent limited weapon.',
    icon: weaponSprite(24, 2),
  },
  lockpick: {
    name: 'Lockpick',
    description: 'Gold door costs are reduced by 25% per lockpick (min 1).',
    icon: weaponSprite(14, 2),
  },
};

function create(kind: AccessoryKind): Accessory {
  const d = DEFS[kind];
  return new Accessory(d.name, kind, d.description, d.icon);
}

export const ACCESSORY_KINDS: AccessoryKind[] = [
  'shield',
  'wraps',
  'grips',
  'venomFlask',
  'pirateCoin',
  'ringOfFire',
  'ringOfIce',
  'ringOfElectricity',
  'ironBand',
  'scrapPouch',
  'lockpick',
];

export const AccessoryFactory = {
  createShield: () => create('shield'),
  createWraps: () => create('wraps'),
  createGrips: () => create('grips'),
  createVenomFlask: () => create('venomFlask'),
  createPirateCoin: () => create('pirateCoin'),
  createRingOfFire: () => create('ringOfFire'),
  createRingOfIce: () => create('ringOfIce'),
  createRingOfElectricity: () => create('ringOfElectricity'),
  createIronBand: () => create('ironBand'),
  createScrapPouch: () => create('scrapPouch'),
  createLockpick: () => create('lockpick'),
  create(kind: AccessoryKind): Accessory {
    return create(kind);
  },
  createRandom(): Accessory {
    const kind = ACCESSORY_KINDS[Math.floor(Math.random() * ACCESSORY_KINDS.length)];
    return create(kind);
  },
};
