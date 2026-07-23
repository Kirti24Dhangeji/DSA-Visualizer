// Base URL for the Spring Boot backend.
// Empty string = relative path ('/api/...'), routed through Vite's dev
// proxy (see vite.config.js) to sidestep the backend's missing CORS config.
// Set VITE_API_BASE_URL in a .env file if you deploy the backend separately
// (e.g. a production build served apart from Spring Boot) and it *does*
// have CORS enabled for your frontend's origin.
const BASE_URL = import.meta.env.VITE_API_BASE_URL || ''

/**
 * Generic POST wrapper shared by every data-structure module.
 * Each module's own api file (e.g. modules/arraylist/api/arrayListApi.js)
 * calls this with its own endpoint path and { operations } body shape.
 */
export async function executeOperations(endpointPath, operations) {
  const response = await fetch(`${BASE_URL}${endpointPath}`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ operations }),
  })

  if (!response.ok) {
    const message = await response.text().catch(() => response.statusText)
    throw new Error(`Request failed (${response.status}): ${message}`)
  }

  return response.json()
}
