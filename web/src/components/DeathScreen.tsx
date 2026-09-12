import { useGameStore } from '../store/gameStore';

export function DeathScreen() {
  const deathCause = useGameStore((s) => s.deathCause);
  const startNewGame = useGameStore((s) => s.startNewGame);
  const exitToSplash = useGameStore((s) => s.exitToSplash);

  return (
    <div className="screen death-screen">
      <div className="death-panel tome-panel">
        <div className="tome-header">
          <h1>You Died</h1>
        </div>
        <p className="death-cause">{deathCause ?? 'You were defeated in the dungeon.'}</p>
        <div className="btn-row">
          <button type="button" className="btn primary" onClick={startNewGame}>
            Try Again
          </button>
          <button type="button" className="btn" onClick={exitToSplash}>
            Main Menu
          </button>
        </div>
      </div>
    </div>
  );
}
