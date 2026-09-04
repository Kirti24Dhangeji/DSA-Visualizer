import { StepType } from '../../../shared/constants/stepTypes.js'

// Same StepType contract as the array-list engine (Step/StepType are shared
// across every traced structure per the backend's engine design). If your
// TracedJLinkedList emits steps that don't fit this mapping cleanly, share
// a sample response and this table can be tuned — see arrayStepVariants.js
// for the reference version built directly off real Postman output.
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

export function isHighlighted(index, highlightedIndices) {
  return Array.isArray(highlightedIndices) && highlightedIndices.includes(index)
}
