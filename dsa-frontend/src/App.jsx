import { useState } from 'react'
import Home from './pages/Home.jsx'
import ArrayListWorkspace from './modules/arraylist/ArrayListWorkspace.jsx'

// No React Router yet — only one module (arraylist) is wired up. Add
// routing once linkedlist/stack/tree modules come online.
export default function App() {
  const [activeModule, setActiveModule] = useState(null) // null = Home

  if (activeModule === 'arraylist') {
    return <ArrayListWorkspace onBack={() => setActiveModule(null)} />
  }

  return <Home onSelect={setActiveModule} />
}
