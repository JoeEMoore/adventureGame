import type { IconRef } from '../game/utils/icons';

interface GameIconProps {
  icon: IconRef;
  size?: number;
  className?: string;
  title?: string;
}

export function SpriteIcon({
  sheet,
  row,
  col,
  tile = 32,
  size = 64,
  title,
  className,
}: {
  sheet: string;
  row: number;
  col: number;
  tile?: number;
  size?: number;
  title?: string;
  className?: string;
}) {
  const scale = size / tile;
  return (
    <div
      className={className}
      title={title}
      style={{ width: size, height: size, overflow: 'hidden', flexShrink: 0 }}
    >
      <div
        style={{
          width: tile,
          height: tile,
          backgroundImage: `url(${sheet})`,
          backgroundRepeat: 'no-repeat',
          backgroundPosition: `-${col * tile}px -${row * tile}px`,
          transform: `scale(${scale})`,
          transformOrigin: 'top left',
        }}
      />
    </div>
  );
}

export function IconView({ icon, size = 64, className, title }: GameIconProps) {
  if (!icon) {
    return (
      <div
        className={className}
        title={title}
        style={{ width: size, height: size, background: 'rgba(0,0,0,0.2)', borderRadius: 4 }}
      />
    );
  }
  if (icon.type === 'sprite') {
    return (
      <SpriteIcon
        sheet={icon.sheet}
        row={icon.row}
        col={icon.col}
        tile={icon.tile ?? 32}
        size={size}
        title={title}
        className={className}
      />
    );
  }
  return (
    <img
      src={icon.path}
      alt=""
      title={title}
      className={className}
      style={{
        width: size,
        height: size,
        objectFit: 'contain',
        transform: icon.flip ? 'scaleX(-1)' : undefined,
      }}
    />
  );
}
