# KLEPTOMAZEIAC

Turn-based dungeon crawler originally built in Java Swing (CPSC 224).  
The playable web port lives in [`web/`](web/).

## Play (web)

```bash
cd web
npm install
npm run dev
```

Open the URL Vite prints. With the GitHub Pages base path configured, that is usually:

`http://localhost:5173/adventureGame/`

### GitHub Pages

The site deploys automatically from `main` via `.github/workflows/deploy-pages.yml`.

1. On GitHub: **Settings → Pages → Build and deployment → Source: GitHub Actions**
2. Push to `main` (or run the workflow manually)
3. Play at: https://JoeEMoore.github.io/adventureGame/


## Java (legacy)

```bash
mvn -DskipTests package
java -jar target/AdventureGame.jar
```
