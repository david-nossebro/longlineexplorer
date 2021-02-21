import React, { useState } from 'react'
import styled from 'styled-components'
import { BusLine } from '../types/bus-line'

const StyledTable = styled.table`
   width: 20em;
   border-collapse: collapse;
   margin: 5em auto;

   th {
      background: #186497;
      color: white;
      font-weight: bold;
   }

   td,
   th {
      padding: 0.2em 1em 0.2em;
      border: 1px solid #ccc;
      text-align: left;
      font-size: 1em;
   }

   tr.selected {
      background-color: #c0e0f5;
   }

   tr:hover {
      cursor: pointer;
      background-color: #f5f8fa;
   }
`

interface IProps {
   busLines?: Array<BusLine>
   onBuslineSelected: (busLine: BusLine) => void
}

export const BuslineTable: React.FC<IProps> = (props: IProps) => {
   const [selectedBusLine, setSelectedBusLine] = useState<BusLine | null>(null)

   return (
      <StyledTable data-testid="BuslineTable">
         <thead>
            <tr>
               <th>Line</th>
               <th>Number of stops</th>
            </tr>
         </thead>
         <tbody>
            {props.busLines &&
               props.busLines.map((busLine) => (
                  <tr
                     key={busLine.lineNumber.toString()}
                     className={selectedBusLine === busLine ? 'selected' : undefined}
                     onClick={() => {
                        props.onBuslineSelected(busLine)
                        setSelectedBusLine(busLine)
                     }}
                  >
                     <td>{busLine.lineNumber}</td>
                     <td>{busLine.nrOfStops}</td>
                  </tr>
               ))}
         </tbody>
      </StyledTable>
   )
}
