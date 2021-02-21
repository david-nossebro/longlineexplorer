package com.david.demo.controllers;

import com.david.demo.services.PublicTransportService;
import com.david.demo.types.BusLine;
import com.david.demo.types.SortOrder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@RestController
@RequestMapping("/api/publictransport")
public class PublicTransportController extends ResponseEntityExceptionHandler {

    @Autowired
    private PublicTransportService publicTransportService;

    @CrossOrigin("http://localhost:3000")
    @GetMapping("/buslines")
    @Operation(summary = "Get all bus lines with a list of the stops along the line.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful operation",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = BusLine.class))
                    )
            )
    })
    public List<BusLine> getAllBuslines(
            @Parameter(description = "Defines a property to sort by. Can be 'lineNumber' or 'nrOfStops'.")
            @RequestParam(required = false) String orderBy,
            @Parameter(description = "Defines the sort order.")
            @RequestParam(required = false, defaultValue = "ASC") SortOrder sortOrder,
            @Parameter(description = "Limits the number of bus lines in the response. Must be a positive number.")
            @RequestParam(required = false) Integer limit) {

        // Negative 'limit' is not valid.
        if(limit != null && limit < 0 ) {
            limit = Integer.MAX_VALUE;
        }

        return publicTransportService.getBusLines(orderBy, sortOrder, limit);
    }
}
