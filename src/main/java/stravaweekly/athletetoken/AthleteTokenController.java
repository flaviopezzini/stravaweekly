package stravaweekly.athletetoken;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stravaweekly.rest.RequestService;

import java.security.Principal;

@RestController
public class AthleteTokenController {

  private final RequestService requestService;

  public AthleteTokenController(RequestService requestService) {
    this.requestService = requestService;
  }

  @RequestMapping("/athleteToken")
  public AthleteToken athleteToken(
      final @AuthenticationPrincipal Principal principal) {

    return AthleteToken.builder()
            .accessToken(requestService.getAccessToken(principal))
            .build();
  }

}
