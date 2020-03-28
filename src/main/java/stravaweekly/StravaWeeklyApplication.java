package stravaweekly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import java.time.Clock;

@SpringBootApplication
@EnableOAuth2Sso
public class StravaWeeklyApplication extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.antMatcher("/**").authorizeRequests()
        .antMatchers("/", "/login**", "/webjars/**", "/error**").permitAll()
        .anyRequest().authenticated()
        .and().logout().logoutSuccessUrl("/").permitAll()
        .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
  }

  public static void main(String[] args) {
    SpringApplication.run(StravaWeeklyApplication.class, args);
  }

  @Bean
  public Clock getClock() {
    return Clock.systemUTC();
  }

}
