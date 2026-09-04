import { StepType } from '../../../shared/constants/stepTypes.js'

// TracedJQueue is built on TracedJLinkedList per the backend design, so it
// shares the same Step/StepType contract. Enqueue/dequeue/peek are expected
// to surface as ADD/REMOVE/GET-flavoured steps. Tune this against a real
// sample response if the actual output differs.
export const stepStyle = {
  [StepType.ADD]: { color: 'cell-success', badge: 'ENQUEUE', ring: true },
  [StepType.REMOVE]: { color: 'cell-danger', badge: 'DEQUEUE', ring: true },
  [StepType.SET]: { color: 'cell-shift', badge: 'SET', ring: true },
  [StepType.GET]: { color: 'cell-shift', badge: 'PEEK', ring: true },
  [StepType.COMPARE]: { color: 'cell-compare', badge: 'COMPARE', ring: false },
  [StepType.SHIFT]: { color: 'cell-shift', badge: 'SHIFT', ring: false },
  [StepType.FOUND]: { color: 'cell-success', badge: 'FOUND', ring: true },
  [StepType.NOT_FOUND]: { color: 'cell-danger', badge: 'EMPTY', ring: true },
  [StepType.SWAP]: { color: 'cell-swap', badge: 'SWAP', ring: true },
  [StepType.SORT]: { color: 'cell-success', badge: 'DONE', ring: true },
}

export function getStepStyle(stepType) {
  return stepStyle[stepType] ?? { color: 'mist-300', badge: stepType, ring: false }
}

export function isHighlighted(index, highlightedIndices) {
  return Array.isArray(highlightedIndices) && highlightedIndices.includes(index)
}
