import { useGameStore } from '../store/gameStore';

export function SplashScreen() {
  const startNewGame = useGameStore((s) => s.startNewGame);

  return (
    <div className="screen splash-screen">
      <div className="splash-overlay tome-panel">
        <div className="tome-header">
          <h1>Adventure Game</h1>
        </div>
        <p className="tagline">A turn-based dungeon crawler</p>
        <div className="btn-row">
          <button type="button" className="btn primary" onClick={startNewGame}>
            New Game
          </button>
        </div>
      </div>
    </div>
  );
}
