package stravaweekly;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AthleteTokenController {

  private final RequestService requestService;

  public AthleteTokenController(RequestService requestService) {
    this.requestService = requestService;
  }

  @GetMapping("/athleteToken")
  public AthleteToken athleteToken(final OAuth2AuthenticationToken auth) {

    String token = auth == null ? null : requestService.getAccessToken(auth);

    return new AthleteToken(
            null,
            token,
            null);
  }

}
