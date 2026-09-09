import { useGameStore } from '../store/gameStore';

export function SplashScreen() {
  const startNewGame = useGameStore((s) => s.startNewGame);

  return (
    <div className="screen splash-screen">
      <div className="splash-overlay">
        <h1 className="brand">Adventure Game</h1>
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
