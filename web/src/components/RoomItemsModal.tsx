import { getCurrentRoom, useGameStore } from '../store/gameStore';
import { IconView } from './IconView';

export function RoomItemsModal() {
  const tick = useGameStore((s) => s.tick);
  const closeModal = useGameStore((s) => s.closeModal);
  const pickupItem = useGameStore((s) => s.pickupItem);
  void tick;

  const room = getCurrentRoom();
  const items = room?.getItems() ?? [];

  return (
    <div className="modal-backdrop" onClick={closeModal}>
      <div className="modal" onClick={(e) => e.stopPropagation()}>
        <h2>Room Items</h2>
        {items.length === 0 ? (
          <p>No items here.</p>
        ) : (
          <ul className="item-list">
            {items.map((item, i) => (
              <li key={i}>
                <button type="button" title={item.getToolTipText()} onClick={() => pickupItem(i)}>
                  <IconView icon={item.getIcon()} size={28} />
                  {item.getName()} — click to pick up
                </button>
              </li>
            ))}
          </ul>
        )}
        <div className="btn-row">
          <button type="button" className="btn" onClick={closeModal}>
            Close
          </button>
        </div>
      </div>
    </div>
  );
}
