import { useGameStore, type PlayerClass } from '../store/gameStore';
import { asset } from '../utils/asset';

const CLASSES: {
  id: PlayerClass;
  name: string;
  icon: string;
  kit: string;
  passive: string;
}[] = [
  {
    id: 'knight',
    name: 'Knight',
    icon: asset('assets/images/creatures/knight2.png'),
    kit: 'Slice resistance. Steel Sword & Wood Club.',
    passive:
      '2 consecutive Slash hits: 50% Bleed (5% max HP/turn for 3 turns). A 3rd consecutive Slash guarantees Bleed if it has not procced yet; if it already has, the streak resets.',
  },
  {
    id: 'mage',
    name: 'Mage',
    icon: asset('assets/images/creatures/mage2.png'),
    kit: 'Magic resistance. Enchanted Staff & Rusty Dagger.',
    passive:
      'On every Magic hit, each of Burn (10 dmg/turn for 3 turns), Shock (50% chance to fail attacks for 2 turns), and Iced (+25% miss chance for 3 turns) has an independent 10% chance to proc (~27% chance of at least one).',
  },
  {
    id: 'ranger',
    name: 'Ranger',
    icon: asset('assets/images/creatures/Ranger.png'),
    kit: 'Projectile resistance. Long Bow & Rudimentary Staff.',
    passive: '+15% dodge chance and +15% accuracy with Projectile (ranged) attacks.',
  },
  {
    id: 'barbarian',
    name: 'Barbarian',
    icon: asset('assets/images/creatures/Barb2.png'),
    kit: 'Blunt resistance. Steel Mace & Crude Bow.',
    passive: 'When using Blunt weapons, 20% chance to Knockout the enemy for 1 turn.',
  },
];

export function PlayerSelectScreen() {
  const selectClass = useGameStore((s) => s.selectClass);

  return (
    <div className="screen select-screen">
      <div className="select-panel tome-panel">
        <div className="tome-header">
          <h1>Choose Your Class</h1>
        </div>
        <p className="select-hint">Each class has a unique combat passive.</p>
        <div className="class-grid">
          {CLASSES.map((c) => (
            <button key={c.id} type="button" className="class-card" onClick={() => selectClass(c.id)}>
              <img src={c.icon} alt={c.name} />
              <strong>{c.name}</strong>
              <span className="class-kit">{c.kit}</span>
              <span className="class-passive">
                <em>Passive</em> {c.passive}
              </span>
            </button>
          ))}
        </div>
      </div>
    </div>
  );
}
