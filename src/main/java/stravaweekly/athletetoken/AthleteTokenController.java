package stravaweekly.athletetoken;

import java.security.Principal;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

// https://stackoverflow.com/questions/27864295/how-to-use-oauth2resttemplate

@RestController
@RequiredArgsConstructor
public class AthleteTokenController {

  private final AthleteTokenRepository athleteTokenRepository;

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
            .accessToken(getAccessToken(principal))
            .build());
  }

  @RequestMapping("/athleteTokens")
  public List<AthleteToken> athletes() {
    return athleteTokenRepository.findAll();
  }

  private String getAccessToken(
      final Principal principal) {

    final OAuth2Authentication oauth2Auth = (OAuth2Authentication) principal;
    final OAuth2AuthenticationDetails oauth2AuthDetails =
        (OAuth2AuthenticationDetails) oauth2Auth.getDetails();

    return oauth2AuthDetails.getTokenValue();
  }

}
