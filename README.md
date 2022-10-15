# strava-weekly

# Configuration

Navigate to [Strava My API Application](https://www.strava.com/settings/api). If required, create a new application to receive a Client ID and Secret. Use those in the run command below.

## Build and Run

Configure both CLIENT_ID and CLIENT_SECRET set up above as environment variables.

```
./mvnw clean install
./mvnw spring-boot:run -Dspring-boot.run.arguments=--security.oauth2.client.client-id=CLIENT_ID_REPLACE_ME,--security.oauth2.client.client-secret=CLIENT_SECRET_REPLACE_ME

```

1. Navigate to [http://localhost:8080](http://localhost:8080) and you should be redirected to Strava
2. If needed, log in to Strava
3. Authorize the application with Strava
4. Once redirected back, it will display your activities over the last week.