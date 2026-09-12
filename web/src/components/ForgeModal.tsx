import { useState } from 'react';
import {
  ACCESSORY_KINDS,
  AccessoryFactory,
} from '../game/items/accessories/AccessoryFactory';
import type { AccessoryKind } from '../game/items/accessories/Accessory';
import {
  canUpgradeWeaponTier,
  weaponGoldCost,
} from '../game/levels/forge';
import { ForgeRoom } from '../game/levels/rooms/ForgeRoom';
import { getCurrentRoom, useGameStore } from '../store/gameStore';
import { IconView } from './IconView';

type ForgeTab = 'weapons' | 'fuse';

export function ForgeModal() {
  const tick = useGameStore((s) => s.tick);
  const player = useGameStore((s) => s.player);
  const closeModal = useGameStore((s) => s.closeModal);
  const forgeUpgradeWeapon = useGameStore((s) => s.forgeUpgradeWeapon);
  const forgeCombineAccessories = useGameStore((s) => s.forgeCombineAccessories);
  const [tab, setTab] = useState<ForgeTab>('weapons');
  const [weaponIdx, setWeaponIdx] = useState(0);
  const [sacrifice, setSacrifice] = useState<number[]>([]);
  const [resultKind, setResultKind] = useState<AccessoryKind>(ACCESSORY_KINDS[0]);
  void tick;

  const room = getCurrentRoom();
  if (!player || !(room instanceof ForgeRoom)) return null;

  const inv = player.getInventory();
  const weapons = inv.getWeapons();
  const accessories = inv.getAccessories();
  const selectedWeapon = weapons[weaponIdx] ?? null;
  const upgradeCost = selectedWeapon ? weaponGoldCost(selectedWeapon) : 0;
  const alreadyUpgraded = selectedWeapon ? room.hasUpgradedWeapon(selectedWeapon) : false;
  const canUpgrade =
    !!selectedWeapon &&
    canUpgradeWeaponTier(selectedWeapon) &&
    !alreadyUpgraded &&
    player.getGold() >= upgradeCost;

  const resultPreview = AccessoryFactory.create(resultKind);
  const canFuse = sacrifice.length === 3 && accessories.length >= 3;

  const toggleSacrifice = (index: number) => {
    setSacrifice((prev) => {
      if (prev.includes(index)) return prev.filter((i) => i !== index);
      if (prev.length >= 3) return prev;
      return [...prev, index];
    });
  };

  return (
    <div className="modal-backdrop">
      <div className="modal tome-modal forge-modal" onClick={(e) => e.stopPropagation()}>
        <div className="tome-header">
          <h2>Forge</h2>
        </div>
        <p className="shop-gold">Your gold: {player.getGold()}</p>
        <p className="shop-section-note">
          Temper weapons one tier higher (once each per floor), or sacrifice three accessories to
          forge one of your choice.
        </p>

        <div className="tome-tabs" role="tablist" aria-label="Forge services">
          <button
            type="button"
            role="tab"
            aria-selected={tab === 'weapons'}
            className={`tome-tab ${tab === 'weapons' ? 'active' : ''}`}
            onClick={() => setTab('weapons')}
          >
            Upgrade Weapon
          </button>
          <button
            type="button"
            role="tab"
            aria-selected={tab === 'fuse'}
            className={`tome-tab ${tab === 'fuse' ? 'active' : ''}`}
            onClick={() => setTab('fuse')}
          >
            Fuse Accessories
          </button>
        </div>

        {tab === 'weapons' ? (
          <>
            <div className="tome-split">
              <div className="shop-list">
                {weapons.length === 0 && <p className="detail-empty">No weapons to upgrade.</p>}
                {weapons.map((w, i) => {
                  const cost = weaponGoldCost(w);
                  const used = room.hasUpgradedWeapon(w);
                  const maxed = !canUpgradeWeaponTier(w);
                  return (
                    <button
                      key={`${w.getName()}-${i}`}
                      type="button"
                      className={`shop-entry ${weaponIdx === i ? 'selected' : ''}`}
                      onClick={() => setWeaponIdx(i)}
                    >
                      <IconView icon={w.getIcon()} size={40} />
                      <div className="shop-info">
                        <strong>
                          {w.getName()} (T{w.getTier()})
                        </strong>
                        <span>
                          {maxed
                            ? 'Max tier'
                            : used
                              ? 'Already upgraded this floor'
                              : `Upgrade for ${cost} gold`}
                        </span>
                      </div>
                    </button>
                  );
                })}
              </div>
              <div className="detail-pane">
                {selectedWeapon ? (
                  <>
                    <div className="detail-preview">
                      <IconView icon={selectedWeapon.getIcon()} size={72} />
                    </div>
                    <h3 className="detail-name">{selectedWeapon.getName()}</h3>
                    <span className="detail-banner">
                      Tier {selectedWeapon.getTier()}
                      {canUpgradeWeaponTier(selectedWeapon)
                        ? ` → ${selectedWeapon.getTier() + 1}`
                        : ' (max)'}
                    </span>
                    <p className="detail-text">{selectedWeapon.getToolTipText()}</p>
                    {alreadyUpgraded && (
                      <p className="forge-status">Upgraded once already on this floor.</p>
                    )}
                  </>
                ) : (
                  <p className="detail-empty">Select a weapon.</p>
                )}
              </div>
            </div>
            <div className="btn-row">
              <button
                type="button"
                className="btn primary"
                disabled={!canUpgrade}
                onClick={() => forgeUpgradeWeapon(weaponIdx)}
              >
                Upgrade ({upgradeCost}g)
              </button>
              <button type="button" className="btn" onClick={closeModal}>
                Leave Forge
              </button>
            </div>
          </>
        ) : (
          <>
            <p className="shop-section-note">
              Select exactly three accessories to sacrifice ({sacrifice.length}/3), then choose what
              to forge.
            </p>
            <div className="tome-split forge-fuse-split">
              <div className="shop-list">
                <p className="forge-list-heading">Sacrifice</p>
                {accessories.length === 0 && (
                  <p className="detail-empty">No accessories in your bag.</p>
                )}
                {accessories.map((a, i) => {
                  const selected = sacrifice.includes(i);
                  const equipped = inv.isAccessoryEquipped(a);
                  return (
                    <button
                      key={`${a.getName()}-${i}`}
                      type="button"
                      className={`shop-entry ${selected ? 'selected' : ''}${
                        equipped ? ' equipped' : ''
                      }`}
                      onClick={() => toggleSacrifice(i)}
                    >
                      <IconView icon={a.getIcon()} size={40} />
                      <div className="shop-info">
                        <strong>{a.getName()}</strong>
                        <span>{equipped ? 'Equipped · tap to select' : 'Tap to select'}</span>
                      </div>
                    </button>
                  );
                })}
              </div>
              <div className="shop-list">
                <p className="forge-list-heading">Forge into</p>
                {ACCESSORY_KINDS.map((kind) => {
                  const preview = AccessoryFactory.create(kind);
                  return (
                    <button
                      key={kind}
                      type="button"
                      className={`shop-entry ${resultKind === kind ? 'selected' : ''}`}
                      onClick={() => setResultKind(kind)}
                    >
                      <IconView icon={preview.getIcon()} size={40} />
                      <div className="shop-info">
                        <strong>{preview.getName()}</strong>
                        <span>{preview.getDescription()}</span>
                      </div>
                    </button>
                  );
                })}
              </div>
            </div>
            <div className="detail-pane forge-result-pane">
              <div className="detail-preview">
                <IconView icon={resultPreview.getIcon()} size={56} />
              </div>
              <div>
                <h3 className="detail-name">{resultPreview.getName()}</h3>
                <p className="detail-text">{resultPreview.getDescription()}</p>
              </div>
            </div>
            <div className="btn-row">
              <button
                type="button"
                className="btn primary"
                disabled={!canFuse}
                onClick={() => {
                  if (!canFuse) return;
                  forgeCombineAccessories(sacrifice, resultKind);
                  setSacrifice([]);
                }}
              >
                Fuse into {resultPreview.getName()}
              </button>
              <button type="button" className="btn" onClick={closeModal}>
                Leave Forge
              </button>
            </div>
          </>
        )}
      </div>
    </div>
  );
}
