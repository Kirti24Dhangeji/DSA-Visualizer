/**
 * Centered-root, 45-degree BST layout.
 * Root sits at x=centerX, y=0. Children extend down at 45° angles.
 * Subtree width is computed bottom-up to prevent overlaps.
 */
export function buildTree(entries) {
  let root = null
  entries.forEach((entry, preIndex) => {
    const node = { key: entry.key, value: entry.value, preIndex, left: null, right: null }
    if (!root) {
      root = node
      return
    }
    let current = root
    for (;;) {
      const side = node.key < current.key ? 'left' : 'right'
      if (!current[side]) {
        current[side] = node
        return
      }
      current = current[side]
    }
  })
  return root
}

export function layoutTree(root, { hGap = 120, vGap = 100, centerX = 400 } = {}) {
  if (!root) return { nodes: [], edges: [], width: 800, height: 400 }

  const nodes = []
  const edges = []

  // Post-order: compute subtree width, then position node
  const place = (node, x, y, depth) => {
    if (!node) return { width: 0, minX: x, maxX: x }

    const leftLayout = place(node.left, x - hGap, y + vGap, depth + 1)
    const rightLayout = place(node.right, x + hGap, y + vGap, depth + 1)

    node.x = x
    node.y = y
    nodes.push(node)

    if (node.left) {
      edges.push({
        parentIndex: node.preIndex,
        childIndex: node.left.preIndex,
        x1: x,
        y1: y,
        x2: node.left.x,
        y2: node.left.y,
      })
    }
    if (node.right) {
      edges.push({
        parentIndex: node.preIndex,
        childIndex: node.right.preIndex,
        x1: x,
        y1: y,
        x2: node.right.x,
        y2: node.right.y,
      })
    }

    const width = Math.max(leftLayout.width + hGap, rightLayout.width + hGap, 80)
    return { width, minX: Math.min(leftLayout.minX, x - width / 2), maxX: Math.max(rightLayout.maxX, x + width / 2) }
  }

  place(root, centerX, 40, 0)

  const minX = Math.min(...nodes.map((n) => n.x)) - 60
  const maxX = Math.max(...nodes.map((n) => n.x)) + 60
  const maxY = Math.max(...nodes.map((n) => n.y)) + 60

  return { nodes, edges, width: Math.max(800, maxX - minX), height: maxY + 40 }
}
