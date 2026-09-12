import { useState } from 'react';
import { ShopRoom } from '../game/levels/rooms/shop/Shop';
import type { ShopEntry } from '../game/levels/rooms/shop/Shop';
import { weaponSellPrice } from '../game/levels/forge';
import { getCurrentRoom, useGameStore } from '../store/gameStore';
import { IconView } from './IconView';

type ShopCategory = 'weapons' | 'consumables' | 'accessories' | 'sell';

export function ShopModal() {
  const tick = useGameStore((s) => s.tick);
  const player = useGameStore((s) => s.player);
  const closeModal = useGameStore((s) => s.closeModal);
  const buyEntry = useGameStore((s) => s.buyEntry);
  const sellWeapon = useGameStore((s) => s.sellWeapon);
  const [category, setCategory] = useState<ShopCategory>('weapons');
  const [selectedKey, setSelectedKey] = useState<string | null>(null);
  const [sellIdx, setSellIdx] = useState(0);
  void tick;

  const room = getCurrentRoom();
  if (!player || !(room instanceof ShopRoom)) return null;

  const bagWeapons = player.getInventory().getWeapons();
  const selectedSell = bagWeapons[sellIdx] ?? null;
  const sellPrice = selectedSell ? weaponSellPrice(selectedSell) : 0;

  const entries: { key: string; entry: ShopEntry }[] =
    category === 'weapons'
      ? room.getWeaponEntries().map((entry, i) => ({ key: `w-${i}`, entry }))
      : category === 'consumables'
        ? room.getConsumableEntries().map((entry, i) => ({ key: `c-${i}`, entry }))
        : category === 'accessories'
          ? room.getAccessoryEntries().map((entry, i) => ({ key: `a-${i}`, entry }))
          : [];

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
          <button
            type="button"
            role="tab"
            aria-selected={category === 'sell'}
            className={`tome-tab ${category === 'sell' ? 'active' : ''}`}
            onClick={() => {
              setCategory('sell');
              setSellIdx(0);
            }}
          >
            Sell
          </button>
        </div>

        {category === 'accessories' && (
          <p className="shop-section-note">
            Carry as many as you like; equip up to {player.getInventory().getMaxAccessories()} at a
            time. All accessories cost 60 gold.
          </p>
        )}
        {category === 'sell' && (
          <p className="shop-section-note">
            Sell weapons from your bag for half their shop value (tier × 60 ÷ 2).
          </p>
        )}

        {category === 'sell' ? (
          <>
            <div className="tome-split">
              <div className="shop-list">
                {bagWeapons.length === 0 && (
                  <p className="detail-empty">You have no weapons to sell.</p>
                )}
                {bagWeapons.map((w, i) => (
                  <button
                    key={`${w.getName()}-${i}`}
                    type="button"
                    className={`shop-entry ${sellIdx === i ? 'selected' : ''}`}
                    onClick={() => setSellIdx(i)}
                  >
                    <IconView icon={w.getIcon()} size={40} />
                    <div className="shop-info">
                      <strong>
                        {w.getName()} (T{w.getTier()})
                      </strong>
                      <span>Sell for {weaponSellPrice(w)} gold</span>
                    </div>
                  </button>
                ))}
              </div>
              <div className="detail-pane">
                {selectedSell ? (
                  <>
                    <div className="detail-preview">
                      <IconView icon={selectedSell.getIcon()} size={72} />
                    </div>
                    <h3 className="detail-name">{selectedSell.getName()}</h3>
                    <span className="detail-banner">Sell for {sellPrice} gold</span>
                    <p className="detail-text">{selectedSell.getToolTipText()}</p>
                  </>
                ) : (
                  <p className="detail-empty">Select a weapon to sell.</p>
                )}
              </div>
            </div>
            <div className="btn-row">
              <button
                type="button"
                className="btn primary"
                disabled={!selectedSell}
                onClick={() => {
                  if (!selectedSell) return;
                  sellWeapon(sellIdx);
                  setSellIdx((idx) => Math.max(0, Math.min(idx, bagWeapons.length - 2)));
                }}
              >
                Sell ({sellPrice}g)
              </button>
              <button type="button" className="btn" onClick={closeModal}>
                Leave Shop
              </button>
            </div>
          </>
        ) : (
          <>
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
          </>
        )}
      </div>
    </div>
  );
}
