import { useGameStore } from '../store/gameStore';
import { getFloorConfig, TOTAL_FLOORS } from '../game/levels/floorConfig';

export function DescendModal() {
  const floorIndex = useGameStore((s) => s.floorIndex);
  const descendFloor = useGameStore((s) => s.descendFloor);
  const closeModal = useGameStore((s) => s.closeModal);
  const next = getFloorConfig(floorIndex + 1);

  return (
    <div className="modal-backdrop">
      <div className="modal tome-modal" onClick={(e) => e.stopPropagation()}>
        <div className="tome-header">
          <h2>Floor Cleared</h2>
        </div>
        <p>
          The stairway down to <strong>{next.displayName}</strong> (Floor {floorIndex + 2}/
          {TOTAL_FLOORS}) waits. Descending restores some health and unlocks another accessory slot.
        </p>
        <div className="btn-row">
          <button type="button" className="btn primary" onClick={descendFloor}>
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
