// Mirrors JHashSetController's switch cases exactly.
export const hashSetOperations = [
  { name: 'insert', label: 'Insert', args: [{ key: 'value', label: 'Value' }] },
  { name: 'delete', label: 'Delete', args: [{ key: 'value', label: 'Value' }] },
  { name: 'search', label: 'Search', args: [{ key: 'value', label: 'Value' }] },
]

export function findOperation(name) {
  return hashSetOperations.find((op) => op.name === name)
}
