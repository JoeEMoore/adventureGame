import { useGameStore } from '../store/gameStore';

export function WinScreen() {
  const startNewGame = useGameStore((s) => s.startNewGame);
  const exitToSplash = useGameStore((s) => s.exitToSplash);

  return (
    <div className="screen win-screen">
      <div className="win-panel">
        <h1>You Win!</h1>
        <p>The boss has fallen. The dungeon is cleared.</p>
        <div className="btn-row">
          <button type="button" className="btn primary" onClick={startNewGame}>
            Play Again
          </button>
          <button type="button" className="btn" onClick={exitToSplash}>
            Main Menu
          </button>
        </div>
      </div>
    </div>
  );
}
