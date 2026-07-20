package com.labelflow.labelchange.auth.security.jwt;

import com.labelflow.labelchange.auth.security.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtProperties jwtProperties;

    public JwtToken generateToken(UserPrincipal userPrincipal) {
        Instant now = Instant.now();

        Instant expiry = now.plusMillis(jwtProperties.getAccessTokenExpiration());

        String token = Jwts.builder()
                .subject(userPrincipal.getUsername())
                .issuer(jwtProperties.getIssuer())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiry))
                .claim("roles",
                        userPrincipal.getAuthorities()
                                .stream()
                                .map(GrantedAuthority::getAuthority)
                                .toList())
                .signWith(getSigningKey())
                .compact();

        return new JwtToken(token, jwtProperties.getAccessTokenExpiration());
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    public boolean validateToken(String token, UserPrincipal userPrincipal) {
        String username = extractUsername(token);
        return (username.equals(userPrincipal.getUsername()) && !isTokenExpired(token));
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
