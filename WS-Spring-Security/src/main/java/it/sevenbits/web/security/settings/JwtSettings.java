package it.sevenbits.web.security.settings;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

/**
 * Reads jwt settings from a file.
 */
@Component
public class JwtSettings {
    private final String tokenIssuer;
    private final String tokenSigningKey;
    private final int aTokenDuration;

    /**
     * A constructor.
     *
     * @param tokenIssuer     an issuer.
     * @param tokenSigningKey a signing key.
     * @param aTokenDuration  time to live for a token in minutes.
     */
    public JwtSettings(@Value("${jwt.issuer}") final String tokenIssuer,
                       @Value("${jwt.signingKey}") final String tokenSigningKey,
                       @Value("${jwt.aTokenDuration}") final int aTokenDuration) {
        this.tokenIssuer = tokenIssuer;
        this.tokenSigningKey = tokenSigningKey;
        this.aTokenDuration = aTokenDuration;
    }

    public String getTokenIssuer() {
        return tokenIssuer;
    }

    public byte[] getTokenSigningKey() {
        return tokenSigningKey.getBytes(StandardCharsets.UTF_8);
    }

    public Duration getTokenExpiredIn() {
        return Duration.ofMinutes(aTokenDuration);
    }
}
