package com.david.demo.traficlabapi.traficlabstypes;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TraficlabStopPoint {
    @JsonProperty("StopPointNumber")
    private Integer stopPointNumber;

    @JsonProperty("StopAreaNumber")
    private Integer stopAreaNumber;
}
