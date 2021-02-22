package stravaweekly.athletetoken;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class AthleteToken {

  private Long athleteTokenId;
  private String accessToken;
  private String refreshToken;

}
