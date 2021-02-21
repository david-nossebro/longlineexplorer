import { BusStop } from './bus-stop'

export type BusLine = {
   lineNumber: number
   nrOfStops: number
   stops: Array<BusStop>
}
