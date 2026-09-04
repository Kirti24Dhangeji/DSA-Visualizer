// Mirrors JLinkedListController's switch cases exactly.
export const linkedListOperations = [
  { name: 'add', label: 'Add', args: [{ key: 'value', label: 'Value' }] },
  { name: 'addFirst', label: 'Add First', args: [{ key: 'value', label: 'Value' }] },
  {
    name: 'set',
    label: 'Set',
    args: [
      { key: 'index', label: 'Index' },
      { key: 'value', label: 'Value' },
    ],
  },
  { name: 'get', label: 'Get', args: [{ key: 'index', label: 'Index' }] },
  { name: 'remove', label: 'Remove', args: [{ key: 'index', label: 'Index' }] },
  { name: 'linearSearch', label: 'Linear Search', args: [{ key: 'target', label: 'Target' }] },
  { name: 'reverse', label: 'Reverse', args: [] },
  { name: 'insertionSort', label: 'Insertion Sort', args: [] },
  { name: 'middleNode', label: 'Middle Node', args: [] },
]
