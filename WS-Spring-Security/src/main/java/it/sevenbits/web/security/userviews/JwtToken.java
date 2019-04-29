package it.sevenbits.web.security.userviews;

import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * It's an unauthenticated user data that is used to set authentication process.
 *
 */
public class JwtToken extends AbstractAuthenticationToken {
    private final String token;

    /**
     * A constructor.
     * @param token some token to authenticate.
     */
    public JwtToken(final String token) {
        super(null);
        this.token = token;
        super.setAuthenticated(false);
    }

    @Override
    public void setAuthenticated(final boolean authenticated) {
        if (authenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted");
        }
        super.setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}