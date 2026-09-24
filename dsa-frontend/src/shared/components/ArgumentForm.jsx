import { useState } from 'react'
import { motion } from 'framer-motion'
import Button from './Button.jsx'

// An arg is a number field unless its config says otherwise (e.g. JTreeMap's
// value, which the backend stores as a String even when it looks numeric).
const isTextArg = (arg) => arg.type === 'text'

function isValidValue(arg, raw) {
  if (isTextArg(arg)) return raw.trim() !== ''
  return raw.trim() !== '' && !Number.isNaN(Number(raw))
}

export default function ArgumentForm({ operation, onSubmit, onCancel }) {
  const [values, setValues] = useState(() => operation.args.map(() => ''))

  const isValid = operation.args.every((arg, i) => isValidValue(arg, values[i] ?? ''))

  function handleChange(i, raw) {
    setValues((prev) => prev.map((v, idx) => (idx === i ? raw : v)))
  }

  function handleSubmit(e) {
    e.preventDefault()
    if (!isValid) return
    onSubmit(values.map((v, i) => (isTextArg(operation.args[i]) ? v.trim() : Number(v))))
  }

  return (
    <motion.form
      initial={{ opacity: 0, height: 0 }}
      animate={{ opacity: 1, height: 'auto' }}
      exit={{ opacity: 0, height: 0 }}
      transition={{ duration: 0.18 }}
      onSubmit={handleSubmit}
      className="overflow-hidden"
    >
      <div className="mt-3 flex flex-wrap items-end gap-3 rounded-lg border border-graphite-600 bg-graphite-800 p-4">
        {operation.args.length === 0 ? (
          <p className="font-mono text-xs text-mist-300">
            {operation.label} needs no arguments — add it straight to the queue.
          </p>
        ) : (
          operation.args.map((arg, i) => (
            <label key={arg.key} className="flex flex-col gap-1">
              <span className="font-mono text-[11px] uppercase tracking-wide text-mist-400">
                {arg.label}
              </span>
              <input
                type={isTextArg(arg) ? 'text' : 'number'}
                value={values[i]}
                onChange={(e) => handleChange(i, e.target.value)}
                autoFocus={i === 0}
                className={`rounded-md border border-graphite-500 bg-graphite-900 px-2.5 py-1.5
                  font-mono text-sm text-mist-100 outline-none focus-visible:border-cell-shift
                  ${isTextArg(arg) ? 'w-36' : 'w-24'}`}
                placeholder={arg.placeholder ?? (isTextArg(arg) ? 'text' : '0')}
              />
            </label>
          ))
        )}

        <div className="ml-auto flex gap-2">
          <Button type="button" variant="ghost" onClick={onCancel}>
            Cancel
          </Button>
          <Button type="submit" variant="primary" disabled={!isValid}>
            Add to queue
          </Button>
        </div>
      </div>
    </motion.form>
  )
}
