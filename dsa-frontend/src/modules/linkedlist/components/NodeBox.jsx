import { motion } from 'framer-motion'

const COLOR_CLASSES = {
  'cell-success': { border: 'border-cell-success', text: 'text-cell-success', glow: 'shadow-[0_0_16px_-2px_#34D399]' },
  'cell-danger': { border: 'border-cell-danger', text: 'text-cell-danger', glow: 'shadow-[0_0_16px_-2px_#F87171]' },
  'cell-shift': { border: 'border-cell-shift', text: 'text-cell-shift', glow: 'shadow-[0_0_16px_-2px_#60A5FA]' },
  'cell-compare': { border: 'border-cell-compare', text: 'text-cell-compare', glow: 'shadow-[0_0_16px_-2px_#F0B429]' },
  'cell-swap': { border: 'border-cell-swap', text: 'text-cell-swap', glow: 'shadow-[0_0_16px_-2px_#A78BFA]' },
}

export default function NodeBox({ value, index, active, colorToken }) {
  const colors = active ? COLOR_CLASSES[colorToken] ?? COLOR_CLASSES['cell-shift'] : null

  return (
    <motion.div
      layout
      layoutId={`node-${index}`}
      initial={{ opacity: 0, scale: 0.6 }}
      animate={{ opacity: 1, scale: 1 }}
      exit={{ opacity: 0, scale: 0.6 }}
      transition={{ type: 'spring', stiffness: 500, damping: 32 }}
      className="flex flex-col items-center gap-1.5"
    >
      <span className="font-mono text-[11px] text-mist-400 tabular-nums">node {index}</span>

      <motion.div
        animate={{ scale: active ? 1.08 : 1 }}
        transition={{ duration: 0.25 }}
        className={`flex h-16 w-16 items-center justify-center rounded-md border-2
          bg-graphite-800 font-mono text-xl font-semibold tabular-nums
          ${active ? `${colors.border} ${colors.text} ${colors.glow}` : 'border-graphite-600 text-mist-100'}`}
      >
        {value}
      </motion.div>
    </motion.div>
  )
}
