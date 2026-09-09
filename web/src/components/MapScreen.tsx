import { BossRoom } from '../game/levels/rooms/Room';
import { ShopRoom } from '../game/levels/rooms/shop/Shop';
import { Coordinate } from '../game/levels/Coordinate';
import { ITEMS_SHEET } from '../game/utils/icons';
import { useGameStore } from '../store/gameStore';
import { IconView, SpriteIcon } from './IconView';
import { getRoomBird, getRoomImage } from './roomArt';
import { InventoryModal } from './InventoryModal';
import { RoomItemsModal } from './RoomItemsModal';
import { ShopModal } from './ShopModal';

export function MapScreen() {
  const tick = useGameStore((s) => s.tick);
  const player = useGameStore((s) => s.player);
  const level = useGameStore((s) => s.level);
  const modal = useGameStore((s) => s.modal);
  const exploreRoom = useGameStore((s) => s.exploreRoom);
  const openInventory = useGameStore((s) => s.openInventory);
  const openRoomItems = useGameStore((s) => s.openRoomItems);
  const exitToSplash = useGameStore((s) => s.exitToSplash);

  void tick;
  if (!player || !level) return null;

  const rooms = level.getRooms();
  const playerPos = player.getCurrentPosition()!;

  return (
    <div className="screen map-screen">
      <aside className="hud">
        <div className="hud-stats">
          <IconView icon={player.getIcon()} size={72} />
          <div>
            <div>
              <strong>{player.getName()}</strong>
            </div>
            <div>
              HP: {Math.round(player.getHealth())}/{player.getMaxHealth()}
            </div>
            <div>Gold: {player.getGold()}</div>
          </div>
        </div>
        <div className="btn-col">
          <button type="button" className="btn" onClick={openInventory}>
            Inventory
          </button>
          <button type="button" className="btn danger" onClick={exitToSplash}>
            Exit
          </button>
        </div>
      </aside>

      <div className="map-grid-wrap">
        <div
          className="map-grid"
          style={{
            gridTemplateColumns: `repeat(${rooms.length}, minmax(72px, 1fr))`,
          }}
        >
          {rooms.map((row, r) =>
            row.map((room, c) => {
              const pos = new Coordinate(r, c);
              if (!room) {
                return <div key={`${r}-${c}`} className="room-cell empty" />;
              }

              const isPlayerHere = pos.equals(playerPos);
              const canMove = pos.isAdjacent(playerPos);
              const explored = room.isExplored();
              const discovered = room.isDiscovered();

              if (!discovered && !explored) {
                return <div key={`${r}-${c}`} className="room-cell empty" />;
              }

              const art = explored ? getRoomImage(rooms, pos) : null;
              const bird = explored ? getRoomBird(rooms, pos) : null;

              return (
                <div
                  key={`${r}-${c}`}
                  className={`room-cell ${canMove ? 'movable' : ''} ${isPlayerHere ? 'here' : ''}${bird ? ' has-bird' : ''}`}
                  title={`Room (${r},${c})`}
                >
                  {canMove ? (
                    <button
                      type="button"
                      className="room-move-hit"
                      onClick={() => exploreRoom(pos)}
                      aria-label={`Enter room ${r},${c}`}
                    />
                  ) : null}

                  {explored && art ? (
                    <img src={art} alt="" className="room-art" />
                  ) : (
                    <img
                      src="/assets/images/rooms/undiscovered.png"
                      alt=""
                      className="room-art fog"
                    />
                  )}

                  {explored &&
                    room.getDecorations().map((dec, i) => (
                      <img
                        key={`dec-${i}`}
                        src={`/assets/sprites/debris/${dec.kind}.png`}
                        alt=""
                        className="room-debris"
                        style={{
                          left: `${dec.x}%`,
                          top: `${dec.y}%`,
                          transform: `translate(-50%, -50%) rotate(${dec.rotation}deg) scale(${dec.scale})`,
                        }}
                      />
                    ))}

                  {explored &&
                    room.getVines().map((vine, i) => (
                      <img
                        key={`vine-${i}`}
                        src={`/assets/sprites/roomTextures/${vine.kind}.png`}
                        alt=""
                        className={`room-vine${vine.flipX ? ' flip-x' : ''}`}
                      />
                    ))}

                  {bird && (
                    <img
                      src="/assets/sprites/debris/audreyBird2.png"
                      alt=""
                      className={`room-bird${bird.flipX ? ' flip-x' : ''}`}
                      style={{ left: `${bird.x}%` }}
                    />
                  )}

                  {discovered && room instanceof BossRoom && (
                    <span className="room-marker">
                      <SpriteIcon sheet={ITEMS_SHEET} row={15} col={6} size={40} />
                    </span>
                  )}
                  {discovered && room instanceof ShopRoom && (
                    <span className="room-marker">
                      <SpriteIcon sheet={ITEMS_SHEET} row={24} col={3} size={40} />
                    </span>
                  )}
                  {explored && room.hasItems() && (
                    <button
                      type="button"
                      className="room-marker loot"
                      disabled={!isPlayerHere}
                      title={isPlayerHere ? 'View room items' : 'Enter this room to loot'}
                      onClick={(e) => {
                        e.stopPropagation();
                        if (isPlayerHere) openRoomItems();
                      }}
                    >
                      {room.getItems().map((item, i) => (
                        <IconView
                          key={`${item.getName()}-${i}`}
                          icon={item.getIcon()}
                          size={36}
                          title={item.getName()}
                        />
                      ))}
                    </button>
                  )}
                  {isPlayerHere && (
                    <span className="room-player">
                      <IconView icon={player.getIcon()} size={48} />
                    </span>
                  )}
                </div>
              );
            }),
          )}
        </div>
      </div>

      {modal === 'inventory' && <InventoryModal />}
      {modal === 'roomItems' && <RoomItemsModal />}
      {modal === 'shop' && <ShopModal />}
    </div>
  );
}
