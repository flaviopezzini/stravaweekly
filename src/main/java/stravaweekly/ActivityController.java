package stravaweekly;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@RestController
public class ActivityController {

  private final RequestService requestService;

  public ActivityController(RequestService requestService) {
    this.requestService = requestService;
  }

  @GetMapping("/athlete/activities")
  public ResponseEntity<String> listAthleteActivities(
          final OAuth2AuthenticationToken auth,
          @RequestParam(name = "userTimezoneOffsetInHours") int userTimezoneOffsetInHours,
          @RequestParam(name = "startDate")
          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                  LocalDateTime startDate,
          @RequestParam(name = "endDate")
          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                  LocalDateTime endDate) {

    // do all calculations on dates based on the user timezone
    ZoneId userZoneId = ZoneId.ofOffset("", ZoneOffset.ofHours(userTimezoneOffsetInHours));
    ZonedDateTime zonedStart = ZonedDateTime.of(startDate, userZoneId);
    ZonedDateTime zonedEnd = ZonedDateTime.of(endDate, userZoneId);

    final String url = String.format("https://www.strava.com/api/v3/athlete/activities?before=%s&after=%s",
            zonedEnd.toInstant().toEpochMilli() / 1000,
            zonedStart.toInstant().toEpochMilli() / 1000);

    return requestService.sendGetRequest(auth, url);
  }

}
