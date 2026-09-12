import { useState } from 'react';
import { ShopRoom } from '../game/levels/rooms/shop/Shop';
import type { ShopEntry } from '../game/levels/rooms/shop/Shop';
import { getCurrentRoom, useGameStore } from '../store/gameStore';
import { IconView } from './IconView';

type ShopCategory = 'weapons' | 'consumables' | 'accessories';

export function ShopModal() {
  const tick = useGameStore((s) => s.tick);
  const player = useGameStore((s) => s.player);
  const closeModal = useGameStore((s) => s.closeModal);
  const buyEntry = useGameStore((s) => s.buyEntry);
  const [category, setCategory] = useState<ShopCategory>('weapons');
  const [selectedKey, setSelectedKey] = useState<string | null>(null);
  void tick;

  const room = getCurrentRoom();
  if (!player || !(room instanceof ShopRoom)) return null;

  const entries: { key: string; entry: ShopEntry }[] =
    category === 'weapons'
      ? room.getWeaponEntries().map((entry, i) => ({ key: `w-${i}`, entry }))
      : category === 'consumables'
        ? room.getConsumableEntries().map((entry, i) => ({ key: `c-${i}`, entry }))
        : room.getAccessoryEntries().map((entry, i) => ({ key: `a-${i}`, entry }));

  const selected = entries.find((e) => e.key === selectedKey) ?? entries[0] ?? null;
  const activeKey = selected?.key ?? null;
  const preview = selected?.entry.getPreview() ?? null;
  const canBuy =
    !!selected &&
    selected.entry.getQuantity() > 0 &&
    player.getGold() >= selected.entry.getPrice();

  return (
    <div className="modal-backdrop">
      <div className="modal tome-modal shop-modal" onClick={(e) => e.stopPropagation()}>
        <div className="tome-header">
          <h2>Shop</h2>
        </div>
        <p className="shop-gold">Your gold: {player.getGold()}</p>

        <div className="tome-tabs" role="tablist" aria-label="Shop categories">
          <button
            type="button"
            role="tab"
            aria-selected={category === 'weapons'}
            className={`tome-tab ${category === 'weapons' ? 'active' : ''}`}
            onClick={() => {
              setCategory('weapons');
              setSelectedKey(null);
            }}
          >
            Weapons
          </button>
          <button
            type="button"
            role="tab"
            aria-selected={category === 'consumables'}
            className={`tome-tab ${category === 'consumables' ? 'active' : ''}`}
            onClick={() => {
              setCategory('consumables');
              setSelectedKey(null);
            }}
          >
            Consumables
          </button>
          <button
            type="button"
            role="tab"
            aria-selected={category === 'accessories'}
            className={`tome-tab ${category === 'accessories' ? 'active' : ''}`}
            onClick={() => {
              setCategory('accessories');
              setSelectedKey(null);
            }}
          >
            Accessories
          </button>
        </div>

        {category === 'accessories' && (
          <p className="shop-section-note">
            Carry as many as you like; equip up to {player.getInventory().getMaxAccessories()} at a
            time. All accessories cost 60 gold.
          </p>
        )}

        <div className="tome-split">
          <div className="shop-list">
            {entries.length === 0 && <p className="detail-empty">Nothing for sale here.</p>}
            {entries.map(({ key, entry }) => {
              const item = entry.getPreview();
              return (
                <button
                  key={key}
                  type="button"
                  className={`shop-entry ${activeKey === key ? 'selected' : ''}`}
                  onClick={() => setSelectedKey(key)}
                >
                  <IconView icon={item.getIcon()} size={40} />
                  <div className="shop-info">
                    <strong>{item.getName()}</strong>
                    <span>
                      {entry.getPrice()} gold · qty {entry.getQuantity()}
                    </span>
                  </div>
                </button>
              );
            })}
          </div>

          <div className="detail-pane">
            {preview && selected ? (
              <>
                <div className="detail-preview">
                  <IconView icon={preview.getIcon()} size={72} />
                </div>
                <h3 className="detail-name">{preview.getName()}</h3>
                <span className="detail-banner">
                  {selected.entry.getPrice()} gold · qty {selected.entry.getQuantity()}
                </span>
                <p className="detail-text">{preview.getToolTipText()}</p>
              </>
            ) : (
              <p className="detail-empty">Select an item to inspect it.</p>
            )}
          </div>
        </div>

        <div className="btn-row">
          <button
            type="button"
            className="btn primary"
            disabled={!canBuy || !selected}
            onClick={() => selected && buyEntry(selected.entry)}
          >
            Buy
          </button>
          <button type="button" className="btn" onClick={closeModal}>
            Leave Shop
          </button>
        </div>
      </div>
    </div>
  );
}
