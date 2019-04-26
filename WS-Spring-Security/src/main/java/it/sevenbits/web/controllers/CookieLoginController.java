package it.sevenbits.web.controllers;

import it.sevenbits.core.model.User;
import it.sevenbits.web.model.users.request.SignInRequest;
import it.sevenbits.web.security.service.JwtTokenService;
import it.sevenbits.web.service.sign_in.SignInService;
import it.sevenbits.web.service.sign_in.exceptions.SignInFailedException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * A cookie sign_in controller.
 */
@Controller
@RequestMapping("/sign_in")
public class CookieLoginController {
    private final SignInService signInService;
    private final JwtTokenService jwtTokenService;

    /**
     * A constructor that gets a sign in service and token service.
     *
     * @param signInService   the sign in service.
     * @param jwtTokenService the token service.
     */
    public CookieLoginController(final SignInService signInService, final JwtTokenService jwtTokenService) {
        this.signInService = signInService;
        this.jwtTokenService = jwtTokenService;
    }

    /**
     * The method sets a token as a cookie when a user signs in.
     *
     * @param signInRequest a sign in request.
     * @param response      a response.
     * @return a response entity with status code.
     */
    @PostMapping
    @ResponseBody
    public ResponseEntity create(final @RequestBody SignInRequest signInRequest, final HttpServletResponse response) {
        User user = null;
        try {
            user = signInService.signIn(signInRequest);
        } catch (SignInFailedException e) {
            return ResponseEntity.notFound().build();
        }
        String token = jwtTokenService.createToken(user);

        Cookie cookie = new Cookie("accessToken", token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge((int) (jwtTokenService.getTokenExpiredIn().toMillis() / 1000));
        response.addCookie(cookie);
        return ResponseEntity.noContent().build();
    }
}