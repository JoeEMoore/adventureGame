import { useGameStore } from '../store/gameStore';

export function SplashScreen() {
  const startNewGame = useGameStore((s) => s.startNewGame);

  return (
    <div className="screen splash-screen">
      <div className="splash-overlay tome-panel">
        <p className="splash-eyebrow">Welcome to</p>
        <h1 className="brand">KLEPTOMAZEIAC</h1>
        <p className="tagline">Steal loot. Forge power. Descend deeper.</p>
        <div className="btn-row">
          <button type="button" className="btn primary" onClick={startNewGame}>
            New Game
          </button>
        </div>
      </div>
    </div>
  );
}
