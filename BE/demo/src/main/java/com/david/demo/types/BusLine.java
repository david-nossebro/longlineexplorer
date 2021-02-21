package com.david.demo.types;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class BusLine {
    private Integer lineNumber;
    private Integer nrOfStops;
    private List<BusStop> stops;

    public BusLine(Integer lineNumber, List<BusStop> stops) {
        this.lineNumber = lineNumber;
        this.stops = stops;
        this.nrOfStops = stops.size();
    }
}
