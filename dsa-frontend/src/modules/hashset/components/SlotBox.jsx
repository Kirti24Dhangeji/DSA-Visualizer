import { motion } from 'framer-motion'

const COLOR_CLASSES = {
  'cell-success': { border: 'border-cell-success', text: 'text-cell-success', glow: 'shadow-[0_0_16px_-2px_#34D399]' },
  'cell-danger': { border: 'border-cell-danger', text: 'text-cell-danger', glow: 'shadow-[0_0_16px_-2px_#F87171]' },
  'cell-shift': { border: 'border-cell-shift', text: 'text-cell-shift', glow: 'shadow-[0_0_16px_-2px_#60A5FA]' },
  'cell-compare': { border: 'border-cell-compare', text: 'text-cell-compare', glow: 'shadow-[0_0_16px_-2px_#F0B429]' },
  'cell-swap': { border: 'border-cell-swap', text: 'text-cell-swap', glow: 'shadow-[0_0_16px_-2px_#A78BFA]' },
}

// The backend sends "null" for a slot that has never been used, and "DEL"
// for a slot whose element was deleted (an open-addressing tombstone).
const EMPTY = 'null'
const DELETED = 'DEL'

/**
 * One slot in the hash table. `index` is the slot's position in the table
 * (0, 1, 2 …), used as the layout key so Framer Motion can animate a
 * resize (capacity growing) via the `layout` prop.
 */
export default function SlotBox({ value, index, active, colorToken }) {
  const isEmpty = value === EMPTY
  const isDeleted = value === DELETED
  const colors = active && !isEmpty && !isDeleted ? COLOR_CLASSES[colorToken] ?? COLOR_CLASSES['cell-shift'] : null

  return (
    <motion.div
      layout
      layoutId={`slot-${index}`}
      initial={{ opacity: 0, scale: 0.6 }}
      animate={{ opacity: 1, scale: 1 }}
      exit={{ opacity: 0, scale: 0.6 }}
      transition={{ type: 'spring', stiffness: 500, damping: 32 }}
      className="flex flex-col items-center gap-1.5"
    >
      {/* plain slot index: 0, 1, 2 … */}
      <span className="font-mono text-[11px] text-mist-400 tabular-nums">{index}</span>

      <motion.div
        animate={{ scale: active && !isEmpty ? 1.08 : 1 }}
        transition={{ duration: 0.25 }}
        className={`flex h-16 w-16 items-center justify-center rounded-md font-mono text-xl font-semibold tabular-nums
          ${
            isEmpty
              ? 'border-2 border-dashed border-graphite-500 bg-transparent'
              : isDeleted
                ? 'border-2 border-dashed border-cell-danger/50 bg-graphite-800/40'
                : active
                  ? `border-2 bg-graphite-800 ${colors.border} ${colors.text} ${colors.glow}`
                  : 'border-2 border-graphite-600 bg-graphite-800 text-mist-100'
          }`}
      >
        {isEmpty ? (
          <span className="font-mono text-xs italic text-mist-400">null</span>
        ) : isDeleted ? (
          <span className="font-mono text-xs italic text-cell-danger/70">DEL</span>
        ) : (
          value
        )}
      </motion.div>
    </motion.div>
  )
}
