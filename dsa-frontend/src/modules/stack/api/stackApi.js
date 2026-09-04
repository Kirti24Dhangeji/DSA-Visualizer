import { executeOperations } from '../../../shared/api/client.js'

const ENDPOINT = '/api/stack/execute'

export function executeStackOperations(operations) {
  return executeOperations(ENDPOINT, operations)
}
