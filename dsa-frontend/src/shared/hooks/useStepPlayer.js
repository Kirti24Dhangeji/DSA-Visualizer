import { useEffect, useRef, useState } from 'react'

/**
 * Drives playback through any steps[] array returned by the backend.
 * Structure-agnostic — doesn't know what a Step "means", just advances
 * an index on a timer. Works for arrays, linked lists, trees, whatever.
 *
 * @param {Array} steps - the trace's steps array (or null before Execute)
 * @param {number} baseIntervalMs - ms per step at 1x speed
 */
export function useStepPlayer(steps, baseIntervalMs = 900) {
  const [currentStep, setCurrentStep] = useState(0)
  const [isPlaying, setIsPlaying] = useState(false)
  const [speed, setSpeed] = useState(1)
  const intervalRef = useRef(null)

  const totalSteps = steps?.length ?? 0
  const isAtEnd = totalSteps === 0 || currentStep >= totalSteps - 1

  useEffect(() => {
    if (!isPlaying || isAtEnd) {
      setIsPlaying(false)
      return
    }

    intervalRef.current = setInterval(() => {
      setCurrentStep((prev) => {
        if (prev >= totalSteps - 1) {
          clearInterval(intervalRef.current)
          return prev
        }
        return prev + 1
      })
    }, baseIntervalMs / speed)

    return () => clearInterval(intervalRef.current)
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [isPlaying, speed, totalSteps, baseIntervalMs])

  function play() {
    if (!isAtEnd) setIsPlaying(true)
  }
  function pause() {
    setIsPlaying(false)
  }
  function next() {
    setIsPlaying(false)
    setCurrentStep((prev) => Math.min(prev + 1, Math.max(totalSteps - 1, 0)))
  }
  function prev() {
    setIsPlaying(false)
    setCurrentStep((p) => Math.max(p - 1, 0))
  }
  function reset(autoplay = true) {
    setCurrentStep(0)
    setIsPlaying(autoplay && totalSteps > 1)
  }

  return {
    currentStep,
    totalSteps,
    isPlaying,
    isAtEnd,
    speed,
    setSpeed,
    play,
    pause,
    next,
    prev,
    reset,
    setCurrentStep,
  }
}
