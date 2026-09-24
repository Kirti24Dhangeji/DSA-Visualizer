/**
 * The backend snapshot of a JTreeMap is its entries in PREORDER
 * ({ key, value } each) — see TracedJTreeMap#snapshot(). A binary search
 * tree is fully determined by its preorder sequence, so inserting the
 * entries in that order into a plain BST rebuilds exactly the tree the
 * backend holds. `preIndex` is the entry's position in the snapshot, which
 * is what `highlightedIndices` refers to.
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

/**
 * Standard symmetrical BST layout: every parent sits centered exactly
 * between its two children (or offset just enough to show a single child
 * on its side), so the tree always mirrors around its own nodes rather
 * than skewing toward one side. Depth sets y; a bottom-up pass sets x.
 */
export function layoutTree(root, { hGap = 80, vGap = 104, padX = 44, padTop = 40, padBottom = 32 } = {}) {
  if (!root) return { nodes: [], edges: [], width: 0, height: 0 }

  let nextLeafSlot = 0
  let deepestDepth = 0

  // Post-order: children get their x first, then the parent centers over them.
  const place = (node, depth) => {
    if (!node) return null
    deepestDepth = Math.max(deepestDepth, depth)

    const leftX = node.left ? place(node.left, depth + 1) : null
    const rightX = node.right ? place(node.right, depth + 1) : null

    let x
    if (leftX != null && rightX != null) {
      x = (leftX + rightX) / 2
    } else if (leftX != null) {
      x = leftX + hGap / 2 // lone left child: parent sits half a gap to its right
    } else if (rightX != null) {
      x = rightX - hGap / 2 // lone right child: parent sits half a gap to its left
    } else {
      x = nextLeafSlot * hGap
      nextLeafSlot += 1
    }

    node.x = x
    node.y = depth
    return x
  }
  place(root, 0)

  const nodes = []
  const edges = []
  const collect = (node) => {
    if (!node) return
    nodes.push(node)
    if (node.left) {
      edges.push({
        side: 'L',
        parentIndex: node.preIndex,
        childIndex: node.left.preIndex,
        childKey: node.left.key,
        x1: node.x,
        y1: node.y,
        x2: node.left.x,
        y2: node.left.y,
      })
      collect(node.left)
    }
    if (node.right) {
      edges.push({
        side: 'R',
        parentIndex: node.preIndex,
        childIndex: node.right.preIndex,
        childKey: node.right.key,
        x1: node.x,
        y1: node.y,
        x2: node.right.x,
        y2: node.right.y,
      })
      collect(node.right)
    }
  }
  collect(root)

  const minX = Math.min(...nodes.map((n) => n.x))
  const maxX = Math.max(...nodes.map((n) => n.x))
  for (const n of nodes) {
    n.x = n.x - minX + padX
    n.y = padTop + n.y * vGap
  }
  for (const e of edges) {
    e.x1 = e.x1 - minX + padX
    e.x2 = e.x2 - minX + padX
    e.y1 = padTop + e.y1 * vGap
    e.y2 = padTop + e.y2 * vGap
  }

  return {
    nodes,
    edges,
    width: maxX - minX + padX * 2,
    height: padTop + padBottom + deepestDepth * vGap,
  }
}
