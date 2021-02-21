import React from 'react'
import { render, screen } from '@testing-library/react'
import App from './App'
import { BusLine } from './types/bus-line'
import { BuslineTable } from './components/BuslineTable'
import { StopsList } from './components/StopsList'
import { BusStop } from './types/bus-stop'

test('renders main header', () => {
   render(<App />)
   const headerElement = screen.getByText(/Long Line Explorer/i)
   expect(headerElement).toBeInTheDocument()
   expect(headerElement.tagName).toBe('H1')
})

test('renders busline table', () => {
   render(<App />)
   const tableElement = screen.getByTestId('BuslineTable')
   expect(tableElement).toBeInTheDocument()
   expect(tableElement.tagName).toBe('TABLE')
})

test('render busline table header', () => {
   render(<App />)
   const headerTableNumberOfStops = screen.getByText(/Number of stops/i)
   expect(headerTableNumberOfStops).toBeInTheDocument()
   expect(headerTableNumberOfStops.tagName).toBe('TH')
})

test('render busline table header', () => {
   const busLines: Array<BusLine> = []
   busLines.push({
      lineNumber: 27,
      nrOfStops: 4,
      stops: [],
   })
   busLines.push({
      lineNumber: 22,
      nrOfStops: 4,
      stops: [],
   })
   busLines.push({
      lineNumber: 33,
      nrOfStops: 4,
      stops: [],
   })

   render(<BuslineTable busLines={busLines} onBuslineSelected={() => 27} />)

   const tableElement = screen.getByTestId('BuslineTable')
   expect(tableElement).toBeInTheDocument()
   const rows = tableElement.getElementsByTagName('tr')

   // One for each added busline (3) and one row for the headers
   expect(rows).toHaveLength(busLines.length + 1)
})

test('render list of stops', () => {
   const busStops: Array<BusStop> = []
   busStops.push({
      id: 27,
      name: 'The Stop',
   })
   busStops.push({
      id: 27,
      name: 'The Other Stop',
   })
   busStops.push({
      id: 27,
      name: 'The Third Stop',
   })

   render(<StopsList lineName={700} busStops={busStops} />)

   const headerElement = screen.getByText(/Stops for bus 700/i)
   expect(headerElement).toBeInTheDocument()

   busStops.forEach((b) => {
      const regex = new RegExp(b.name, 'i')
      const el = screen.getByText(regex)
      expect(el).toBeInTheDocument()
   })
})
