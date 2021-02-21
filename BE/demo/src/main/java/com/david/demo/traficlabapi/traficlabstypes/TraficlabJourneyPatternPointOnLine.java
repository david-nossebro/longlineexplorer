package com.david.demo.traficlabapi.traficlabstypes;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TraficlabJourneyPatternPointOnLine {
    @JsonProperty("LineNumber")
    private Integer lineNumber;
    @JsonProperty("JourneyPatternPointNumber")
    private Integer journeyPatternPointNumber;
}
