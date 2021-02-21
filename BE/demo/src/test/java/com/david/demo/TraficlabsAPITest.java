package com.david.demo;

import com.david.demo.traficlabapi.traficlabstypes.TraficlabJourneyPatternPointOnLine;
import com.david.demo.traficlabapi.traficlabstypes.TraficlabResponse;
import com.david.demo.traficlabapi.traficlabstypes.TraficlabSite;
import com.david.demo.traficlabapi.traficlabstypes.TraficlabStopPoint;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TraficlabsAPITest {

    @Value("${traficlabapi.url}")
    private String traficlabsUrl;

    @Value("${traficlabapi.key}")
    private String traficlabsKey;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getStopsShouldReturnOk() throws RestClientException {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(traficlabsUrl)
                .queryParam("key", traficlabsKey)
                .queryParam("model", "stop");

        ResponseEntity response = restTemplate.getForEntity(builder.toUriString(), Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void getStopsShouldReturnEntitiesThatMatchOurMapping() throws RestClientException {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(traficlabsUrl)
                .queryParam("key", traficlabsKey)
                .queryParam("model", "stop");

        TraficlabResponse<TraficlabStopPoint> stopsResponse = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<TraficlabResponse<TraficlabStopPoint>>() {}).getBody();

        assertThat(stopsResponse).isNotNull();
        assertThat(stopsResponse.getResponseData()).isNotNull();
        assertThat(stopsResponse.getResponseData().getResult()).isNotNull();
        assertThat(stopsResponse.getResponseData().getResult().isEmpty()).isNotEqualTo(true);
        assertThat(stopsResponse.getResponseData().getResult()).hasNoNullFieldsOrProperties();
    }

    @Test
    public void getSitesShouldReturnEntitiesThatMatchOurMapping() throws RestClientException {

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(traficlabsUrl)
                .queryParam("key", traficlabsKey)
                .queryParam("model", "site");

        TraficlabResponse<TraficlabSite> sitesResponse = restTemplate.exchange(
                uriBuilder.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<TraficlabResponse<TraficlabSite>>() {}).getBody();

        assertThat(sitesResponse).isNotNull();
        assertThat(sitesResponse.getResponseData()).isNotNull();
        assertThat(sitesResponse.getResponseData().getResult()).isNotNull();
        assertThat(sitesResponse.getResponseData().getResult().isEmpty()).isNotEqualTo(true);
        assertThat(sitesResponse.getResponseData().getResult()).hasNoNullFieldsOrProperties();
    }

    @Test
    public void getJourneyPatternPointOnLineShouldReturnEntitiesThatMatchOurMapping() throws RestClientException {

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(traficlabsUrl)
                .queryParam("key", traficlabsKey)
                .queryParam("model", "JourneyPatternPointOnLine")
                .queryParam("DefaultTransportModeCode", "BUS");

        TraficlabResponse<TraficlabJourneyPatternPointOnLine> journyPatternPointOnLineResponse = restTemplate.exchange(
                uriBuilder.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<TraficlabResponse<TraficlabJourneyPatternPointOnLine>>() {}).getBody();

        assertThat(journyPatternPointOnLineResponse).isNotNull();
        assertThat(journyPatternPointOnLineResponse.getResponseData()).isNotNull();
        assertThat(journyPatternPointOnLineResponse.getResponseData().getResult()).isNotNull();
        assertThat(journyPatternPointOnLineResponse.getResponseData().getResult().isEmpty()).isNotEqualTo(true);
        assertThat(journyPatternPointOnLineResponse.getResponseData().getResult()).hasNoNullFieldsOrProperties();
    }
}
