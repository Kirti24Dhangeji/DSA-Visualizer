import { StepType } from '../../../shared/constants/stepTypes.js'

// Maps each StepType to a semantic color + short badge label used by
// OutputWindow / ArrayBox. Colors reference the cell.* tokens defined in
// tailwind.config.js so meaning stays consistent across the whole app.
export const stepStyle = {
  [StepType.ADD]: { color: 'cell-success', badge: 'ADD', ring: true },
  [StepType.REMOVE]: { color: 'cell-danger', badge: 'REMOVE', ring: true },
  [StepType.SET]: { color: 'cell-shift', badge: 'SET', ring: true },
  [StepType.GET]: { color: 'cell-shift', badge: 'GET', ring: false },
  [StepType.COMPARE]: { color: 'cell-compare', badge: 'COMPARE', ring: false },
  [StepType.SHIFT]: { color: 'cell-shift', badge: 'SHIFT', ring: false },
  [StepType.FOUND]: { color: 'cell-success', badge: 'FOUND', ring: true },
  [StepType.NOT_FOUND]: { color: 'cell-danger', badge: 'NOT FOUND', ring: true },
  [StepType.SWAP]: { color: 'cell-swap', badge: 'SWAP', ring: true },
  [StepType.SORT]: { color: 'cell-success', badge: 'SORTED', ring: true },
}

export function getStepStyle(stepType) {
  return stepStyle[stepType] ?? { color: 'mist-300', badge: stepType, ring: false }
}

// Whether a box index should be treated as "active" for the current step.
export function isHighlighted(index, highlightedIndices) {
  return Array.isArray(highlightedIndices) && highlightedIndices.includes(index)
}
