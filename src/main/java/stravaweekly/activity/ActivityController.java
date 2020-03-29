package stravaweekly.activity;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import stravaweekly.rest.RequestService;

import java.security.Principal;
import java.time.*;

@RestController
public class ActivityController {

  private final RequestService requestService;

  public ActivityController(RequestService requestService) {
    this.requestService = requestService;
  }

  @GetMapping("/athlete/clubs")
  public ResponseEntity<String> athleteClubs(
      final @AuthenticationPrincipal Principal principal) {
      final String url = "https://www.strava.com/api/v3/athlete/clubs";
    return requestService.sendGetRequest(principal, url);
  }

  @GetMapping("/athlete/activities")
  public ResponseEntity<String> listAthleteActivities(
          final @AuthenticationPrincipal Principal principal,
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
            (zonedEnd.toInstant().toEpochMilli() / 1000),
            (zonedStart.toInstant().toEpochMilli() / 1000));

    return requestService.sendGetRequest(principal, url);
  }

}
