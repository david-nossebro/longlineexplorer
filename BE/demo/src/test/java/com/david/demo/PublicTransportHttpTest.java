package com.david.demo;

import com.david.demo.types.BusLine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.util.Comparator;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PublicTransportHttpTest {

    private String buslinesUrl;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @PostConstruct
    private void createBuslineUrl() {
        buslinesUrl = UriComponentsBuilder
            .fromHttpUrl("http://localhost")
            .port(port)
            .path("/api/publictransport/buslines")
            .toUriString();
    }

    @Test
    public void getBuslinesShouldReturnOk() throws Exception {
        ResponseEntity response = restTemplate.getForEntity(buslinesUrl, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void getBuslinesWithoutLimitShouldReturnMoreThan10() throws Exception {
        BusLine[] busLines = restTemplate.getForObject(buslinesUrl, BusLine[].class);
        assertThat(busLines.length).isGreaterThan(10);
    }

    @Test
    public void getBuslinesWithLimit10ShouldReturn10() throws Exception {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(buslinesUrl)
                .queryParam("limit", 10);

        BusLine[] busLines = restTemplate.getForObject(builder.toUriString(), BusLine[].class);
        assertThat(busLines.length).isEqualTo(10);
    }

    @Test
    public void getBuslinesWithLimit5ShouldReturn5() throws Exception {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(buslinesUrl)
                .queryParam("limit", 5);

        BusLine[] busLines = restTemplate.getForObject(builder.toUriString(), BusLine[].class);
        assertThat(busLines.length).isEqualTo(5);
    }

    @Test
    public void getBuslinesWithNegativeLimitShouldReturnSameAsNoLimit() throws Exception {
        UriComponentsBuilder builderNegativeLimit = UriComponentsBuilder
                .fromHttpUrl(buslinesUrl)
                .queryParam("limit", -1);

        BusLine[] busLinesNegativeLimit = restTemplate.getForObject(builderNegativeLimit.toUriString(), BusLine[].class);
        BusLine[] busLinesNoLimit = restTemplate.getForObject(buslinesUrl, BusLine[].class);

        assertThat(busLinesNegativeLimit.length).isEqualTo(busLinesNoLimit.length);
        assertThat(busLinesNegativeLimit).isEqualTo(busLinesNoLimit);
    }

    @Test
    public void getBuslinesOrderedByLineNumberShouldReturnSortedResponse() throws Exception {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(buslinesUrl)
                .queryParam("orderBy", "lineNumber");

        BusLine[] busLines = restTemplate.getForObject(builder.toUriString(), BusLine[].class);

        // If no 'sortOrder' is specified, default order should be ascending.
        assertThat(busLines).isSortedAccordingTo(Comparator.comparingInt(BusLine::getLineNumber));
    }

    @Test
    public void getBuslinesOrderedByNrOfStopsDescendingShouldReturnSortedResponse() throws Exception {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(buslinesUrl)
                .queryParam("orderBy", "nrOfStops")
                .queryParam("sortOrder", "DESC");

        BusLine[] busLines = restTemplate.getForObject(builder.toUriString(), BusLine[].class);
        assertThat(busLines).isSortedAccordingTo(Comparator.comparingInt(BusLine::getNrOfStops).reversed());
    }

    @Test
    public void getBuslinesOrderedByNrOfStopsAscendingShouldReturnSortedResponse() throws Exception {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(buslinesUrl)
                .queryParam("orderBy", "nrOfStops")
                .queryParam("sortOrder", "ASC");

        BusLine[] busLines = restTemplate.getForObject(builder.toUriString(), BusLine[].class);
        assertThat(busLines).isSortedAccordingTo(Comparator.comparingInt(BusLine::getNrOfStops));
    }

    @Test
    public void getBuslinesOrderedByNrOfStopsDescendingAndLimit10ShouldReturnLimitedSortedResponse() throws Exception {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(buslinesUrl)
                .queryParam("orderBy", "nrOfStops")
                .queryParam("sortOrder", "DESC")
                .queryParam("limit", 10);

        BusLine[] busLines = restTemplate.getForObject(builder.toUriString(), BusLine[].class);
        assertThat(busLines).isSortedAccordingTo(Comparator.comparingInt(BusLine::getNrOfStops).reversed());
        assertThat(busLines.length).isEqualTo(10);
    }

    @Test
    public void getBuslinesOrderedByUnknownPropertyShouldNotFail() {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(buslinesUrl)
                .queryParam("orderBy", "theVeryUnknownAndWeirdPropertyThatDoesNotExistOnABusLine");

        ResponseEntity response = restTemplate.getForEntity(builder.toUriString(), Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void getBuslinesWithUnknownSortOrderShouldNotFail() {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(buslinesUrl)
                .queryParam("sortOrder", "INVALID SORTORDER");

        ResponseEntity response = restTemplate.getForEntity(builder.toUriString(), Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
