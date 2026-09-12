import { useState } from 'react';
import { getCurrentRoom, useGameStore } from '../store/gameStore';
import { IconView } from './IconView';

export function RoomItemsModal() {
  const tick = useGameStore((s) => s.tick);
  const closeModal = useGameStore((s) => s.closeModal);
  const pickupItem = useGameStore((s) => s.pickupItem);
  const [selectedIdx, setSelectedIdx] = useState(0);
  void tick;

  const room = getCurrentRoom();
  const items = room?.getItems() ?? [];
  const safeIdx = items.length === 0 ? 0 : Math.min(selectedIdx, items.length - 1);
  const selected = items[safeIdx] ?? null;

  return (
    <div className="modal-backdrop" onClick={closeModal}>
      <div className="modal tome-modal" onClick={(e) => e.stopPropagation()}>
        <div className="tome-header">
          <h2>Room Items</h2>
        </div>

        {items.length === 0 ? (
          <p className="detail-empty">No items here.</p>
        ) : (
          <div className="tome-split">
            <ul className="item-list" role="listbox" aria-label="Room loot">
              {items.map((item, i) => (
                <li key={i}>
                  <button
                    type="button"
                    role="option"
                    aria-selected={safeIdx === i}
                    className={safeIdx === i ? 'selected' : ''}
                    onClick={() => setSelectedIdx(i)}
                  >
                    <IconView icon={item.getIcon()} size={28} />
                    {item.getName()}
                  </button>
                </li>
              ))}
            </ul>

            <div className="detail-pane">
              {selected ? (
                <>
                  <div className="detail-preview">
                    <IconView icon={selected.getIcon()} size={72} />
                  </div>
                  <h3 className="detail-name">{selected.getName()}</h3>
                  <span className="detail-banner">Loot</span>
                  <p className="detail-text">{selected.getToolTipText()}</p>
                </>
              ) : (
                <p className="detail-empty">Select an item to inspect it.</p>
              )}
            </div>
          </div>
        )}

        <div className="btn-row">
          <button
            type="button"
            className="btn primary"
            disabled={!selected}
            onClick={() => {
              if (!selected) return;
              const len = items.length;
              pickupItem(safeIdx);
              setSelectedIdx(Math.min(safeIdx, Math.max(0, len - 2)));
            }}
          >
            Pick Up
          </button>
          <button type="button" className="btn" onClick={closeModal}>
            Close
          </button>
        </div>
      </div>
    </div>
  );
}
