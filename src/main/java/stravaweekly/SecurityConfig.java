package stravaweekly;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.PrincipalMethodArgumentResolver;

import java.util.List;

@Configuration
public class SecurityConfig implements WebMvcConfigurer {

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(new PrincipalMethodArgumentResolver());
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.antMatcher("/**").authorizeRequests()
            .antMatchers(
                    "/",
                    "/webjars/**",
                    "/error**",
                    "/oauth2/authorization/**",
                    "/athleteToken"
            ).permitAll()
            .anyRequest().authenticated()
            .and().logout().logoutSuccessUrl("/").permitAll()
            .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            .and()
            .oauth2Login();
            //.loginPage("/index.html");
    return http.build();
  }
}
