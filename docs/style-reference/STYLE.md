# adventureGame style reference

Source of truth for UI and art direction. Agents: read this before visual work. Humans: drop good/bad examples into the sibling folders.

## Palette (from `web/src/styles/game.css`)

| Token | Value | Use |
|-------|--------|-----|
| Page bg | `#0e1020` | App shell |
| `--ink` / btn ink | `#1a1c28` | Text on light buttons |
| `--menu` | `#9cdbad` | Primary / positive actions |
| `--game` | `#8f93b8` | Secondary accent |
| `--health` | `#e02d2d` | HP |
| `--poison` | `#209410` | Poison / nature status |
| `--panel` | `rgba(20, 22, 36, 0.82)` | Modal / overlay panels |
| `--panel-border` | `rgba(255, 255, 255, 0.18)` | Panel edges |
| `--btn` | `#e8eef5` | Default buttons |
| Body text | `#f4f6fb` | On dark panels |

Danger actions use roughly `#c45b5b` (see `.btn.danger`).

## Typography & chrome

- Font stack: `'Segoe UI', 'Trebuchet MS', sans-serif` (keep unless a deliberate redesign)
- Panels: semi-transparent dark fill, light hairline border, ~12px radius
- Buttons: ~6px radius, clear primary (mint) vs default (light) vs danger
- Screens often sit on full-bleed background images with a dark gradient scrub for readability

## Screens & atmosphere

- Splash / select: dungeon background + centered panel (see `selectBackground2.png`)
- Map: room tiles with door configurations; debris and vines as light clutter, not noise
- Fight: dedicated fight backgrounds; keep HUD readable over art
- Win: dedicated win background

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

- Reuse CSS variables and `.btn` / `.screen` patterns
- Match sprite scale and palette to neighboring assets
- Keep panels readable over busy backgrounds (gradient + `--panel`)

**Don’t**

- Purple-on-white or generic SaaS gradients
- Flat single-color pages with no dungeon atmosphere
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
