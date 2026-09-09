import { useGameStore, type PlayerClass } from '../store/gameStore';

const CLASSES: { id: PlayerClass; name: string; icon: string; desc: string }[] = [
  {
    id: 'knight',
    name: 'Knight',
    icon: '/assets/images/creatures/knight2.png',
    desc: 'Slice resistance. Steel Sword & Wood Club.',
  },
  {
    id: 'mage',
    name: 'Mage',
    icon: '/assets/images/creatures/mage2.png',
    desc: 'Magic resistance. Enchanted Staff & Rusty Dagger.',
  },
  {
    id: 'ranger',
    name: 'Ranger',
    icon: '/assets/images/creatures/Ranger.png',
    desc: 'Projectile resistance. Long Bow & Rudimentary Staff.',
  },
  {
    id: 'barbarian',
    name: 'Barbarian',
    icon: '/assets/images/creatures/Barb2.png',
    desc: 'Blunt resistance. Steel Mace & Crude Bow.',
  },
];

export function PlayerSelectScreen() {
  const selectClass = useGameStore((s) => s.selectClass);

  return (
    <div className="screen select-screen">
      <div className="select-panel">
        <h1>Choose Your Class</h1>
        <div className="class-grid">
          {CLASSES.map((c) => (
            <button key={c.id} type="button" className="class-card" onClick={() => selectClass(c.id)}>
              <img src={c.icon} alt={c.name} />
              <strong>{c.name}</strong>
              <span>{c.desc}</span>
            </button>
          ))}
        </div>
      </div>
    </div>
  );
}
