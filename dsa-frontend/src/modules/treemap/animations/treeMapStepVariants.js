import { StepType } from '../../../shared/constants/stepTypes.js'

// TracedJTreeMap emits ADD / SET on insert (SET when the key already
// exists), REMOVE / FAILED on delete, FOUND / FAILED on search — see
// traced/TracedJTreeMap.java.
export const stepStyle = {
  [StepType.ADD]: { color: 'cell-success', badge: 'ADD', ring: true },
  [StepType.SET]: { color: 'cell-shift', badge: 'UPDATED', ring: true },
  [StepType.REMOVE]: { color: 'cell-danger', badge: 'REMOVE', ring: true },
  [StepType.FOUND]: { color: 'cell-success', badge: 'FOUND', ring: true },
  [StepType.FAILED]: { color: 'cell-danger', badge: 'NOT PRESENT', ring: true },
}

export function getStepStyle(stepType) {
  return stepStyle[stepType] ?? { color: 'mist-300', badge: stepType, ring: false }
}
