import { executeOperations } from '../../../shared/api/client.js'

const ENDPOINT = '/api/treemap/execute'

/**
 * @param {{name: string, arguments: (number|string)[]}[]} operations
 * @returns {Promise<{steps: object[], totalSteps: number}>}
 */
export function executeTreeMapOperations(operations) {
  return executeOperations(ENDPOINT, operations)
}
