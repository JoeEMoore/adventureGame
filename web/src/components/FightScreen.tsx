import {
  useCallback,
  useEffect,
  useState,
  type Dispatch,
  type ReactNode,
  type SetStateAction,
} from 'react';
import type { Effect } from '../game/effects/Effect';
import type { IconRef } from '../game/utils/icons';
import { POT_EFFECTS_SHEET } from '../game/utils/icons';
import type { Creature } from '../game/creatures/Creature';
import type { Player } from '../game/creatures/Player';
import { DamageType, getDamageTypes } from '../game/damagetypes/DamageType';
import { countEquipped } from '../game/items/accessories/accessoryCombat';
import type { CombatFx } from '../fx/combatFx';
import {
  loadFightLayout,
  resetFightLayout,
  saveFightLayout,
  type FightBoxId,
  type FightLayout,
} from '../game/fightLayout';
import { useGameStore } from '../store/gameStore';
import { IconView, SpriteIcon } from './IconView';
import { InventoryModal } from './InventoryModal';
import { PositionedBox } from './PositionedBox';

const TURN_DELAY_MS = 1500;
const FX_MS = 700;

/** PotEffects.png 1-based strip indices */
const STATUS_AURA: { name: string; index: number; className: string }[] = [
  { name: 'Iced', index: 10, className: 'aura-iced' },
  { name: 'Burn', index: 14, className: 'aura-burn' },
  { name: 'Shock', index: 8, className: 'aura-shock' },
  { name: 'Poison', index: 6, className: 'aura-poison' },
  { name: 'Bleed', index: 12, className: 'aura-bleed' },
];

const STATUS_CHIP_CLASS: Record<string, string> = {
  Poison: 'status-poison',
  Bleed: 'status-bleed',
  Burn: 'status-burn',
  Shock: 'status-shock',
  Iced: 'status-iced',
  Knockout: 'status-knockout',
};

type Floater = {
  id: number;
  text: string;
  side: 'player' | 'enemy';
  kind: 'damage' | 'miss' | 'heal' | 'proc' | 'block' | 'resist' | 'weak';
};

type CommandMode = 'commands' | 'fightMoves';

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
  const [floaters, setFloaters] = useState<Floater[]>([]);
  const [flashWeapon, setFlashWeapon] = useState<{ side: 'player' | 'enemy'; index: number } | null>(
    null,
  );
  const [commandMode, setCommandMode] = useState<CommandMode>('commands');
  /** Permanent layout editor — toggle anytime; do not remove. */
  const [layoutEdit, setLayoutEdit] = useState(false);
  const [layout, setLayout] = useState<FightLayout>(() => loadFightLayout());
  const [copied, setCopied] = useState(false);

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

  const moveBox = useCallback((id: FightBoxId, pos: FightLayout[FightBoxId]) => {
    setLayout((prev) => {
      const next = { ...prev, [id]: pos };
      saveFightLayout(next);
      return next;
    });
  }, []);

  const copyLayout = useCallback(async () => {
    const text = JSON.stringify(layout, null, 2);
    try {
      await navigator.clipboard.writeText(text);
      setCopied(true);
      window.setTimeout(() => setCopied(false), 1500);
    } catch {
      window.prompt('Copy fight layout JSON:', text);
    }
  }, [layout]);

  if (!player || !fightState) return null;
  const { enemy, isPlayersTurn, log } = fightState;
  const weapons = player.getInventory().getWeapons();

  return (
    <div className={`screen fight-screen ${shake ? 'shake' : ''} ${layoutEdit ? 'layout-edit' : ''}`}>
      {layoutEdit ? (
        <div className="fight-layout-toolbar">
          <strong>Layout edit</strong>
          <button type="button" className="btn" onClick={() => setLayoutEdit(false)}>
            Done
          </button>
          <button
            type="button"
            className="btn"
            onClick={() => {
              setLayout(resetFightLayout());
            }}
          >
            Reset
          </button>
          <button type="button" className="btn primary" onClick={copyLayout}>
            {copied ? 'Copied!' : 'Copy layout JSON'}
          </button>
          <span className="fight-layout-hint">
            Drag boxes to reposition. Paste JSON here when you want defaults updated in code.
          </span>
        </div>
      ) : (
        <button
          type="button"
          className="btn fight-layout-toggle"
          onClick={() => setLayoutEdit(true)}
          title="Reposition fight UI"
        >
          Layout
        </button>
      )}

      <div className="fight-canvas">
        <div className="fight-dock-bar" aria-hidden />

        <PositionedBox
          id="playerStage"
          pos={layout.playerStage}
          editable={layoutEdit}
          onMove={moveBox}
          className={`fighter-stage-box ${combatFx?.shake && combatFx.targetSide === 'player' ? 'hit-flash' : ''}`}
        >
          <FighterStage
            side="player"
            icon={player.getIcon()}
            effects={player.getEffects()}
            floaters={floaters.filter((f) => f.side === 'player')}
          />
        </PositionedBox>

        <PositionedBox
          id="playerPlate"
          pos={layout.playerPlate}
          editable={layoutEdit}
          onMove={moveBox}
        >
          <HpPlate
            name={player.getName()}
            health={player.getHealth()}
            maxHealth={player.getMaxHealth()}
          />
        </PositionedBox>

        <PositionedBox
          id="playerExtras"
          pos={layout.playerExtras}
          editable={layoutEdit}
          onMove={moveBox}
        >
          <CreatureExtras
            resists={notableResists(player)}
            effects={player.getEffects()}
            predictionSlot={<PlayerPredictionHud player={player} />}
          />
        </PositionedBox>

        <PositionedBox
          id="enemyStage"
          pos={layout.enemyStage}
          editable={layoutEdit}
          onMove={moveBox}
          className={`fighter-stage-box ${combatFx?.shake && combatFx.targetSide === 'enemy' ? 'hit-flash' : ''}`}
        >
          <FighterStage
            side="enemy"
            icon={enemy.getIcon()}
            effects={enemy.getEffects()}
            floaters={floaters.filter((f) => f.side === 'enemy')}
          />
        </PositionedBox>

        <PositionedBox
          id="enemyPlate"
          pos={layout.enemyPlate}
          editable={layoutEdit}
          onMove={moveBox}
        >
          <HpPlate
            name={enemy.getName()}
            health={enemy.getHealth()}
            maxHealth={enemy.getMaxHealth()}
          />
        </PositionedBox>

        <PositionedBox
          id="enemyExtras"
          pos={layout.enemyExtras}
          editable={layoutEdit}
          onMove={moveBox}
        >
          <CreatureExtras resists={notableResists(enemy)} effects={enemy.getEffects()} />
        </PositionedBox>

        <PositionedBox
          id="fightMessage"
          pos={layout.fightMessage}
          editable={layoutEdit}
          onMove={moveBox}
          className="fight-message-box"
        >
          <div className="fight-message" role="status">
            {log}
            {!isPlayersTurn && <span className="turn-hint">Enemy turn...</span>}
            {isPlayersTurn && commandMode === 'commands' && (
              <span className="turn-hint turn-hint-player">What will you do?</span>
            )}
            {isPlayersTurn && commandMode === 'fightMoves' && (
              <span className="turn-hint turn-hint-player">Choose a weapon.</span>
            )}
          </div>
        </PositionedBox>

        <PositionedBox
          id="fightCommands"
          pos={layout.fightCommands}
          editable={layoutEdit}
          onMove={moveBox}
          className="fight-commands-box"
        >
          <div className="fight-commands">
            {commandMode === 'commands' ? (
              <div className="cmd-grid">
                <button
                  type="button"
                  className="cmd-btn fight"
                  disabled={!isPlayersTurn || layoutEdit}
                  onClick={() => setCommandMode('fightMoves')}
                >
                  Fight
                </button>
                <button
                  type="button"
                  className="cmd-btn bag"
                  disabled={!isPlayersTurn || layoutEdit}
                  onClick={openInventory}
                >
                  Bag
                </button>
              </div>
            ) : (
              <div className="fight-moves-row">
                <div className="weapon-list">
                  {weapons.map((w, i) => {
                    const uses = w.getMove().getUses();
                    const disabled = !isPlayersTurn || uses === 0 || layoutEdit;
                    return (
                      <button
                        key={i}
                        type="button"
                        className={`btn weapon-btn ${
                          flashWeapon?.side === 'player' && flashWeapon.index === i
                            ? 'weapon-flash'
                            : ''
                        }`}
                        disabled={disabled}
                        title={w.getToolTipText()}
                        onClick={() => performPlayerMove(i)}
                      >
                        <IconView icon={w.getIcon()} size={24} />
                        <span>{w.toString()}</span>
                      </button>
                    );
                  })}
                </div>
                <button
                  type="button"
                  className="cmd-btn back"
                  disabled={layoutEdit}
                  onClick={() => setCommandMode('commands')}
                >
                  Back
                </button>
              </div>
            )}
          </div>
        </PositionedBox>
      </div>

      {modal === 'inventory' && <InventoryModal initialCategory="consumables" />}
    </div>
  );
}

function HpPlate({
  name,
  health,
  maxHealth,
}: {
  name: string;
  health: number;
  maxHealth: number;
}) {
  const pct = Math.max(0, (health / maxHealth) * 100);
  return (
    <div className="hp-plate">
      <h3 className="hp-plate-name">{name}</h3>
      <div className="hp-bar">
        <div className="hp-fill" style={{ width: `${pct}%` }} />
      </div>
      <div className="hp-text">
        {Math.round(health)} / {maxHealth}
      </div>
    </div>
  );
}

function CreatureExtras({
  resists,
  effects,
  predictionSlot,
}: {
  resists: { type: string; kind: 'resist' | 'weak' }[];
  effects: Effect[];
  predictionSlot?: ReactNode;
}) {
  if (resists.length === 0 && effects.length === 0 && !predictionSlot) {
    return null;
  }

  return (
    <div className="creature-extras">
      {resists.length > 0 && (
        <div className="resist-list" aria-label="Damage type affinities">
          {resists.map((r) => (
            <span key={`${r.kind}-${r.type}`} className={`resist-chip resist-${r.kind}`}>
              {r.kind === 'resist' ? 'Resists' : 'Weak'} {r.type}
            </span>
          ))}
        </div>
      )}
      {predictionSlot}
      {effects.length > 0 && (
        <div className="status-list" aria-label="Active statuses">
          {effects.map((e, i) => (
            <span
              key={`${e.getName()}-${i}`}
              className={`status-chip ${STATUS_CHIP_CLASS[e.getName()] ?? ''}`}
            >
              {e.toString()}
            </span>
          ))}
        </div>
      )}
    </div>
  );
}

function FighterStage({
  side,
  icon,
  effects,
  floaters,
}: {
  side: 'player' | 'enemy';
  icon: IconRef;
  effects: Effect[];
  floaters: Floater[];
}) {
  return (
    <div className={`creature-stage side-${side}`}>
      <div className="creature-portrait">
        <IconView icon={icon} size={side === 'player' ? 150 : 160} />
        <StatusAura effects={effects} />
        {floaters.map((f) => (
          <span key={f.id} className={`floater floater-${f.kind}`}>
            {f.text}
          </span>
        ))}
      </div>
    </div>
  );
}

function notableResists(creature: Creature): { type: string; kind: 'resist' | 'weak' }[] {
  const mods = creature.getTurnModifiers();
  const out: { type: string; kind: 'resist' | 'weak' }[] = [];
  for (const dt of getDamageTypes()) {
    if (dt === DamageType.Pure) continue;
    const r = mods.getResistance(dt);
    if (r < 0.95) out.push({ type: dt, kind: 'resist' });
    else if (r > 1.05) out.push({ type: dt, kind: 'weak' });
  }
  return out;
}

function PlayerPredictionHud({ player }: { player: Player }) {
  const cls = player.getPlayerClass();
  const streak = player.getConsecutiveSlashHits();
  const bleedProcced = player.hasSlashBleedProcced();

  let classHint: string | null = null;
  if (cls === 'barbarian') classHint = 'Blunt hits: 20% Knockout';
  else if (cls === 'mage') classHint = 'Magic hits: 10% Burn, 10% Shock, 10% Iced each';
  else if (cls === 'ranger') classHint = 'Projectile +15% accuracy';
  else if (cls === 'knight') {
    if (streak === 0) classHint = 'Land Slice hits to build Bleed streak';
    else if (streak === 1) classHint = 'Next Slice: 50% Bleed';
    else if (streak === 2 && !bleedProcced) classHint = 'Next Slice: guaranteed Bleed';
    else if (streak === 2 && bleedProcced) classHint = 'Bleed already hit — streak resets next Slice';
    else classHint = 'Slash streak active';
  }

  const accessoryHints: string[] = [];
  const shields = countEquipped(player, 'shield');
  if (shields > 0) accessoryHints.push(`Shield ×${shields} (10% block each)`);
  const wraps = countEquipped(player, 'wraps');
  if (wraps > 0) accessoryHints.push(`Wraps ×${wraps} (+${wraps * 10}% Blunt)`);
  const grips = countEquipped(player, 'grips');
  if (grips > 0) {
    accessoryHints.push(
      `Grips ×${grips} (+${grips * 10}% acc / +${grips * 15}% Blunt acc)`,
    );
  }
  const venom = countEquipped(player, 'venomFlask');
  if (venom > 0) accessoryHints.push(`Venom ×${venom} (20% Poison)`);
  const fire = countEquipped(player, 'ringOfFire');
  if (fire > 0) accessoryHints.push(`Fire ring ×${fire} (10% Burn)`);
  const ice = countEquipped(player, 'ringOfIce');
  if (ice > 0) accessoryHints.push(`Ice ring ×${ice} (10% Iced)`);
  const elec = countEquipped(player, 'ringOfElectricity');
  if (elec > 0) accessoryHints.push(`Storm ring ×${elec} (10% Shock)`);
  const iron = countEquipped(player, 'ironBand');
  if (iron > 0) accessoryHints.push(`Iron Band ×${iron} (−20% Blunt taken)`);
  const scrap = countEquipped(player, 'scrapPouch');
  if (scrap > 0) accessoryHints.push(`Scrap Pouch (restore ammo)`);
  const picks = countEquipped(player, 'lockpick');
  if (picks > 0) accessoryHints.push(`Lockpick ×${picks} (−25% gold locks)`);

  return (
    <div className="prediction-hud" aria-label="Combat predictions">
      {cls === 'knight' && (
        <div className="streak-meter" title="Knight Slash streak toward Bleed">
          <span className="streak-label">Slash streak</span>
          <div className="streak-pips" aria-hidden>
            {[1, 2, 3].map((n) => (
              <span
                key={n}
                className={`streak-pip ${streak >= n ? 'filled' : ''} ${
                  n === 2 && streak >= 2 && !bleedProcced ? 'bleed-ready' : ''
                }`}
              />
            ))}
          </div>
          <span className="streak-count">
            {Math.min(streak, 3)}/3
            {bleedProcced ? ' · bled' : ''}
          </span>
        </div>
      )}
      {classHint && <p className="prediction-hint">{classHint}</p>}
      {accessoryHints.length > 0 && (
        <ul className="accessory-hints">
          {accessoryHints.map((h) => (
            <li key={h}>{h}</li>
          ))}
        </ul>
      )}
    </div>
  );
}

function StatusAura({ effects }: { effects: Effect[] }) {
  const names = new Set(effects.map((e) => e.getName()));
  const active = STATUS_AURA.filter((s) => names.has(s.name));
  if (active.length === 0) return null;

  return (
    <div className="status-aura" aria-hidden>
      {active.map((status) =>
        [0, 1, 2].map((i) => (
          <span
            key={`${status.name}-${i}`}
            className={`status-particle ${status.className}`}
            style={{ animationDelay: `${i * -0.7}s`, ['--orbit-i' as string]: String(i) }}
          >
            <SpriteIcon sheet={POT_EFFECTS_SHEET} row={0} col={status.index - 1} tile={16} size={22} />
          </span>
        )),
      )}
    </div>
  );
}

function applyCombatFx(
  fx: CombatFx,
  setShake: (v: boolean) => void,
  setFloaters: Dispatch<SetStateAction<Floater[]>>,
  setFlashWeapon: (v: { side: 'player' | 'enemy'; index: number } | null) => void,
): void {
  setFlashWeapon({ side: fx.sourceSide, index: fx.weaponIndex });

  const next: Floater[] = [];
  const procSide = fx.targetSide;

  for (let i = 0; i < (fx.procs?.length ?? 0); i++) {
    const text = fx.procs![i];
    let kind: Floater['kind'] = 'proc';
    if (text === 'BLOCK') kind = 'block';
    else if (text === 'RESIST') kind = 'resist';
    else if (text === 'WEAK') kind = 'weak';
    next.push({
      id: fx.id * 10 + i + 1,
      text,
      side: procSide,
      kind,
    });
  }

  if (fx.missed && !(fx.procs?.includes('KO') || fx.procs?.includes('SHOCK'))) {
    next.push({ id: fx.id, text: 'MISS', side: fx.targetSide, kind: 'miss' });
  } else if (!fx.missed) {
    if (fx.shake) setShake(true);
    if (fx.damageDealt > 0) {
      next.push({
        id: fx.id,
        text: `-${Math.round(fx.damageDealt)}`,
        side: fx.targetSide,
        kind: 'damage',
      });
    } else if (fx.healed) {
      next.push({ id: fx.id, text: 'HEAL', side: fx.targetSide, kind: 'heal' });
    } else if (fx.procs?.includes('BLOCK')) {
      /* block already floated */
    }
  }

  if (next.length) {
    setFloaters((prev) => [...prev, ...next]);
    const ids = new Set(next.map((f) => f.id));
    window.setTimeout(() => {
      setFloaters((prev) => prev.filter((f) => !ids.has(f.id)));
    }, FX_MS);
  }
}
