import Button from './Button.jsx'

export default function PlaybackControls({
  currentStep,
  totalSteps,
  isPlaying,
  speed,
  setSpeed,
  play,
  pause,
  next,
  prev,
  disabled,
}) {
  return (
    <div
      className={`flex flex-col gap-3 rounded-lg border border-graphite-600 bg-graphite-800/60 px-4 py-3
        ${disabled ? 'opacity-40 pointer-events-none' : ''}`}
    >
      <div className="flex items-center justify-between gap-4">
        <div className="flex items-center gap-2">
          <Button variant="ghost" onClick={prev} disabled={disabled || currentStep === 0} aria-label="Previous step">
            ◀
          </Button>
          {isPlaying ? (
            <Button variant="secondary" onClick={pause} disabled={disabled} aria-label="Pause">
              ❚❚ Pause
            </Button>
          ) : (
            <Button variant="primary" onClick={play} disabled={disabled} aria-label="Play">
              ▶ Play
            </Button>
          )}
          <Button
            variant="ghost"
            onClick={next}
            disabled={disabled || currentStep >= totalSteps - 1}
            aria-label="Next step"
          >
            ▶|
          </Button>
        </div>

        <span className="font-mono text-xs text-mist-300 tabular-nums">
          step {totalSteps === 0 ? 0 : currentStep + 1} / {totalSteps}
        </span>
      </div>

      <div className="flex items-center gap-3">
        <span className="font-mono text-xs text-mist-400 w-10">{speed.toFixed(2)}x</span>
        <input
          type="range"
          min="0.25"
          max="3"
          step="0.25"
          value={speed}
          onChange={(e) => setSpeed(Number(e.target.value))}
          disabled={disabled}
          className="w-full accent-cell-shift"
          aria-label="Playback speed"
        />
      </div>
    </div>
  )
}
