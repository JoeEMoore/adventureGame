import { useState } from 'react';
import type { Item } from '../game/items/Item';
import { Accessory } from '../game/items/accessories/Accessory';
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
  const equipAccessory = useGameStore((s) => s.equipAccessory);
  const unequipAccessory = useGameStore((s) => s.unequipAccessory);

  const [category, setCategory] = useState<InventoryCategory>(initialCategory);
  const [selectedIdx, setSelectedIdx] = useState(0);

  void tick;
  if (!player) return null;

  const inv = player.getInventory();
  const weapons = inv.getWeapons();
  const consumables = inv.getConsumables();
  const accessories = inv.getAccessories();
  const equippedCount = inv.getEquippedAccessories().length;
  const inFight = screen === 'fight' && !!fightState;
  const canUseItem = !inFight || !!fightState?.isPlayersTurn;

  const items: Item[] =
    category === 'weapons' ? weapons : category === 'consumables' ? consumables : accessories;
  const capacityLabel =
    category === 'weapons'
      ? `${weapons.length}/${inv.getMaxWeapons()}`
      : category === 'consumables'
        ? `${consumables.length}/${inv.getMaxConsumables()}`
        : `${equippedCount}/${inv.getMaxAccessories()} equipped · ${accessories.length} carried`;
  const safeIdx = clampIndex(selectedIdx, items.length);
  const selected = items[safeIdx] ?? null;
  const selectedAccessory =
    category === 'accessories' && selected instanceof Accessory ? selected : null;
  const selectedIsEquipped = selectedAccessory ? inv.isAccessoryEquipped(selectedAccessory) : false;
  const canEquip =
    !!selectedAccessory && !selectedIsEquipped && equippedCount < inv.getMaxAccessories();
  const canUnequip = !!selectedAccessory && selectedIsEquipped;

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
              ? 'Using a consumable spends your turn. You can swap accessories freely.'
              : 'Wait for your turn to use consumables. You can still swap accessories.'}
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
              {equippedCount}/{inv.getMaxAccessories()} eq
            </span>
          </button>
        </div>

        {category === 'accessories' && (
          <p className="inventory-capacity">{capacityLabel}</p>
        )}

        <div className="tome-split">
          <ul className="item-list" role="listbox" aria-label={`${category} list`}>
            {items.length === 0 && <li className="empty-slot">Empty</li>}
            {items.map((item, i) => {
              const equipped =
                category === 'accessories' && item instanceof Accessory
                  ? inv.isAccessoryEquipped(item)
                  : false;
              return (
                <li key={i}>
                  <button
                    type="button"
                    role="option"
                    aria-selected={safeIdx === i}
                    className={`${safeIdx === i ? 'selected' : ''}${equipped ? ' equipped' : ''}`}
                    onClick={() => setSelectedIdx(i)}
                  >
                    <IconView icon={item.getIcon()} size={28} />
                    <span className="item-list-label">
                      {category === 'weapons' ? item.toString() : item.getName()}
                      {equipped ? ' · Equipped' : ''}
                    </span>
                  </button>
                </li>
              );
            })}
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
                      : selectedIsEquipped
                        ? 'Equipped'
                        : 'Carried'}
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
          {category === 'accessories' && (
            <>
              <button
                type="button"
                className="btn primary"
                disabled={!canEquip}
                title={
                  selectedIsEquipped
                    ? 'Already equipped'
                    : equippedCount >= inv.getMaxAccessories()
                      ? 'No free equip slots'
                      : 'Equip this accessory'
                }
                onClick={() => equipAccessory(safeIdx)}
              >
                Equip
              </button>
              <button
                type="button"
                className="btn"
                disabled={!canUnequip}
                onClick={() => unequipAccessory(safeIdx)}
              >
                Unequip
              </button>
            </>
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
