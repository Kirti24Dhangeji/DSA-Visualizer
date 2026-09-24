import { useEffect, useMemo, useRef } from 'react'
import { motion } from 'framer-motion'
import { buildTree, layoutTree } from '../utils/layoutTree.js'

const COLOR_CLASSES = {
  'cell-success': { stroke: '#34D399', border: 'border-cell-success', text: 'text-cell-success', glow: 'shadow-[0_0_16px_-2px_#34D399]' },
  'cell-danger': { stroke: '#F87171', border: 'border-cell-danger', text: 'text-cell-danger', glow: 'shadow-[0_0_16px_-2px_#F87171]' },
  'cell-shift': { stroke: '#60A5FA', border: 'border-cell-shift', text: 'text-cell-shift', glow: 'shadow-[0_0_16px_-2px_#60A5FA]' },
  'cell-compare': { stroke: '#F0B429', border: 'border-cell-compare', text: 'text-cell-compare', glow: 'shadow-[0_0_16px_-2px_#F0B429]' },
  'cell-swap': { stroke: '#A78BFA', border: 'border-cell-swap', text: 'text-cell-swap', glow: 'shadow-[0_0_16px_-2px_#A78BFA]' },
}
const IDLE_STROKE = '#3A4557' // graphite-500, matches other modules' idle-edge tone
const NODE = 56 // node box side, in px
const MOVE = { type: 'spring', stiffness: 200, damping: 24 }

/**
 * Standard binary-search-tree drawing: nodes as boxes matching the rest of
 * the app's "cell" style, edges as lines, each edge labelled L or R by the
 * side it descends on so the parent → child link is unambiguous.
 */
export default function TreeCanvas({ step, colorToken }) {
  const entries = step?.snapshot ?? []
  const { nodes, edges, width, height } = useMemo(() => layoutTree(buildTree(entries)), [entries])

  const path = step?.highlightedIndices?.filter((i) => i >= 0) ?? []
  const onPath = new Set(path)
  const lastOnPath = path.length ? path[path.length - 1] : -1

  // REMOVE is recorded twice by TracedJTreeMap: once with the target still in
  // the tree (mark it solid), once right after (the path now ends at its
  // parent, which must stay a path node, not the solid "target" node).
  const marksTarget = step?.stepType !== 'REMOVE' || path.length > 0
  const colors = COLOR_CLASSES[colorToken] ?? COLOR_CLASSES['cell-shift']

  // Keep the node the current step is about in view when the tree is wide.
  const targetRef = useRef(null)
  useEffect(() => {
    targetRef.current?.scrollIntoView?.({ block: 'nearest', inline: 'center', behavior: 'smooth' })
  }, [step])

  if (nodes.length === 0) {
    return <p className="font-mono text-sm text-mist-400">empty — queue an operation and hit Execute to begin</p>
  }

  return (
    <div className="w-full overflow-x-auto">
      <div className="relative mx-auto" style={{ width, height }}>
        <svg width={width} height={height} className="absolute inset-0">
          {edges.map((e) => {
            const lit = onPath.has(e.parentIndex) && onPath.has(e.childIndex)
            const midX = (e.x1 + e.x2) / 2
            const midY = (e.y1 + e.y2) / 2
            return (
              <g key={`${e.parentIndex}-${e.childIndex}`}>
                <motion.line
                  initial={false}
                  animate={{ x1: e.x1, y1: e.y1 + NODE / 2, x2: e.x2, y2: e.y2 - NODE / 2 }}
                  transition={MOVE}
                  stroke={lit ? colors.stroke : IDLE_STROKE}
                  strokeWidth={lit ? 3 : 1.75}
                  strokeLinecap="round"
                />
                {/* L / R label — which side this child hangs from its parent */}
                <motion.g initial={false} animate={{ x: midX, y: midY }} transition={MOVE}>
                  <circle r={8} fill="#121822" stroke={lit ? colors.stroke : '#26303F'} strokeWidth={1} />
                  <text
                    textAnchor="middle"
                    dominantBaseline="central"
                    fontSize="9"
                    fontFamily="ui-monospace, monospace"
                    fontWeight="700"
                    fill={lit ? colors.stroke : '#6B7785'}
                  >
                    {e.side}
                  </text>
                </motion.g>
              </g>
            )
          })}
        </svg>

        {nodes.map((n) => {
          const isOnPath = onPath.has(n.preIndex)
          const isTarget = marksTarget && n.preIndex === lastOnPath
          return (
            <motion.div
              key={n.key}
              ref={isTarget ? targetRef : undefined}
              initial={false}
              animate={{ x: n.x - NODE / 2, y: n.y - NODE / 2, scale: isTarget ? 1.08 : 1 }}
              transition={MOVE}
              className="absolute flex flex-col items-center gap-1"
              style={{ width: NODE }}
            >
              <div
                className={`flex items-center justify-center rounded-md border-2 bg-graphite-800
                  font-mono text-lg font-semibold tabular-nums
                  ${
                    isTarget
                      ? `${colors.border} ${colors.text} ${colors.glow}`
                      : isOnPath
                        ? `${colors.border} ${colors.text}`
                        : 'border-graphite-600 text-mist-100'
                  }`}
                style={{ height: NODE, width: NODE }}
              >
                {n.key}
              </div>
              <span className="max-w-[4.5rem] truncate font-mono text-[11px] text-mist-400" title={String(n.value)}>
                {n.value}
              </span>
            </motion.div>
          )
        })}
      </div>
    </div>
  )
}
