package com.david.demo.repository;

import com.david.demo.errorhandling.LoadDataException;
import com.david.demo.traficlabapi.TraficlabAPIService;
import com.david.demo.traficlabapi.traficlabstypes.TraficlabJourneyPatternPointOnLine;
import com.david.demo.traficlabapi.traficlabstypes.TraficlabSite;
import com.david.demo.traficlabapi.traficlabstypes.TraficlabStopPoint;
import com.david.demo.types.BusLine;
import com.david.demo.types.BusStop;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class BuslineRepository {

    @Autowired
    private TraficlabAPIService traficlabAPIService;

    private List<TraficlabStopPoint> traficlabStopPoints;
    private List<TraficlabSite> traficlabSites;
    private List<TraficlabJourneyPatternPointOnLine> traficlabJourneyPatternPointOnLine;

    private List<BusLine> busLines;

    public List<BusLine> getBusLines() {
        if(busLines == null) {
            log.error("Failed to serve requested bus lines. No bus lines has been loaded.");
            throw new LoadDataException("Sorry, but no buslines has been loaded on startup. Something must have gone wrong.");
        }

        return busLines;
    }

    /**
     * Preparing the data in this case means loading the data from the external Traficlab API and then convert
     * it into a format that we user in this application.
     *
     * The data loaded from Traficlab is built up by 3 types explained below:
     * JourneyPatternPointOnLine - A list of all StopPoints on a line
     * StopPoints - Contains 'StopPointNumber' used to map it from the JourneyPatternPointOnLine
     *              'JourneyPatternPointNumber' and 'StopAreaNumber' used to map it to a Site.
     * Sites - A site is a set of StopPoints that is geographically close to each other. In this application
     *          I have made the assumption that this is what regular people see as bus stops.
     *
     *  More information about the Traficlab data can be found here:
     *  https://www.trafiklab.se/api/sl-hallplatser-och-linjer-2
     *  This picture is pretty good for understanding the format:
     *  https://www.trafiklab.se/sites/default/files/documentation-files/model.pdf
     *
     * The local format is just a list of bus lines ('BusLine') where a single BusLine object also contains a list
     * of all stops on that particular line ('BusStop').
     *
     */
    @PostConstruct
    private void prepareData() {
        try {
            log.info("Loading data from Traficlab API...");
            CompletableFuture<List<TraficlabStopPoint>> traficlabStopPointsFuture = traficlabAPIService.getStopPoints();
            CompletableFuture<List<TraficlabSite>> traficlabSitesFuture = traficlabAPIService.getSites();
            CompletableFuture<List<TraficlabJourneyPatternPointOnLine>> traficlabJourneyPatternPointOnLineFuture =
                    traficlabAPIService.getJourneyPatternPointOnLine();

            CompletableFuture
                    .allOf(traficlabStopPointsFuture, traficlabSitesFuture, traficlabJourneyPatternPointOnLineFuture)
                    .join();

            traficlabStopPoints = traficlabStopPointsFuture.get();
            traficlabSites = traficlabSitesFuture.get();
            traficlabJourneyPatternPointOnLine = traficlabJourneyPatternPointOnLineFuture.get();
            log.info("Successfully loaded data from Traficlab API! :-)");

            busLines = buildInternalBusLinesList();
            log.info("Successfully compiled internal BusLines list from Traficlab data");

        } catch (Throwable e) {
            log.error("Failed to load data from Traficlab API, the application will not work properly.", e);
        }
    }

    private List<BusLine> buildInternalBusLinesList() {

        List<BusLine> lines = traficlabJourneyPatternPointOnLine
                .stream()
                .collect(Collectors.groupingBy(TraficlabJourneyPatternPointOnLine::getLineNumber))
                .entrySet()
                .stream()
                .parallel()
                .map(g -> {
                    List<Integer> stopPointsOnTheLineIds = g.getValue().stream().map(t -> t.getJourneyPatternPointNumber()).collect(Collectors.toList());

                    List<Integer> stopAreasOnTheLine = traficlabStopPoints
                            .stream()
                            .filter(p -> stopPointsOnTheLineIds.contains(p.getStopPointNumber()))
                            .map(sP -> sP.getStopAreaNumber())
                            .collect(Collectors.toList());

                    List<TraficlabSite> sitesOnTheLine = traficlabSites
                            .stream()
                            .filter(s -> stopAreasOnTheLine.contains(s.getStopAreaNumber()))
                            .collect(Collectors.toList());

                    List<BusStop> busStops = sitesOnTheLine
                            .stream()
                            .map(s -> new BusStop(s.getStopAreaNumber(), s.getSiteName()))
                            .collect(Collectors.toList());

                    return new BusLine(g.getKey(), busStops);
                }).collect(Collectors.toList());

        return lines;
    }
}
