import { useState } from 'react'
import Home from './pages/Home.jsx'
import ArrayListWorkspace from './modules/arraylist/ArrayListWorkspace.jsx'
import LinkedListWorkspace from './modules/linkedlist/LinkedListWorkspace.jsx'
import StackWorkspace from './modules/stack/StackWorkspace.jsx'
import QueueWorkspace from './modules/queue/QueueWorkspace.jsx'

// No React Router yet — routes between Home and a structure's workspace via
// simple state. Add real routing once every module (tree, graph) is ready.
export default function App() {
  const [activeModule, setActiveModule] = useState(null) // null = Home

  const back = () => setActiveModule(null)

  switch (activeModule) {
    case 'arraylist':
      return <ArrayListWorkspace onBack={back} />
    case 'linkedlist':
      return <LinkedListWorkspace onBack={back} />
    case 'stack':
      return <StackWorkspace onBack={back} />
    case 'queue':
      return <QueueWorkspace onBack={back} />
    default:
      return <Home onSelect={setActiveModule} />
  }
}
