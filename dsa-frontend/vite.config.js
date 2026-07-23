import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  plugins: [react()],
  server: {
    host: true,
    port: 5173,
    // The Spring Boot backend has no CORS configuration, so route API
    // calls through Vite's dev-server proxy instead of hitting it
    // cross-origin from the browser. The frontend calls relative
    // '/api/...' paths; Vite forwards them to Spring Boot server-side.
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
    },
  },
})
