/** Root-relative asset paths that respect Vite's `base` (GitHub Pages). */
export function asset(path: string): string {
  const cleaned = path.replace(/^\/+/, '');
  const base = import.meta.env.BASE_URL || '/';
  return `${base}${cleaned}`;
}
