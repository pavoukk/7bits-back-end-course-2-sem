package it.sevenbits.web.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import it.sevenbits.core.model.User;
import it.sevenbits.web.security.userviews.AuthenticatedJwtToken;
import it.sevenbits.web.security.settings.JwtSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * It's a service that is needed to create tokens.
 */
@Service
public class JsonWebTokenService implements JwtTokenService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final JwtSettings settings;
    private final String authorities = "authorities";

    /**
     * A constructor that gets jwt settings.
     *
     * @param settings the settings.
     */
    public JsonWebTokenService(final JwtSettings settings) {
        this.settings = settings;
    }

    @Override
    public String createToken(final User user) {
        logger.debug("Generating token for {}", user.getUsername());
        Instant now = Instant.now();

        Claims claims = Jwts.claims()
                .setIssuer(settings.getTokenIssuer())
                .setIssuedAt(Date.from(now))
                .setSubject(user.getUsername())
                .setExpiration(Date.from(now.plus(settings.getTokenExpiredIn())));
        claims.put(authorities, user.getAuthorities());

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, settings.getTokenSigningKey())
                .compact();
    }

    @Override
    public Duration getTokenExpiredIn() {
        return settings.getTokenExpiredIn();
    }

    @Override
    public Authentication parseToken(final String token) {
        Jws<Claims> claims = Jwts.parser()
                .setSigningKey(settings.getTokenSigningKey())
                .parseClaimsJws(token);

        String subject = claims.getBody().getSubject();
        List<String> tokenAuthorities = claims.getBody().get(authorities, List.class);

        List<GrantedAuthority> authorities = tokenAuthorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new AuthenticatedJwtToken(subject, authorities);
    }
}
