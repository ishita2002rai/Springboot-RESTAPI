package com.example.green;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    private final String secret;
    private final long expiration;

    @Autowired
    public JwtTokenUtil(@Value("${jwt.secret}") String secret, @Value("${jwt.expiration}") long expiration) {
        this.secret = secret;
        this.expiration = expiration;
    }


 public String generateToken(UserDetails userDetails) {
     Map<String, Object> claims = new HashMap<>();
     return createToken(claims, userDetails.getUsername());
 }

 private String createToken(Map<String, Object> claims, String subject) {
     Date now = new Date();
     Date expiryDate = new Date(now.getTime() + expiration);

     return Jwts.builder()
             .setClaims(claims)
             .setSubject(subject)
             .setIssuedAt(now)
             .setExpiration(expiryDate)
             .signWith(SignatureAlgorithm.HS512, secret)
             .compact();
 }

 public String extractUsername(String token) {
     return extractClaim(token, Claims::getSubject);
 }

 public Date extractExpirationDate(String token) {
     return extractClaim(token, Claims::getExpiration);
 }

 public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
     Claims claims = extractAllClaims(token);
     return claimsResolver.apply(claims);
 }

 private Claims extractAllClaims(String token) {
     return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
 }

 private boolean isTokenExpired(String token) {
     Date expirationDate = extractExpirationDate(token);
     return expirationDate.before(new Date());
 }

 public boolean validateToken(String token, UserDetails userDetails) {
     String username = extractUsername(token);
     return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
 }
}
