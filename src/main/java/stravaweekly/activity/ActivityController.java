package stravaweekly.activity;

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
          @RequestParam(name = "userTimezoneOffsetInHours", required = true) int userTimezoneOffsetInHours) {

    // do all calculations on dates based on the user timezone
    ZoneId userZoneId = ZoneId.ofOffset("", ZoneOffset.ofHours(userTimezoneOffsetInHours));

    LocalDateTime now = LocalDateTime.now();
    ZonedDateTime zonedNow = ZonedDateTime.of(now, userZoneId);

    // 1-Mon, 2-Tue, 3-Wed, 4-Thu, 5-Fri, 6-Sat, 7-Sun
    int dayOfWeek = zonedNow.getDayOfWeek().getValue();
    ZonedDateTime zonedEnd;
    // if it's Sunday get this week, else get last week
    if (zonedNow.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
      zonedEnd = zonedNow;
    } else {
      zonedEnd = zonedNow.minusDays(dayOfWeek);
    }
    ZonedDateTime zonedStart = zonedEnd.minusDays(7);

    final String url = String.format("https://www.strava.com/api/v3/athlete/activities?before=%s&after=%s",
            (zonedEnd.toInstant().toEpochMilli() / 1000),
            (zonedStart.toInstant().toEpochMilli() / 1000));

    return requestService.sendGetRequest(principal, url);
  }

}
