import { useState } from 'react'
import OutputWindow from './components/OutputWindow.jsx'
import { stackOperations } from './config/operationsConfig.js'
import { executeStackOperations } from './api/stackApi.js'
import OperationPicker from '../../shared/components/OperationPicker.jsx'
import PlaybackControls from '../../shared/components/PlaybackControls.jsx'
import OperationQueueList from '../../shared/components/OperationQueueList.jsx'
import BackExecuteBar from '../../shared/components/BackExecuteBar.jsx'
import WorkspaceHeader from '../../shared/components/WorkspaceHeader.jsx'
import { useStepPlayer } from '../../shared/hooks/useStepPlayer.js'

export default function StackWorkspace({ onBack }) {
  const [queue, setQueue] = useState([])
  const [trace, setTrace] = useState(null)
  const [isLoading, setIsLoading] = useState(false)
  const [error, setError] = useState(null)

  const player = useStepPlayer(trace?.steps ?? null)
  const currentStepData = trace?.steps?.[player.currentStep] ?? null

  function addToQueue(op) {
    setQueue((prev) => [...prev, op])
  }

  function removeFromQueue(index) {
    setQueue((prev) => prev.filter((_, i) => i !== index))
  }

  async function handleExecute() {
    setError(null)
    setIsLoading(true)
    try {
      const response = await executeStackOperations(queue)
      setTrace(response)
      player.reset(true)
    } catch (err) {
      setError(err.message ?? 'Something went wrong talking to the backend.')
    } finally {
      setIsLoading(false)
    }
  }

  return (
    <div className="mx-auto flex max-w-6xl flex-col gap-6 px-4 py-10 sm:px-8">
      <WorkspaceHeader moduleNumber={3} moduleGroup="custom-collections" title="JStack" />

      <OutputWindow step={currentStepData} />

      <PlaybackControls
        currentStep={player.currentStep}
        totalSteps={player.totalSteps}
        isPlaying={player.isPlaying}
        speed={player.speed}
        setSpeed={player.setSpeed}
        play={player.play}
        pause={player.pause}
        next={player.next}
        prev={player.prev}
        disabled={!trace}
      />

      <section className="flex flex-col gap-2">
        <p className="font-mono text-[11px] uppercase tracking-wide text-mist-400">
          Operation queue
        </p>
        <OperationQueueList queue={queue} onRemove={removeFromQueue} />
      </section>

      <OperationPicker operations={stackOperations} onQueueOperation={addToQueue} />

      {error && (
        <p className="rounded-md border border-cell-danger/40 bg-cell-danger/10 px-3 py-2 font-mono text-xs text-cell-danger">
          {error}
        </p>
      )}

      <BackExecuteBar
        onBack={onBack}
        onExecute={handleExecute}
        isExecuteDisabled={queue.length === 0}
        isLoading={isLoading}
      />
    </div>
  )
}
