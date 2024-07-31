import { Fragment, useState } from 'react'
import './App.css'
import MyAppBar from './components/MyAppBar'
import ResponsiveDrawer from './components/ResponsiveDrawer'

function App() {

  return (
    <Fragment>
      <ResponsiveDrawer />
      <MyAppBar title="Hello, World!" />
    </Fragment>
  )
}

export default App
