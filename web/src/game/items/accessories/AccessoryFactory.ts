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
  doubleShot: {
    name: 'Double Shot',
    description:
      'Projectile weapons deal double damage and cost 2 uses per shot, but have −10% accuracy.',
    icon: weaponSprite(12, 4),
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
  echoCharm: {
    name: 'Echo Charm',
    description: 'After you miss, your next attack has +25% accuracy.',
    icon: weaponSprite(19, 1),
  },
  focusCrystal: {
    name: 'Focus Crystal',
    description: 'Your first landed hit each fight deals +40% damage.',
    icon: weaponSprite(20, 3),
  },
  thornCollar: {
    name: 'Thorn Collar',
    description: 'When you take a hit, reflect 20% of that damage as Pure.',
    icon: weaponSprite(16, 2),
  },
  vampiricFang: {
    name: 'Vampiric Fang',
    description: 'Heal for 10% of damage dealt on hit; max HP −10% while equipped.',
    icon: weaponSprite(15, 4),
  },
  ritualCodex: {
    name: 'Ritual Codex',
    description:
      'If the enemy already has Burn, Shock, or Iced, Magic hits have a 15% chance to extend that status by 1 turn.',
    icon: weaponSprite(21, 0),
  },
  oathMedallion: {
    name: 'Oath Medallion',
    description: 'Knight Bleed streak procs one hit earlier.',
    icon: weaponSprite(22, 1),
  },
  quickstepBoots: {
    name: 'Quickstep Boots',
    description: 'After an enemy misses you, your next attack deals +25% damage.',
    icon: weaponSprite(14, 5),
  },
  emptyQuiverCord: {
    name: 'Empty Quiver Cord',
    description:
      'Once per fight, when a limited weapon hits 0 uses, restore 1 use to a different spent weapon.',
    icon: weaponSprite(12, 2),
  },
  glassDice: {
    name: 'Glass Dice',
    description: '+20% damage dealt, −10% accuracy on all attacks.',
    icon: weaponSprite(23, 3),
  },
  secondWindBandana: {
    name: 'Second Wind Bandana',
    description:
      'Once per fight, when you first drop below 25% HP, gain Damage Resistance for 2 turns.',
    icon: weaponSprite(13, 4),
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
  'doubleShot',
  'venomFlask',
  'pirateCoin',
  'ringOfFire',
  'ringOfIce',
  'ringOfElectricity',
  'ironBand',
  'scrapPouch',
  'lockpick',
  'echoCharm',
  'focusCrystal',
  'thornCollar',
  'vampiricFang',
  'ritualCodex',
  'oathMedallion',
  'quickstepBoots',
  'emptyQuiverCord',
  'glassDice',
  'secondWindBandana',
];

export const AccessoryFactory = {
  createShield: () => create('shield'),
  createWraps: () => create('wraps'),
  createGrips: () => create('grips'),
  createDoubleShot: () => create('doubleShot'),
  createVenomFlask: () => create('venomFlask'),
  createPirateCoin: () => create('pirateCoin'),
  createRingOfFire: () => create('ringOfFire'),
  createRingOfIce: () => create('ringOfIce'),
  createRingOfElectricity: () => create('ringOfElectricity'),
  createIronBand: () => create('ironBand'),
  createScrapPouch: () => create('scrapPouch'),
  createLockpick: () => create('lockpick'),
  createEchoCharm: () => create('echoCharm'),
  createFocusCrystal: () => create('focusCrystal'),
  createThornCollar: () => create('thornCollar'),
  createVampiricFang: () => create('vampiricFang'),
  createRitualCodex: () => create('ritualCodex'),
  createOathMedallion: () => create('oathMedallion'),
  createQuickstepBoots: () => create('quickstepBoots'),
  createEmptyQuiverCord: () => create('emptyQuiverCord'),
  createGlassDice: () => create('glassDice'),
  createSecondWindBandana: () => create('secondWindBandana'),
  create(kind: AccessoryKind): Accessory {
    return create(kind);
  },
  createRandom(): Accessory {
    const kind = ACCESSORY_KINDS[Math.floor(Math.random() * ACCESSORY_KINDS.length)];
    return create(kind);
  },
};
