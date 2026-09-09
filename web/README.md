# Adventure Game (Web)

React + Vite + TypeScript port of the CPSC 224 Java Swing dungeon crawler.

## Run

```bash
cd web
npm install
npm run dev
```

Then open the URL Vite prints (usually `http://localhost:5173`).

## Build

```bash
cd web
npm run build
npm run preview
```

## Layout

- `src/game/` — pure TypeScript port of combat, levels, factories, pools
- `src/store/gameStore.ts` — Zustand session/UI state
- `src/components/` — screens and modals
- `public/assets/` — images and sprites from the original game

The original Java sources remain at the repo root for reference.
