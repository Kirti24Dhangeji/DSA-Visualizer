const VARIANTS = {
  primary:
    'bg-cell-success text-graphite-950 hover:bg-cell-success/90 disabled:bg-graphite-600 disabled:text-mist-400',
  secondary:
    'bg-graphite-700 text-mist-100 hover:bg-graphite-600 border border-graphite-500 disabled:opacity-40',
  ghost:
    'bg-transparent text-mist-300 hover:text-mist-100 hover:bg-graphite-800 disabled:opacity-30',
  danger:
    'bg-transparent text-cell-danger hover:bg-cell-danger/10 disabled:opacity-30',
}

export default function Button({
  children,
  variant = 'secondary',
  className = '',
  ...props
}) {
  return (
    <button
      className={`px-4 py-2 rounded-md font-mono text-sm font-medium tracking-tight
        transition-colors duration-150 disabled:cursor-not-allowed
        ${VARIANTS[variant]} ${className}`}
      {...props}
    >
      {children}
    </button>
  )
}
