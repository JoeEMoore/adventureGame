import { useCallback, useRef, type PointerEvent as ReactPointerEvent, type ReactNode } from 'react';
import type { BoxPos, FightBoxId } from '../game/fightLayout';
import { FIGHT_BOX_LABELS, clampPos } from '../game/fightLayout';

type Props = {
  id: FightBoxId;
  pos: BoxPos;
  editable: boolean;
  onMove: (id: FightBoxId, pos: BoxPos) => void;
  className?: string;
  children: ReactNode;
};

/**
 * Absolutely positioned fight UI box.
 * When `editable`, drag to reposition (layout editor). Keep this — do not remove.
 */
export function PositionedBox({ id, pos, editable, onMove, className = '', children }: Props) {
  const dragging = useRef(false);
  const origin = useRef({ pointerX: 0, pointerY: 0, startX: 0, startY: 0 });

  const onPointerDown = useCallback(
    (e: ReactPointerEvent<HTMLDivElement>) => {
      if (!editable) return;
      const target = e.target as HTMLElement;
      if (target.closest('button, a, input')) return;

      dragging.current = true;
      origin.current = {
        pointerX: e.clientX,
        pointerY: e.clientY,
        startX: pos.x,
        startY: pos.y,
      };
      e.currentTarget.setPointerCapture(e.pointerId);
      e.preventDefault();
    },
    [editable, pos.x, pos.y],
  );

  const onPointerMove = useCallback(
    (e: ReactPointerEvent<HTMLDivElement>) => {
      if (!editable || !dragging.current) return;
      const parent = e.currentTarget.offsetParent as HTMLElement | null;
      if (!parent) return;
      const rect = parent.getBoundingClientRect();
      const dx = ((e.clientX - origin.current.pointerX) / rect.width) * 100;
      const dy = ((e.clientY - origin.current.pointerY) / rect.height) * 100;
      onMove(
        id,
        clampPos({
          x: origin.current.startX + dx,
          y: origin.current.startY + dy,
        }),
      );
    },
    [editable, id, onMove],
  );

  const onPointerUp = useCallback((e: ReactPointerEvent<HTMLDivElement>) => {
    if (!dragging.current) return;
    dragging.current = false;
    try {
      e.currentTarget.releasePointerCapture(e.pointerId);
    } catch {
      /* already released */
    }
  }, []);

  return (
    <div
      className={`fight-pos-box ${editable ? 'editable' : ''} ${className}`}
      data-box-id={id}
      style={{ left: `${pos.x}%`, top: `${pos.y}%` }}
      onPointerDown={onPointerDown}
      onPointerMove={onPointerMove}
      onPointerUp={onPointerUp}
      onPointerCancel={onPointerUp}
    >
      {editable && <span className="fight-drag-label">{FIGHT_BOX_LABELS[id]}</span>}
      {children}
    </div>
  );
}
