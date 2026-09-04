import { useState } from 'react'
import { AnimatePresence } from 'framer-motion'
import ArgumentForm from './ArgumentForm.jsx'

/**
 * Generic operation button grid + inline argument form.
 * `operations` is a module-specific config array of
 * { name, label, args: [{ key, label }] } — see each module's
 * config/operationsConfig.js for the exact list it supports.
 */
export default function OperationPicker({ operations, onQueueOperation }) {
  const [selected, setSelected] = useState(null)

  function handleSubmit(args) {
    onQueueOperation({ name: selected.name, arguments: args })
    setSelected(null)
  }

  return (
    <div>
      <p className="mb-2 font-mono text-[11px] uppercase tracking-wide text-mist-400">
        Supported operations
      </p>
      <div className="flex flex-wrap gap-2">
        {operations.map((op) => (
          <button
            key={op.name}
            onClick={() => setSelected(op)}
            className={`rounded-md border px-3 py-1.5 font-mono text-sm transition-colors
              ${
                selected?.name === op.name
                  ? 'border-cell-shift bg-cell-shift/10 text-cell-shift'
                  : 'border-graphite-600 bg-graphite-800 text-mist-100 hover:border-graphite-500'
              }`}
          >
            {op.label}
          </button>
        ))}
      </div>

      <AnimatePresence>
        {selected && (
          <ArgumentForm
            key={selected.name}
            operation={selected}
            onSubmit={handleSubmit}
            onCancel={() => setSelected(null)}
          />
        )}
      </AnimatePresence>
    </div>
  )
}
