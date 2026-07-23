import Button from './Button.jsx'

export default function BackExecuteBar({ onBack, onExecute, isExecuteDisabled, isLoading }) {
  return (
    <div className="flex items-center justify-between border-t border-graphite-600 pt-4">
      <Button variant="ghost" onClick={onBack}>
        ← Back
      </Button>
      <Button variant="primary" onClick={onExecute} disabled={isExecuteDisabled || isLoading}>
        {isLoading ? 'Running…' : 'Execute ▶'}
      </Button>
    </div>
  )
}
