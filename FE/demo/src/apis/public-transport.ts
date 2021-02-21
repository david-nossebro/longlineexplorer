import { BusLine } from '../types/bus-line'

async function getBusLines(orderBy: string, sortOrder: SortOrder, limit: number): Promise<Array<BusLine>> {
   const url = new URL('http://localhost:8080/api/publictransport/buslines')

   orderBy && url.searchParams.append('orderBy', orderBy)
   sortOrder && url.searchParams.append('sortOrder', SortOrder[sortOrder])
   limit && url.searchParams.append('limit', limit.toString())

   const response = await fetch(url.toString())
   const body = await response.json()

   return body
}

export enum SortOrder {
   ASC,
   DESC,
}

export default {
   getBusLines,
}
