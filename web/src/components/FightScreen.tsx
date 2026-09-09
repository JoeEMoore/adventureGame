import { useEffect, useState, type Dispatch, type SetStateAction } from 'react';
import type { IconRef } from '../game/utils/icons';
import type { Weapon } from '../game/items/weapons/Weapon';
import type { CombatFx } from '../fx/combatFx';
import { useGameStore } from '../store/gameStore';
import { IconView } from './IconView';
import { InventoryModal } from './InventoryModal';

const TURN_DELAY_MS = 1500;
const FX_MS = 700;

export function FightScreen() {
  const tick = useGameStore((s) => s.tick);
  const player = useGameStore((s) => s.player);
  const fightState = useGameStore((s) => s.fightState);
  const combatFx = useGameStore((s) => s.combatFx);
  const modal = useGameStore((s) => s.modal);
  const performPlayerMove = useGameStore((s) => s.performPlayerMove);
  const finishEnemyTurn = useGameStore((s) => s.finishEnemyTurn);
  const openInventory = useGameStore((s) => s.openInventory);
  const clearCombatFx = useGameStore((s) => s.clearCombatFx);

  const [shake, setShake] = useState(false);
  const [floaters, setFloaters] = useState<
    { id: number; text: string; side: 'player' | 'enemy'; kind: 'damage' | 'miss' | 'heal' }[]
  >([]);
  const [flashWeapon, setFlashWeapon] = useState<{ side: 'player' | 'enemy'; index: number } | null>(
    null,
  );

  void tick;

  useEffect(() => {
    if (!fightState || fightState.isPlayersTurn) return;
    const t = window.setTimeout(() => finishEnemyTurn(), TURN_DELAY_MS);
    return () => window.clearTimeout(t);
  }, [fightState?.isPlayersTurn, fightState?.log, finishEnemyTurn, fightState]);

  useEffect(() => {
    if (!player || !fightState || !fightState.isPlayersTurn) return;
    if (player.getInventory().getWeapons().length === 0) {
      useGameStore.setState({
        fightState: { ...fightState, isPlayersTurn: false, log: 'No weapons! Enemy attacks...' },
      });
    }
  }, [player, fightState, tick]);

  useEffect(() => {
    if (!combatFx) return;
    applyCombatFx(combatFx, setShake, setFloaters, setFlashWeapon);
    const t = window.setTimeout(() => {
      setShake(false);
      setFlashWeapon(null);
      clearCombatFx();
    }, FX_MS);
    return () => window.clearTimeout(t);
  }, [combatFx, clearCombatFx]);

  if (!player || !fightState) return null;
  const { enemy, isPlayersTurn, log } = fightState;

  return (
    <div className={`screen fight-screen ${shake ? 'shake' : ''}`}>
      <button type="button" className="btn inventory-float" onClick={openInventory}>
        Inventory
      </button>

      <p className="fight-log">{log}</p>

      <div className="fight-arena">
        <CreatureFightCard
          side="player"
          name={player.getName()}
          health={player.getHealth()}
          maxHealth={player.getMaxHealth()}
          icon={player.getIcon()}
          weapons={player.getInventory().getWeapons()}
          interactive={isPlayersTurn}
          onWeapon={(i) => performPlayerMove(i)}
          floaters={floaters.filter((f) => f.side === 'player')}
          flashIndex={flashWeapon?.side === 'player' ? flashWeapon.index : null}
          hitFlash={combatFx?.shake && combatFx.targetSide === 'player'}
        />
        <CreatureFightCard
          side="enemy"
          name={enemy.getName()}
          health={enemy.getHealth()}
          maxHealth={enemy.getMaxHealth()}
          icon={enemy.getIcon()}
          weapons={enemy.getInventory().getWeapons()}
          interactive={false}
          floaters={floaters.filter((f) => f.side === 'enemy')}
          flashIndex={flashWeapon?.side === 'enemy' ? flashWeapon.index : null}
          hitFlash={combatFx?.shake && combatFx.targetSide === 'enemy'}
        />
      </div>

      {!isPlayersTurn && <p className="turn-hint">Enemy turn...</p>}
      {modal === 'inventory' && <InventoryModal />}
    </div>
  );
}

function applyCombatFx(
  fx: CombatFx,
  setShake: (v: boolean) => void,
  setFloaters: Dispatch<
    SetStateAction<
      { id: number; text: string; side: 'player' | 'enemy'; kind: 'damage' | 'miss' | 'heal' }[]
    >
  >,
  setFlashWeapon: (v: { side: 'player' | 'enemy'; index: number } | null) => void,
): void {
  setFlashWeapon({ side: fx.sourceSide, index: fx.weaponIndex });

  if (fx.missed) {
    setFloaters((prev) => [
      ...prev,
      { id: fx.id, text: 'MISS', side: fx.targetSide, kind: 'miss' },
    ]);
    return;
  }

  if (fx.shake) setShake(true);

  if (fx.damageDealt > 0) {
    setFloaters((prev) => [
      ...prev,
      {
        id: fx.id,
        text: `-${Math.round(fx.damageDealt)}`,
        side: fx.targetSide,
        kind: 'damage',
      },
    ]);
  } else if (fx.healed) {
    setFloaters((prev) => [
      ...prev,
      { id: fx.id, text: 'HEAL', side: fx.targetSide, kind: 'heal' },
    ]);
  }

  window.setTimeout(() => {
    setFloaters((prev) => prev.filter((f) => f.id !== fx.id));
  }, FX_MS);
}

function CreatureFightCard({
  name,
  health,
  maxHealth,
  icon,
  weapons,
  interactive,
  onWeapon,
  floaters,
  flashIndex,
  hitFlash,
}: {
  side: 'player' | 'enemy';
  name: string;
  health: number;
  maxHealth: number;
  icon: IconRef;
  weapons: Weapon[];
  interactive: boolean;
  onWeapon?: (i: number) => void;
  floaters: { id: number; text: string; kind: 'damage' | 'miss' | 'heal' }[];
  flashIndex: number | null;
  hitFlash?: boolean;
}) {
  const pct = Math.max(0, (health / maxHealth) * 100);
  return (
    <div className={`creature-card ${hitFlash ? 'hit-flash' : ''}`}>
      <div className="creature-portrait">
        <IconView icon={icon} size={160} />
        {floaters.map((f) => (
          <span key={f.id} className={`floater floater-${f.kind}`}>
            {f.text}
          </span>
        ))}
      </div>
      <h3>{name}</h3>
      <div className="hp-bar">
        <div className="hp-fill" style={{ width: `${pct}%` }} />
      </div>
      <div className="hp-text">
        {Math.round(health)} / {maxHealth}
      </div>
      <div className="weapon-list">
        {weapons.map((w, i) => {
          const uses = w.getMove().getUses();
          const disabled = !interactive || uses === 0;
          return (
            <button
              key={i}
              type="button"
              className={`btn weapon-btn ${flashIndex === i ? 'weapon-flash' : ''}`}
              disabled={disabled}
              title={w.getToolTipText()}
              onClick={() => onWeapon?.(i)}
            >
              <IconView icon={w.getIcon()} size={32} />
              <span>{w.toString()}</span>
            </button>
          );
        })}
      </div>
    </div>
  );
}
