import { ShopRoom } from '../game/levels/rooms/shop/Shop';
import type { ShopEntry } from '../game/levels/rooms/shop/Shop';
import { getCurrentRoom, useGameStore } from '../store/gameStore';
import { IconView } from './IconView';

export function ShopModal() {
  const tick = useGameStore((s) => s.tick);
  const player = useGameStore((s) => s.player);
  const closeModal = useGameStore((s) => s.closeModal);
  const buyEntry = useGameStore((s) => s.buyEntry);
  void tick;

  const room = getCurrentRoom();
  if (!player || !(room instanceof ShopRoom)) return null;

  const renderEntry = (entry: ShopEntry, key: string) => {
    const preview = entry.getPreview();
    const disabled = entry.getQuantity() <= 0 || player.getGold() < entry.getPrice();

    return (
      <div key={key} className="shop-entry">
        <IconView icon={preview.getIcon()} size={48} />
        <div className="shop-info">
          <strong title={preview.getToolTipText()}>{preview.getName()}</strong>
          <span>
            {entry.getPrice()} gold · qty {entry.getQuantity()}
          </span>
        </div>
        <button
          type="button"
          className="btn primary"
          disabled={disabled}
          onClick={() => buyEntry(entry)}
        >
          Buy
        </button>
      </div>
    );
  };

  return (
    <div className="modal-backdrop">
      <div className="modal shop-modal" onClick={(e) => e.stopPropagation()}>
        <h2>Shop</h2>
        <p>Your gold: {player.getGold()}</p>
        <h3>Weapons</h3>
        <div className="shop-list">
          {room.getWeaponEntries().map((e, i) => renderEntry(e, `w-${i}`))}
        </div>
        <h3>Consumables</h3>
        <div className="shop-list">
          {room.getConsumableEntries().map((e, i) => renderEntry(e, `c-${i}`))}
        </div>
        <div className="btn-row">
          <button type="button" className="btn" onClick={closeModal}>
            Leave Shop
          </button>
        </div>
      </div>
    </div>
  );
}
