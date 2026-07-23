import { motion, AnimatePresence } from 'framer-motion'

/**
 * Renders the queued operations built by the user before hitting Execute.
 * Each item shape: { name: string, arguments: number[] }
 */
export default function OperationQueueList({ queue, onRemove }) {
  if (queue.length === 0) {
    return (
      <div className="rounded-lg border border-dashed border-graphite-600 px-4 py-6 text-center">
        <p className="font-mono text-sm text-mist-400">
          queue is empty — pick an operation below to add one
        </p>
      </div>
    )
  }

  return (
    <ol className="flex flex-col gap-2">
      <AnimatePresence initial={false}>
        {queue.map((op, index) => (
          <motion.li
            key={`${op.name}-${index}-${op.arguments.join(',')}`}
            layout
            initial={{ opacity: 0, x: -12 }}
            animate={{ opacity: 1, x: 0 }}
            exit={{ opacity: 0, x: 12 }}
            transition={{ duration: 0.18 }}
            className="flex items-center justify-between rounded-md border border-graphite-600
              bg-graphite-800 px-3 py-2 font-mono text-sm"
          >
            <span className="text-mist-100">
              <span className="text-mist-400">{String(index + 1).padStart(2, '0')}.</span>{' '}
              <span className="text-cell-shift">{op.name}</span>
              <span className="text-mist-300">({op.arguments.join(', ')})</span>
            </span>
            <button
              onClick={() => onRemove(index)}
              className="text-mist-400 hover:text-cell-danger transition-colors px-2"
              aria-label={`Remove ${op.name} from queue`}
            >
              ✕
            </button>
          </motion.li>
        ))}
      </AnimatePresence>
    </ol>
  )
}
