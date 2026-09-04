import { executeOperations } from '../../../shared/api/client.js'

const ENDPOINT = '/api/queue/execute'

export function executeQueueOperations(operations) {
  return executeOperations(ENDPOINT, operations)
}
