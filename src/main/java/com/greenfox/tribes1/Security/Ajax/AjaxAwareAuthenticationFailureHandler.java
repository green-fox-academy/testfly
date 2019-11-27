package com.greenfox.tribes1.Security.Ajax;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenfox.tribes1.Exception.ErrorMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AjaxAwareAuthenticationFailureHandler implements AuthenticationFailureHandler {
  private final ObjectMapper mapper;

  @Autowired
  public AjaxAwareAuthenticationFailureHandler(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);

    if (e instanceof BadCredentialsException) {
      mapper.writeValue(response.getWriter(), new ErrorMsg("error", "Wrong password!"));
    } else if (e instanceof UsernameNotFoundException) {
      mapper.writeValue(response.getWriter(), new ErrorMsg("error", e.getMessage()));
    } else if (e instanceof AuthenticationServiceException){
      mapper.writeValue(response.getWriter(), new ErrorMsg("error", "Auth failure"));
    }
    mapper.writeValue(response.getWriter(), new ErrorMsg("error", "unknown error"));
  }
}
