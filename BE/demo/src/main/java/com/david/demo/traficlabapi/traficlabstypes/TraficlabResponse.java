package com.david.demo.traficlabapi.traficlabstypes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown=true)
@Data
public class TraficlabResponse<T> {

    @JsonProperty("StatusCode")
    private Integer statusCode;
    @JsonProperty("Message")
    private String message;
    @JsonProperty("ResponseData")
    private TraficlabResponseData<T> responseData;
}
