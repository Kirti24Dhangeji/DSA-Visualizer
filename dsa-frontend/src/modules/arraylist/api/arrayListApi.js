import { executeOperations } from '../../../shared/api/client.js'

const ENDPOINT = '/api/arraylist/execute'

/**
 * @param {{name: string, arguments: number[]}[]} operations
 * @returns {Promise<{steps: object[], totalSteps: number}>}
 */
export function executeArrayListOperations(operations) {
  return executeOperations(ENDPOINT, operations)
}
