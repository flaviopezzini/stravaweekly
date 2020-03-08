package stravaweekly.rest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;

@Service
public class RequestService {

    public ResponseEntity<String> sendGetRequest(
            final Principal principal,
            final String url) {

        final RestTemplate restTemplate = new RestTemplate();

        final HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getAccessToken(principal));
        final HttpEntity<String> entity =
                new HttpEntity<String>("parameters", headers);

        return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    }

    public String getAccessToken(
            final Principal principal) {

        final OAuth2Authentication oauth2Auth = (OAuth2Authentication) principal;
        final OAuth2AuthenticationDetails oauth2AuthDetails =
                (OAuth2AuthenticationDetails) oauth2Auth.getDetails();

        return oauth2AuthDetails.getTokenValue();
    }

}
