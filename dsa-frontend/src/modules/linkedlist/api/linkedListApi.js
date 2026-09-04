import { executeOperations } from '../../../shared/api/client.js'

const ENDPOINT = '/api/linkedlist/execute'

export function executeLinkedListOperations(operations) {
  return executeOperations(ENDPOINT, operations)
}
