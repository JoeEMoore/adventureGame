import { useState } from 'react';
import type { Item } from '../game/items/Item';
import { useGameStore } from '../store/gameStore';
import { IconView } from './IconView';

export type InventoryCategory = 'weapons' | 'consumables' | 'accessories';

type Props = {
  initialCategory?: InventoryCategory;
};

function clampIndex(idx: number, length: number): number {
  if (length <= 0) return 0;
  return Math.max(0, Math.min(idx, length - 1));
}

function afterRemoveIndex(prev: number, lengthBefore: number): number {
  const nextLen = lengthBefore - 1;
  if (nextLen <= 0) return 0;
  return Math.min(prev, nextLen - 1);
}

export function InventoryModal({ initialCategory = 'weapons' }: Props) {
  const tick = useGameStore((s) => s.tick);
  const player = useGameStore((s) => s.player);
  const screen = useGameStore((s) => s.screen);
  const fightState = useGameStore((s) => s.fightState);
  const closeModal = useGameStore((s) => s.closeModal);
  const useConsumable = useGameStore((s) => s.useConsumable);
  const dropWeapon = useGameStore((s) => s.dropWeapon);
  const dropConsumable = useGameStore((s) => s.dropConsumable);
  const dropAccessory = useGameStore((s) => s.dropAccessory);

  const [category, setCategory] = useState<InventoryCategory>(initialCategory);
  const [selectedIdx, setSelectedIdx] = useState(0);

  void tick;
  if (!player) return null;

  const inv = player.getInventory();
  const weapons = inv.getWeapons();
  const consumables = inv.getConsumables();
  const accessories = inv.getAccessories();
  const inFight = screen === 'fight' && !!fightState;
  const canUseItem = !inFight || !!fightState?.isPlayersTurn;

  const items: Item[] =
    category === 'weapons' ? weapons : category === 'consumables' ? consumables : accessories;
  const max =
    category === 'weapons'
      ? inv.getMaxWeapons()
      : category === 'consumables'
        ? inv.getMaxConsumables()
        : inv.getMaxAccessories();
  const safeIdx = clampIndex(selectedIdx, items.length);
  const selected = items[safeIdx] ?? null;

  const selectCategory = (next: InventoryCategory) => {
    setCategory(next);
    setSelectedIdx(0);
  };

  const onDrop = () => {
    if (!selected) return;
    const len = items.length;
    if (category === 'weapons') dropWeapon(safeIdx);
    else if (category === 'consumables') dropConsumable(safeIdx);
    else dropAccessory(safeIdx);
    setSelectedIdx(afterRemoveIndex(safeIdx, len));
  };

  return (
    <div className="modal-backdrop" onClick={closeModal}>
      <div className="modal tome-modal inventory-layout" onClick={(e) => e.stopPropagation()}>
        <div className="tome-header">
          <h2>Inventory</h2>
        </div>

        {inFight && (
          <p className="inventory-turn-note">
            {canUseItem
              ? 'Using a consumable spends your turn.'
              : 'Wait for your turn to use consumables.'}
          </p>
        )}

        <div className="tome-tabs" role="tablist" aria-label="Inventory categories">
          <button
            type="button"
            role="tab"
            aria-selected={category === 'weapons'}
            className={`tome-tab ${category === 'weapons' ? 'active' : ''}`}
            onClick={() => selectCategory('weapons')}
          >
            Weapons
            <span className="tab-cap">
              {weapons.length}/{inv.getMaxWeapons()}
            </span>
          </button>
          <button
            type="button"
            role="tab"
            aria-selected={category === 'consumables'}
            className={`tome-tab ${category === 'consumables' ? 'active' : ''}`}
            onClick={() => selectCategory('consumables')}
          >
            Consumables
            <span className="tab-cap">
              {consumables.length}/{inv.getMaxConsumables()}
            </span>
          </button>
          <button
            type="button"
            role="tab"
            aria-selected={category === 'accessories'}
            className={`tome-tab ${category === 'accessories' ? 'active' : ''}`}
            onClick={() => selectCategory('accessories')}
          >
            Accessories
            <span className="tab-cap">
              {accessories.length}/{inv.getMaxAccessories()}
            </span>
          </button>
        </div>

        <div className="tome-split">
          <ul className="item-list" role="listbox" aria-label={`${category} list`}>
            {items.length === 0 && (
              <li className="empty-slot">
                Empty — {items.length}/{max}
              </li>
            )}
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
                  {category === 'weapons' ? item.toString() : item.getName()}
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
                <h3 className="detail-name">
                  {category === 'weapons' ? selected.toString() : selected.getName()}
                </h3>
                <span className="detail-banner">
                  {category === 'weapons'
                    ? 'Weapon'
                    : category === 'consumables'
                      ? 'Consumable'
                      : 'Accessory'}
                </span>
                <p className="detail-text">{selected.getToolTipText()}</p>
              </>
            ) : (
              <p className="detail-empty">Select an item to inspect it.</p>
            )}
          </div>
        </div>

        <div className="btn-row">
          {category === 'consumables' && (
            <button
              type="button"
              className="btn primary"
              disabled={!selected || !canUseItem}
              title={
                inFight && canUseItem
                  ? 'Spends your combat turn'
                  : !canUseItem
                    ? 'Not your turn'
                    : undefined
              }
              onClick={() => useConsumable(safeIdx)}
            >
              Use{inFight ? ' (turn)' : ''}
            </button>
          )}
          <button type="button" className="btn" disabled={!selected} onClick={onDrop}>
            Drop
          </button>
          <button type="button" className="btn" onClick={closeModal}>
            Close
          </button>
        </div>
      </div>
    </div>
  );
}
