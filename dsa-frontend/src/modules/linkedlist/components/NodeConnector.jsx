/**
 * Renders between every pair of adjacent boxes (including the null
 * bookends) — a top arrow pointing right for `.next`, a bottom arrow
 * pointing left for `.prev`, so the doubly-linked structure is visible
 * at a glance.
 */
export default function NodeConnector() {
  return (
    <div className="flex flex-col items-center justify-center gap-1 px-1 pb-[26px]">
      <svg width="36" height="14" viewBox="0 0 36 14" className="text-cell-shift">
        <line x1="2" y1="7" x2="30" y2="7" stroke="currentColor" strokeWidth="1.5" />
        <path d="M25 3 L31 7 L25 11" fill="none" stroke="currentColor" strokeWidth="1.5" strokeLinecap="round" strokeLinejoin="round" />
      </svg>
      <svg width="36" height="14" viewBox="0 0 36 14" className="text-cell-swap">
        <line x1="6" y1="7" x2="34" y2="7" stroke="currentColor" strokeWidth="1.5" />
        <path d="M11 3 L5 7 L11 11" fill="none" stroke="currentColor" strokeWidth="1.5" strokeLinecap="round" strokeLinejoin="round" />
      </svg>
    </div>
  )
}
