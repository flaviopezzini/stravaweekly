package stravaweekly.athletetoken;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class AthleteToken {

  @Id @GeneratedValue
  private Long athleteTokenId;
  private String accessToken;
  private String refreshToken;

}
