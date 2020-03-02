package stravaoauth2.example;

import java.security.Principal;
import java.time.*;
import java.time.temporal.TemporalUnit;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

// https://stackoverflow.com/questions/19238715/how-to-set-an-accept-header-on-spring-resttemplate-request

@RestController
public class ActivityController {

  @GetMapping("/athlete/clubs")
  public ResponseEntity<String> athleteClubs(
      final @AuthenticationPrincipal Principal principal) {

    final String url = "https://www.strava.com/api/v3/athlete/clubs";

    return sendGetRequest(principal, url);
  }

  @GetMapping("/athlete/activities")
  public ResponseEntity<String> athleteActivities(
      final @AuthenticationPrincipal Principal principal) {

    LocalDateTime weekAgo = LocalDateTime.now().minusDays(7);
    ZonedDateTime zonedWeekAgo = ZonedDateTime.of(weekAgo, ZoneId.systemDefault());

    final String url = String.format("https://www.strava.com/api/v3/athlete/activities?before=%s&after=%s",
            (Instant.now().toEpochMilli() / 1000),
            (zonedWeekAgo.toInstant().toEpochMilli()) / 1000);

    return sendGetRequest(principal, url);
  }

  private ResponseEntity<String> sendGetRequest(
      final Principal principal,
      final String url) {

    final RestTemplate restTemplate = new RestTemplate();

    final HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(getAccessToken(principal));
    final HttpEntity<String> entity =
        new HttpEntity<String>("parameters", headers);

    return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
  }

  private String getAccessToken(
      final Principal principal) {

    final OAuth2Authentication oauth2Auth = (OAuth2Authentication) principal;
    final OAuth2AuthenticationDetails oauth2AuthDetails =
        (OAuth2AuthenticationDetails) oauth2Auth.getDetails();

    return oauth2AuthDetails.getTokenValue();
  }

}
