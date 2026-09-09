# Adventure Game (Web)

React + Vite + TypeScript port of the CPSC 224 Java Swing dungeon crawler.

## Run locally

```bash
cd web
npm install
npm run dev
```

Open `http://localhost:5173/adventureGame/` (the `/adventureGame/` base path matches GitHub Pages).

## Build

```bash
cd web
npm run build
npm run preview
```

## GitHub Pages

Deployed from repo root via `.github/workflows/deploy-pages.yml` on push to `main`.

1. Repo **Settings → Pages → Source: GitHub Actions**
2. After a successful workflow run, play at:
   https://JoeEMoore.github.io/adventureGame/

## Layout

- `src/game/` — pure TypeScript port of combat, levels, factories, pools
- `src/store/gameStore.ts` — Zustand session/UI state
- `src/components/` — screens and modals
- `public/assets/` — images and sprites from the original game

The original Java sources remain at the repo root for reference.
