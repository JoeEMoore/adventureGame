import react from '@vitejs/plugin-react'
import { defineConfig } from 'vite'

// https://vite.dev/config/
export default defineConfig({
  // Required for GitHub Pages project site: https://JoeEMoore.github.io/adventureGame/
  base: '/adventureGame/',
  plugins: [react()],
})
