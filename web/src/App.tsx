import { useGameStore } from './store/gameStore';
import { SplashScreen } from './components/SplashScreen';
import { PlayerSelectScreen } from './components/PlayerSelectScreen';
import { MapScreen } from './components/MapScreen';
import { FightScreen } from './components/FightScreen';
import { WinScreen } from './components/WinScreen';
import { DeathScreen } from './components/DeathScreen';
import './styles/game.css';

export default function App() {
  const screen = useGameStore((s) => s.screen);

  switch (screen) {
    case 'splash':
      return <SplashScreen />;
    case 'select':
      return <PlayerSelectScreen />;
    case 'map':
      return <MapScreen />;
    case 'fight':
      return <FightScreen />;
    case 'win':
      return <WinScreen />;
    case 'death':
      return <DeathScreen />;
  }
}
