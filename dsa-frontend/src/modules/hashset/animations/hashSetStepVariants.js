import { StepType } from '../../../shared/constants/stepTypes.js'

// Same StepType contract as every other traced structure. TracedJHashSet
// emits ADD / EXISTING on insert, REMOVE / FAILED on delete, GET / FAILED
// on search — see traced/TracedJHashSet.java.
export const stepStyle = {
  [StepType.ADD]: { color: 'cell-success', badge: 'ADD', ring: true },
  [StepType.EXISTING]: { color: 'cell-compare', badge: 'ALREADY EXISTS', ring: true },
  [StepType.REMOVE]: { color: 'cell-danger', badge: 'REMOVE', ring: true },
  [StepType.GET]: { color: 'cell-success', badge: 'FOUND', ring: true },
  [StepType.FAILED]: { color: 'cell-danger', badge: 'NOT PRESENT', ring: true },
}

export function getStepStyle(stepType) {
  return stepStyle[stepType] ?? { color: 'mist-300', badge: stepType, ring: false }
}

export function isHighlighted(index, highlightedIndices) {
  return Array.isArray(highlightedIndices) && highlightedIndices.includes(index)
}
