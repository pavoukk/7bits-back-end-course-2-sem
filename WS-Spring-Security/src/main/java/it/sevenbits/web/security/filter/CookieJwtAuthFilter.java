package it.sevenbits.web.security.filter;

import it.sevenbits.web.security.exceptions.JwtAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * An extension of JwtAuthFilter that gets a token from cookie.
 */
public class CookieJwtAuthFilter extends JwtAuthFilter {

    /**
     * A constructor that gets request matcher.
     * @param matcher the matcher.
     */
    public CookieJwtAuthFilter(final RequestMatcher matcher) {
        super(matcher);
    }

    @Override
    protected String takeToken(final HttpServletRequest request) throws AuthenticationException {
        Cookie cookie = WebUtils.getCookie(request, "accessToken");
        if (cookie != null) {
            return cookie.getValue();
        } else {
            throw new JwtAuthenticationException("Invalid 'accessToken' cookie: " + cookie);
        }
    }
}