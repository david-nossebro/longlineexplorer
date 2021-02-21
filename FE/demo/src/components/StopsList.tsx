import React from 'react'
import styled from 'styled-components'
import { BusStop } from '../types/bus-stop'

const Container = styled.div`
   margin-left: 2em;
   margin-right: 2em;
`

const Header = styled.h3``

interface IProps {
   lineName: number
   busStops: Array<BusStop>
}

export const StopsList: React.FC<IProps> = (props: IProps) => {
   return (
      <Container>
         <Header>Stops for bus {props.lineName}</Header>
         <div>
            {props.busStops.map(
               (busStop, index) =>
                  //Adds ',' after each stop except for the last one.
                  busStop.name + (props.busStops.length - 1 === index ? '.' : ', '),
            )}
         </div>
      </Container>
   )
}
