package com.david.demo.traficlabapi.traficlabstypes;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TraficlabSite {
    @JsonProperty("SiteName")
    private String siteName;
    @JsonProperty("StopAreaNumber")
    private Integer stopAreaNumber;
}
