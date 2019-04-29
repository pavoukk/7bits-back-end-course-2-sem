package it.sevenbits.web.security.userviews;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * It is a fully authenticated object containing credentials. It extends
 * AbstractAuthenticationToken to make it easy for Spring Security to see the
 * object and to use it where it's needed. AbstractAuthenticationToken implements
 * Authentication interface that is a view of a user in Spring Security.
 * It describes a fully authorized user, 'fully authorized' means the data which
 * will be used in our application AFTER authentication process is finished.
 */
public class AuthenticatedJwtToken extends AbstractAuthenticationToken {
    private final String subject;

    /**
     * A constructor that gets username and user's authorities.
     *
     * @param subject     the username.
     * @param authorities the authorities.
     */
    public AuthenticatedJwtToken(final String subject,
                                 final Collection<GrantedAuthority> authorities) {
        super(authorities);
        this.subject = subject;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return subject;
    }
}