import React from 'react'
import styled from 'styled-components'
import { BuslineView } from './components/BuslineView'

const Header = styled.h1`
   text-align: center;
   padding-top: 0.5em;
`

const Container = styled.div``

const App = (): JSX.Element => {
   return (
      <Container>
         <Header>Long Line Explorer</Header>
         <BuslineView />
      </Container>
   )
}

export default App
