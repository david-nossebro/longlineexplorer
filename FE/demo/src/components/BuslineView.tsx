import React, { useState, useEffect } from 'react'
import PublicTransportAPI from '../apis/public-transport'
import { SortOrder } from '../apis/public-transport'
import { BusLine } from '../types/bus-line'
import { BuslineTable } from './BuslineTable'
import { StopsList } from './StopsList'

export const BuslineView = (): JSX.Element => {
   const [busLines, setBusLines] = useState<Array<BusLine>>()
   const [selectedBusLine, setSelectedBusLine] = useState<BusLine | null>(null)

   useEffect(() => {
      const loadBusLines = async () => {
         const busLines: Array<BusLine> = await PublicTransportAPI.getBusLines('nrOfStops', SortOrder.DESC, 10)
         console.log('Fetched buslines: ' + busLines)
         setBusLines(busLines)
      }
      loadBusLines()
   }, [])

   const onBuslineSelected = (busLine: BusLine) => {
      setSelectedBusLine(busLine)
   }

   return (
      <>
         <BuslineTable busLines={busLines} onBuslineSelected={onBuslineSelected} />
         {selectedBusLine != null && (
            <StopsList lineName={selectedBusLine.lineNumber} busStops={selectedBusLine.stops} />
         )}
      </>
   )
}
