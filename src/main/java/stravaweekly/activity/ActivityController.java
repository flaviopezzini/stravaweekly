package stravaweekly.activity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

  Logger logger = LoggerFactory.getLogger(ActivityController.class);

  private final RequestService requestService;
  private final ActivityService activityService;

  public ActivityController(RequestService requestService, ActivityService activityService) {
    this.requestService = requestService;
    this.activityService = activityService;
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

    logger.info("userTimezoneOffsetInHours = " + userTimezoneOffsetInHours);

    // do all calculations on dates based on the user timezone
    ZoneId userZoneId = ZoneId.ofOffset("", ZoneOffset.ofHours(userTimezoneOffsetInHours));

    logger.info("userZoneId = " + userZoneId);

    ZonedDateTime zonedEnd = activityService.getEndDate(userZoneId);
    ZonedDateTime zonedStart = activityService.getStartDate(zonedEnd);

    logger.info("zonedEnd = " + zonedEnd);
    logger.info("zonedStart = " + zonedStart);

    final String url = String.format("https://www.strava.com/api/v3/athlete/activities?before=%s&after=%s",
            (zonedEnd.toInstant().toEpochMilli() / 1000),
            (zonedStart.toInstant().toEpochMilli() / 1000));

    return requestService.sendGetRequest(principal, url);
  }

}
