import { motion } from 'framer-motion'

const STRUCTURES = [
  { id: 'arraylist', label: 'Array', hex: '0x01', ready: true },
  { id: 'linkedlist', label: 'Linked List', hex: '0x02', ready: false },
  { id: 'stack', label: 'Stack', hex: '0x03', ready: false },
  { id: 'queue', label: 'Queue', hex: '0x04', ready: false },
  { id: 'tree', label: 'Binary Tree', hex: '0x05', ready: false },
  { id: 'graph', label: 'Graph', hex: '0x06', ready: false },
]

export default function Home({ onSelect }) {
  return (
    <div className="mx-auto flex max-w-3xl flex-col gap-10 px-6 py-16">
      <header className="flex flex-col gap-3">
        <p className="font-mono text-xs uppercase tracking-widest text-cell-success">
          dsa-visualiser · step engine
        </p>
        <h1 className="font-mono text-4xl font-bold leading-tight text-mist-100">
          Watch your data structures<br />think, step by step.
        </h1>
        <p className="max-w-lg font-mono text-sm leading-relaxed text-mist-300">
          Pick a structure below, queue up the operations you want to run, and
          the traced execution engine plays back every comparison, shift, and
          swap as an animation.
        </p>
      </header>

      <section>
        <p className="mb-3 font-mono text-[11px] uppercase tracking-wide text-mist-400">
          Data structures
        </p>
        <div className="grid grid-cols-2 gap-3 sm:grid-cols-3">
          {STRUCTURES.map((s, i) => (
            <motion.button
              key={s.id}
              disabled={!s.ready}
              onClick={() => s.ready && onSelect(s.id)}
              initial={{ opacity: 0, y: 8 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ delay: i * 0.05, duration: 0.3 }}
              whileHover={s.ready ? { y: -2 } : undefined}
              className={`group relative flex flex-col items-start gap-3 rounded-lg border p-4 text-left
                transition-colors
                ${
                  s.ready
                    ? 'border-graphite-600 bg-graphite-800 hover:border-cell-success/60 cursor-pointer'
                    : 'border-graphite-700 bg-graphite-800/40 cursor-not-allowed'
                }`}
            >
              <span className="font-mono text-[11px] text-mist-400">{s.hex}</span>
              <span
                className={`font-mono text-base font-semibold ${
                  s.ready ? 'text-mist-100' : 'text-mist-400'
                }`}
              >
                {s.label}
              </span>
              {!s.ready && (
                <span className="font-mono text-[10px] uppercase tracking-wide text-mist-400">
                  coming soon
                </span>
              )}
              {s.ready && (
                <span className="absolute right-3 top-3 h-1.5 w-1.5 rounded-full bg-cell-success" />
              )}
            </motion.button>
          ))}
        </div>
      </section>
    </div>
  )
}
