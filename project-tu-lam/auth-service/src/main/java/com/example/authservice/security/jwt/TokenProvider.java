package com.example.authservice.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.*;

@Component
public class TokenProvider implements InitializingBean {
    private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    private static final String AUTHORITIES_KEY = "auth";
    private final String secret = "YnVpIGRpbmggaGlldSBidWkgZGluaCBoaWV1IGJ1aSBkaW5oIGhpZXUgYnVpIGRpbmggaGlldSBidWkgZGluaCBoaWV1IGJ1aSBkaW5oIGhpZXUg";

    private final long timeTokenInMilliseconds = 30000000000L;

    private Key key;

    public TokenProvider() {}

    public void afterPropertiesSet() {
      byte[] keyBytes = Decoders.BASE64.decode(secret);
      this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateTokenByClaims(Object value) {
        long now = new Date().getTime();

        Map<String, Object> claims = new HashMap<>();
        claims.put("claims", value);

        return Jwts.builder().addClaims(claims).signWith(key, SignatureAlgorithm.HS512).setExpiration(new Date(now + timeTokenInMilliseconds)).compact();
    }

    public String generateTokenBySubject(String username) {
        long now = new Date().getTime();

        return Jwts.builder().setSubject(username).signWith(key, SignatureAlgorithm.HS512).setExpiration(new Date(now + timeTokenInMilliseconds)).compact();
    }

    public String getClaimsByToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return claims.get("claims").toString();
    }

    public String getSubjectByToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            logger.info("Invalid JWT signature.");
            logger.trace("Invalid JWT signature trace: {}", e);
          } catch (ExpiredJwtException e) {
            logger.info("Expired JWT token.");
            logger.trace("Expired JWT token trace: {}", e);
          } catch (UnsupportedJwtException e) {
            logger.info("Unsupported JWT token.");
            logger.trace("Unsupported JWT token trace: {}", e);
          } catch (IllegalArgumentException e) {
            logger.info("JWT token compact of handler are invalid.");
            logger.trace("JWT token compact of handler are invalid trace: {}", e);
          }

          return false;
    }
}
