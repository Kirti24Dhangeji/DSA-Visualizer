import { useState } from 'react'
import OutputWindow from './components/OutputWindow.jsx'
import OperationPicker from './components/OperationPicker.jsx'
import { executeArrayListOperations } from './api/arrayListApi.js'
import PlaybackControls from '../../shared/components/PlaybackControls.jsx'
import OperationQueueList from '../../shared/components/OperationQueueList.jsx'
import BackExecuteBar from '../../shared/components/BackExecuteBar.jsx'
import { useStepPlayer } from '../../shared/hooks/useStepPlayer.js'

export default function ArrayListWorkspace({ onBack }) {
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
      const response = await executeArrayListOperations(queue)
      setTrace(response)
      player.reset(true)
    } catch (err) {
      setError(err.message ?? 'Something went wrong talking to the backend.')
    } finally {
      setIsLoading(false)
    }
  }

  return (
    <div className="mx-auto flex max-w-3xl flex-col gap-6 px-6 py-10">
      <header className="flex items-baseline justify-between">
        <div>
          <p className="font-mono text-xs uppercase tracking-wide text-mist-400">
            module · custom-collections
          </p>
          <h1 className="font-mono text-2xl font-semibold text-mist-100">JArrayList</h1>
        </div>
        <span className="font-mono text-xs text-mist-400">0x{'00'}–0x{'FF'}</span>
      </header>

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

      <OperationPicker onQueueOperation={addToQueue} />

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
