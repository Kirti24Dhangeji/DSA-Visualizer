import { executeOperations } from '../../../shared/api/client.js'

const ENDPOINT = '/api/hashset/execute'

/**
 * @param {{name: string, arguments: number[]}[]} operations
 * @returns {Promise<{steps: object[], totalSteps: number}>}
 */
export function executeHashSetOperations(operations) {
  return executeOperations(ENDPOINT, operations)
}
