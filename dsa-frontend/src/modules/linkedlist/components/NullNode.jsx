export default function NullNode() {
  return (
    <div className="flex flex-col items-center gap-1.5">
      <span className="font-mono text-[11px] text-transparent select-none">·</span>
      <div className="flex h-16 w-16 items-center justify-center rounded-md border-2 border-dashed border-graphite-500">
        <span className="font-mono text-xs italic text-mist-400">null</span>
      </div>
    </div>
  )
}
