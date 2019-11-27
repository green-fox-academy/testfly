package com.greenfox.tribes1.Security.Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenfox.tribes1.Security.Ajax.AjaxAuthenticationProvider;
import com.greenfox.tribes1.Security.Ajax.AjaxLoginProcessingFilter;
import com.greenfox.tribes1.Security.CustomCorsFilter;
import com.greenfox.tribes1.Security.JWT.Extractor.TokenExtractor;
import com.greenfox.tribes1.Security.JWT.JwtAuthenticationProvider;
import com.greenfox.tribes1.Security.JWT.JwtTokenAuthenticationProcessingFilter;
import com.greenfox.tribes1.Security.JWT.SkipPathRequestMatcher;
import com.greenfox.tribes1.Security.RestAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  public static final String AUTHENTICATION_HEADER_NAME = "Authorization";
  private static final String AUTHENTICATION_URL = "/login";
  private static final String REGISTRATION_URL = "/register";
  private static final String REFRESH_TOKEN_URL = "/refreshtoken";
  private static final String API_ROOT_URL = "/**";

  @Autowired
  private RestAuthenticationEntryPoint authenticationEntryPoint;
  @Autowired
  private AuthenticationSuccessHandler successHandler;
  @Autowired
  private AuthenticationFailureHandler failureHandler;
  @Autowired
  private AjaxAuthenticationProvider ajaxAuthenticationProvider;
  @Autowired
  private JwtAuthenticationProvider jwtAuthenticationProvider;
  @Autowired
  private TokenExtractor tokenExtractor;
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private ObjectMapper objectMapper;

  private AjaxLoginProcessingFilter buildAjaxLoginProcessingFilter() {
    AjaxLoginProcessingFilter filter = new AjaxLoginProcessingFilter(AUTHENTICATION_URL, successHandler, failureHandler, objectMapper);
    filter.setAuthenticationManager(this.authenticationManager);
    return filter;
  }

  private JwtTokenAuthenticationProcessingFilter buildJwtTokenAuthenticationProcessingFilter(List<String> pathsToSkip, String pattern) {
    SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip, pattern);
    JwtTokenAuthenticationProcessingFilter filter
            = new JwtTokenAuthenticationProcessingFilter(failureHandler, tokenExtractor, matcher);
    filter.setAuthenticationManager(this.authenticationManager);
    return filter;
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) {
    auth.authenticationProvider(ajaxAuthenticationProvider);
    auth.authenticationProvider(jwtAuthenticationProvider);
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    List<String> permitAllEndpointList = Arrays.asList(
            AUTHENTICATION_URL,
            REFRESH_TOKEN_URL,
            REGISTRATION_URL
    );
    http.cors().and().csrf().disable()
              .exceptionHandling().authenticationEntryPoint(this.authenticationEntryPoint)
            .and()
              .sessionManagement()
              .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
              .authorizeRequests()
              .antMatchers(AUTHENTICATION_URL, REFRESH_TOKEN_URL, REGISTRATION_URL).permitAll()
              .antMatchers("/admin/**").hasRole("ADMIN")
            .and()
              .addFilterBefore(new CustomCorsFilter(), UsernamePasswordAuthenticationFilter.class)
              .addFilterBefore(buildAjaxLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
              .addFilterBefore(buildJwtTokenAuthenticationProcessingFilter(permitAllEndpointList, API_ROOT_URL), UsernamePasswordAuthenticationFilter.class)
              .logout().permitAll();
  }
}
