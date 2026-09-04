export default function WorkspaceHeader({ moduleNumber, moduleGroup, title }) {
  return (
    <header className="flex items-baseline justify-between">
      <div>
        <p className="font-mono text-xs uppercase tracking-wide text-mist-400">
          module · {moduleGroup}
        </p>
        <h1 className="font-mono text-2xl font-semibold text-mist-100">{title}</h1>
      </div>
      <span className="font-mono text-xs text-mist-400">{moduleNumber} / 6</span>
    </header>
  )
}
