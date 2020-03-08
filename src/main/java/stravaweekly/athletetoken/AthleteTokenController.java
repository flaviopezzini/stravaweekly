package stravaweekly.athletetoken;

import java.security.Principal;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import stravaweekly.rest.RequestService;

// https://stackoverflow.com/questions/27864295/how-to-use-oauth2resttemplate

@RestController
public class AthleteTokenController {

  private final AthleteTokenRepository athleteTokenRepository;
  private final RequestService requestService;

  public AthleteTokenController(AthleteTokenRepository athleteTokenRepository, RequestService requestService) {
    this.athleteTokenRepository = athleteTokenRepository;
    this.requestService = requestService;
  }

  @RequestMapping("/principal")
  public Principal principal(
      @AuthenticationPrincipal Principal principal) {

    return principal;
  }

  @RequestMapping("/athleteToken")
  public AthleteToken athleteToken(
      final @AuthenticationPrincipal Principal principal) {

    return athleteTokenRepository.save(
        AthleteToken.builder()
            .accessToken(requestService.getAccessToken(principal))
            .build());
  }

  @RequestMapping("/athleteTokens")
  public List<AthleteToken> athletes() {
    return athleteTokenRepository.findAll();
  }

}
