import { useEffect, useState } from 'react'
import './App.css'

function App() {
  const [testMessage, SetTestMessage] = useState();

  useEffect(() => {
    async function testApi() {
      const response = await fetch('/api/v1/documents')
      const data = await response.json();
      return data;
    }
    SetTestMessage(testApi());
  }, [])

  return (
    <>
      <p>{testMessage}</p>
    </>
  )
}

export default App
