# adventureGame style reference

Source of truth for UI and art direction. Agents: read this before visual work. Humans: drop good/bad examples into the sibling folders.

## Palette (from `web/src/styles/game.css`)

| Token | Value | Use |
|-------|--------|-----|
| Page bg | `#1a1410` | App shell |
| `--ink` / `--btn-ink` | `#2a1f14` | Text on parchment |
| `--parchment` | `#e8d9b8` | Modal / panel fill |
| `--parchment-dark` | `#d4c19a` | Tabs, inset cells |
| `--leather` | `#5c3d2e` | Outer frame borders |
| `--leather-dark` | `#3a2418` | Inner stitch / dark edge |
| `--stitch` | `#c4a574` | Stitched inner line |
| `--banner` | `#c45a28` | Header bars, active tabs |
| `--banner-gold` | `#e8c56a` | Accent highlights |
| `--menu` | `#6fad7a` | Primary / positive actions |
| `--game` | `#c4a35a` | Secondary accent |
| `--health` | `#e02d2d` | HP |
| `--poison` | `#209410` | Poison / nature status |
| `--cmd-fight` | `#c45b6a` | Fight command button |
| `--cmd-bag` | `#d4893a` | Bag command button |
| `--panel` | `#e8d9b8` | Alias for parchment panels |
| `--panel-border` | `#5c3d2e` | Alias for leather borders |
| `--btn` | `#f0e6d0` | Default buttons |

Danger actions use roughly `#c45b5b` (see `.btn.danger`).

## Typography & chrome

- Font stack: `'Segoe UI', 'Trebuchet MS', sans-serif` (chunky labels via weight + letter-spacing)
- Panels: parchment fill, thick leather border, inset stitch line (`.tome-panel` / `.tome-modal`)
- Headers: orange banner bar (`.tome-header`)
- Tabs: category strip (`.tome-tabs` / `.tome-tab`)
- Detail panes: preview + name + body text (`.detail-pane`)
- Buttons: leather-bordered parchment; primary mint; danger red
- Fight commands: bubbly color-coded `.cmd-btn` (fight / bag / back)
- Screens often sit on full-bleed background images with a dark gradient scrub for readability

## Screens & atmosphere

- Splash / select: dungeon background + centered tome panel
- Map: room tiles with door configurations; HUD as tome panel; debris and vines as light clutter
- Fight: Pokémon-style diagonal battlefield + bottom message/command dock
- Win / death: dedicated backgrounds with tome end panels

Canonical asset roots:

- Backgrounds: `web/public/assets/images/backgrounds/`
- Rooms: `web/public/assets/images/rooms/`
- Creatures: `web/public/assets/images/creatures/`
- Debris / vines: `web/public/assets/sprites/debris/`, `.../roomTextures/`

## Room & sprite conventions

- Room PNGs encode door layout in the filename (`oneDoor_Down`, `threeDoor_Missing_Up`, …)
- Debris (`skull`, `bones`, `rock`, `rock2`, `barrel`) should read as floor clutter at modest scale
- Barrels / rocks stay upright; skulls/bones may rotate
- Vines are full-room overlays; flip X only, no rotation
- Prefer adding art next to existing sprites, then wiring through decor/room helpers

## Motion & FX

- Prefer subtle, purposeful feedback (combat FX already live under `web/src/fx/`)
- Avoid heavy glow stacks, particle spam, or UI “juice” that fights the pixel art

## Do / don’t

**Do**

- Reuse CSS variables and `.btn` / `.tome-*` / `.cmd-*` / `.screen` patterns
- Match sprite scale and palette to neighboring assets
- Keep panels readable over busy backgrounds (parchment + leather frame)

**Don’t**

- Purple-on-white or generic SaaS gradients
- Flat single-color pages with no dungeon atmosphere
- Dark translucent glass panels (replaced by parchment/leather chrome)
- Oversized debris that blocks exits or UI
- New fonts/palettes without updating this file

## Reference folders

| Folder | Put here |
|--------|----------|
| `ui/` | Screenshots of good HUD/modals/screens |
| `rooms/` | Favorite room tiles / door layouts |
| `sprites/` | Creatures, items, debris that define the look |
| `anti-patterns/` | “Don’t ship this” examples |

Seeded starters (replace/extend freely):

- `ui/selectBackground2.png`, `ui/fightBackground.png`
- `rooms/fourDoor.png`, `rooms/oneDoor_Down.png`
- `sprites/Goblin.png`, `sprites/barrel.png`, `sprites/skull.png`

Also use live assets under `web/public/assets/` when needed.
