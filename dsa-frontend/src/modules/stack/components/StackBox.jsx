import { motion } from 'framer-motion'

const COLOR_CLASSES = {
  'cell-success': { border: 'border-cell-success', text: 'text-cell-success', glow: 'shadow-[0_0_16px_-2px_#34D399]' },
  'cell-danger': { border: 'border-cell-danger', text: 'text-cell-danger', glow: 'shadow-[0_0_16px_-2px_#F87171]' },
  'cell-shift': { border: 'border-cell-shift', text: 'text-cell-shift', glow: 'shadow-[0_0_16px_-2px_#60A5FA]' },
  'cell-compare': { border: 'border-cell-compare', text: 'text-cell-compare', glow: 'shadow-[0_0_16px_-2px_#F0B429]' },
  'cell-swap': { border: 'border-cell-swap', text: 'text-cell-swap', glow: 'shadow-[0_0_16px_-2px_#A78BFA]' },
}

export default function StackBox({ value, index, active, colorToken, isTop }) {
  const colors = active ? COLOR_CLASSES[colorToken] ?? COLOR_CLASSES['cell-shift'] : null

  return (
    <motion.div
      layout
      layoutId={`stack-${index}`}
      initial={{ opacity: 0, y: -24, scale: 0.85 }}
      animate={{ opacity: 1, y: 0, scale: 1 }}
      exit={{ opacity: 0, y: -24, scale: 0.85 }}
      transition={{ type: 'spring', stiffness: 500, damping: 32 }}
      className="flex items-center gap-3"
    >
      <span
        className={`w-8 text-right font-mono text-[11px] font-bold uppercase tracking-wide
          ${isTop ? 'text-cell-shift' : 'text-transparent'}`}
      >
        {isTop ? 'top' : '·'}
      </span>

      <motion.div
        animate={{ scale: active ? 1.05 : 1 }}
        transition={{ duration: 0.25 }}
        className={`flex h-14 w-40 items-center justify-center rounded-md border-2
          bg-graphite-800 font-mono text-xl font-semibold tabular-nums
          ${active ? `${colors.border} ${colors.text} ${colors.glow}` : 'border-graphite-600 text-mist-100'}
          ${isTop ? 'border-t-4' : ''}`}
      >
        {value}
      </motion.div>

      <span className="w-8 font-mono text-[11px] text-mist-400 tabular-nums">[{index}]</span>
    </motion.div>
  )
}
