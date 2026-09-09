import { useState } from 'react';
import { useGameStore } from '../store/gameStore';
import { IconView } from './IconView';

export function InventoryModal() {
  const tick = useGameStore((s) => s.tick);
  const player = useGameStore((s) => s.player);
  const closeModal = useGameStore((s) => s.closeModal);
  const useConsumable = useGameStore((s) => s.useConsumable);
  const dropWeapon = useGameStore((s) => s.dropWeapon);
  const dropConsumable = useGameStore((s) => s.dropConsumable);
  const [weaponIdx, setWeaponIdx] = useState(0);
  const [consIdx, setConsIdx] = useState(0);

  void tick;
  if (!player) return null;

  const weapons = player.getInventory().getWeapons();
  const consumables = player.getInventory().getConsumables();

  return (
    <div className="modal-backdrop" onClick={closeModal}>
      <div className="modal" onClick={(e) => e.stopPropagation()}>
        <h2>Inventory</h2>
        <div className="inventory-cols">
          <section>
            <h3>Weapons</h3>
            <ul className="item-list">
              {weapons.map((w, i) => (
                <li key={i}>
                  <button
                    type="button"
                    className={weaponIdx === i ? 'selected' : ''}
                    title={w.getToolTipText()}
                    onClick={() => setWeaponIdx(i)}
                  >
                    <IconView icon={w.getIcon()} size={28} />
                    {w.toString()}
                  </button>
                </li>
              ))}
            </ul>
          </section>
          <section>
            <h3>Consumables</h3>
            <ul className="item-list">
              {consumables.map((c, i) => (
                <li key={i}>
                  <button
                    type="button"
                    className={consIdx === i ? 'selected' : ''}
                    title={c.getToolTipText()}
                    onClick={() => setConsIdx(i)}
                  >
                    <IconView icon={c.getIcon()} size={28} />
                    {c.getName()}
                  </button>
                </li>
              ))}
            </ul>
          </section>
        </div>
        <div className="btn-row">
          <button
            type="button"
            className="btn primary"
            disabled={consumables.length === 0}
            onClick={() => useConsumable(consIdx)}
          >
            Use
          </button>
          <button
            type="button"
            className="btn"
            disabled={weapons.length === 0}
            onClick={() => {
              dropWeapon(weaponIdx);
              setWeaponIdx(0);
            }}
          >
            Drop Weapon
          </button>
          <button
            type="button"
            className="btn"
            disabled={consumables.length === 0}
            onClick={() => {
              dropConsumable(consIdx);
              setConsIdx(0);
            }}
          >
            Drop Consumable
          </button>
          <button type="button" className="btn" onClick={closeModal}>
            Close
          </button>
        </div>
      </div>
    </div>
  );
}
