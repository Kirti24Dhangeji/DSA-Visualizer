/** @type {import('tailwindcss').Config} */
export default {
  content: ['./index.html', './src/**/*.{js,jsx}'],
  theme: {
    extend: {
      colors: {
        // Base "systems console" palette
        graphite: {
          950: '#080B0F',
          900: '#0B0F14',
          800: '#121822',
          700: '#1A2230',
          600: '#26303F',
          500: '#3A4557',
        },
        mist: {
          400: '#6B7785',
          300: '#8B96A3',
          100: '#E6EDF3',
        },
        // Semantic step colors — each StepType maps to exactly one of these
        cell: {
          idle: '#1A2230',
          compare: '#F0B429',   // amber — COMPARE
          swap: '#A78BFA',      // violet — SWAP
          shift: '#60A5FA',     // blue — SHIFT, SET, GET
          success: '#34D399',   // green — ADD, FOUND, SORT
          danger: '#F87171',    // red — REMOVE, NOT_FOUND
        },
      },
      fontFamily: {
        mono: ['"IBM Plex Mono"', 'ui-monospace', 'SFMono-Regular', 'monospace'],
        sans: ['Inter', 'ui-sans-serif', 'system-ui', 'sans-serif'],
      },
      backgroundImage: {
        'grid-paper':
          'linear-gradient(to right, rgba(255,255,255,0.035) 1px, transparent 1px), linear-gradient(to bottom, rgba(255,255,255,0.035) 1px, transparent 1px)',
      },
      backgroundSize: {
        'grid-paper': '28px 28px',
      },
    },
  },
  plugins: [],
}
