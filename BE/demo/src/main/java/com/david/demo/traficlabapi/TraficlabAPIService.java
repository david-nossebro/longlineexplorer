package com.david.demo.traficlabapi;

import com.david.demo.traficlabapi.traficlabstypes.TraficlabJourneyPatternPointOnLine;
import com.david.demo.traficlabapi.traficlabstypes.TraficlabResponse;
import com.david.demo.traficlabapi.traficlabstypes.TraficlabSite;
import com.david.demo.traficlabapi.traficlabstypes.TraficlabStopPoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Service interacting with the Traficlab API. Documentation can be found here:
 * https://www.trafiklab.se/api/sl-hallplatser-och-linjer-2
 */
@Slf4j
@Service
public class TraficlabAPIService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${traficlabapi.url}")
    private String traficlabsUrl;

    @Value("${traficlabapi.key}")
    private String traficlabsKey;

    @Async
    public CompletableFuture<List<TraficlabStopPoint>> getStopPoints() {
        log.info("Fetching StopPoints");
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(traficlabsUrl)
                .queryParam("key", traficlabsKey)
                .queryParam("model", "stop");

        TraficlabResponse<TraficlabStopPoint> stopsResponse = restTemplate.exchange(
                uriBuilder.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<TraficlabResponse<TraficlabStopPoint>>() {}).getBody();

        List<TraficlabStopPoint> stopPoints = stopsResponse.getResponseData().getResult();

        log.info("StopPoints fetched");
        return CompletableFuture.completedFuture(stopPoints);
    }

    @Async
    public CompletableFuture<List<TraficlabSite>> getSites() {
        log.info("Fetching Sites");
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(traficlabsUrl)
                .queryParam("key", traficlabsKey)
                .queryParam("model", "site");

        TraficlabResponse<TraficlabSite> sitesResponse = restTemplate.exchange(
                uriBuilder.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<TraficlabResponse<TraficlabSite>>() {}).getBody();

        List<TraficlabSite> sites = sitesResponse.getResponseData().getResult();

        log.info("Sites fetched");
        return CompletableFuture.completedFuture(sites);
    }

    @Async
    public CompletableFuture<List<TraficlabJourneyPatternPointOnLine>> getJourneyPatternPointOnLine() {
        log.info("Fetching JourneyPatternPointsOnLine");
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(traficlabsUrl)
                .queryParam("key", traficlabsKey)
                .queryParam("model", "JourneyPatternPointOnLine")
                .queryParam("DefaultTransportModeCode", "BUS");


        TraficlabResponse<TraficlabJourneyPatternPointOnLine> journeyPatternPointOnLineResponse = restTemplate.exchange(
                uriBuilder.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<TraficlabResponse<TraficlabJourneyPatternPointOnLine>>() {}).getBody();

        List<TraficlabJourneyPatternPointOnLine> journeyPatternPoints =
                journeyPatternPointOnLineResponse.getResponseData().getResult();

        log.info("JourneyPatternPointsOnLine fetched");
        return CompletableFuture.completedFuture(journeyPatternPoints);
    }
}
