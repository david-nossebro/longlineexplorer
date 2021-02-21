package com.david.demo.traficlabapi.traficlabstypes;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TraficlabResponseData<T> {

    @JsonProperty("Result")
    private List<T> result;
}
