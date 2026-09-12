import { useState } from 'react';
import { useGameStore } from '../store/gameStore';
import { getFloorConfig, TOTAL_FLOORS } from '../game/levels/floorConfig';
import {
  DESCEND_BUFF_OPTIONS,
  type DescendBuffId,
} from '../game/creatures/descendBuffs';

export function DescendModal() {
  const floorIndex = useGameStore((s) => s.floorIndex);
  const descendFloor = useGameStore((s) => s.descendFloor);
  const closeModal = useGameStore((s) => s.closeModal);
  const next = getFloorConfig(floorIndex + 1);
  const [selected, setSelected] = useState<DescendBuffId | null>(null);

  return (
    <div className="modal-backdrop">
      <div className="modal tome-modal" onClick={(e) => e.stopPropagation()}>
        <div className="tome-header">
          <h2>Floor Cleared</h2>
        </div>
        <p>
          The stairway down to <strong>{next.displayName}</strong> (Floor {floorIndex + 2}/
          {TOTAL_FLOORS}) waits. Descending restores some health and unlocks another accessory equip
          slot — but first, claim a permanent scar of power.
        </p>
        <p className="descend-pick-label">Choose one permanent buff:</p>
        <ul className="item-list descend-buff-list">
          {DESCEND_BUFF_OPTIONS.map((opt) => (
            <li key={opt.id}>
              <button
                type="button"
                className={selected === opt.id ? 'selected' : undefined}
                onClick={() => setSelected(opt.id)}
              >
                <span className="item-list-label">
                  <strong>{opt.label}</strong>
                  <span className="descend-buff-desc">{opt.description}</span>
                </span>
              </button>
            </li>
          ))}
        </ul>
        <div className="btn-row">
          <button
            type="button"
            className="btn primary"
            disabled={!selected}
            onClick={() => {
              if (selected) descendFloor(selected);
            }}
          >
            Descend
          </button>
          <button type="button" className="btn" onClick={closeModal}>
            Stay a moment
          </button>
        </div>
      </div>
    </div>
  );
}
