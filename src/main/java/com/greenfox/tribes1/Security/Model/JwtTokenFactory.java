package com.greenfox.tribes1.Security.Model;

import com.greenfox.tribes1.ApplicationUser.ApplicationUserRepository;
import com.greenfox.tribes1.ApplicationUser.ApplicationUserService;
import com.greenfox.tribes1.Security.Config.JwtSettings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtTokenFactory {

  public AccessJwtToken createAccessJwtToken(UserContext userContext) {
    Claims claims = Jwts.claims().setSubject(userContext.getUsername());
    claims.put("scopes", userContext.getAuthorities().stream().map(Object::toString).collect(Collectors.toList()));

    LocalDateTime currentTime = LocalDateTime.now();

    String token = Jwts.builder()
            .setClaims(claims)
            .setIssuer(JwtSettings.TOKEN_ISSUER)
            .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
            .setExpiration(Date.from(currentTime
                    .plusMinutes(JwtSettings.ACCESS_TOKEN_LIFETIME)
                    .atZone(ZoneId.systemDefault()).toInstant()))
            .signWith(SignatureAlgorithm.HS512, JwtSettings.TOKEN_SIGNING_KEY)
            .compact();
    return new AccessJwtToken(token, claims);
  }

  public JwtToken createRefreshToken(UserContext userContext) {
    LocalDateTime currentTime = LocalDateTime.now();

    Claims claims = Jwts.claims().setSubject(userContext.getUsername());
    claims.put("scopes", Arrays.asList(Scopes.REFRESH_TOKEN.authority()));

    String token = Jwts.builder()
            .setClaims(claims)
            .setIssuer(JwtSettings.TOKEN_ISSUER)
            .setId(UUID.randomUUID().toString())
            .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
            .setExpiration(Date.from(currentTime
                    .plusMinutes(JwtSettings.REFRESH_TOKEN_LIFETIME)
                    .atZone(ZoneId.systemDefault()).toInstant()))
            .signWith(SignatureAlgorithm.HS512, JwtSettings.TOKEN_SIGNING_KEY)
            .compact();

    return new AccessJwtToken(token, claims);
  }
}
