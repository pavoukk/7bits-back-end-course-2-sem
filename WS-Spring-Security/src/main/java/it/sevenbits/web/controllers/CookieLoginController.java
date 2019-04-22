package it.sevenbits.web.controllers;

import it.sevenbits.core.model.User;
import it.sevenbits.web.model.SignInRequest;
import it.sevenbits.web.security.service.JwtTokenService;
import it.sevenbits.web.service.login.SignInService;
import it.sevenbits.web.service.login.exceptions.LoginFailedException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/login")
public class CookieLoginController {
    private final SignInService signInService;
    private final JwtTokenService jwtTokenService;
    // Constructor ...

    public CookieLoginController(SignInService signInService, JwtTokenService jwtTokenService) {
        this.signInService = signInService;
        this.jwtTokenService = jwtTokenService;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity create(@RequestBody SignInRequest signInRequest, HttpServletResponse response) {
        User user = null;
        try {
            user = signInService.signIn(signInRequest);
        } catch (LoginFailedException e) {
            return ResponseEntity.notFound().build();
        }
        String token = jwtTokenService.createToken(user);

        Cookie cookie = new Cookie("accessToken", token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge((int)(jwtTokenService.getTokenExpiredIn().toMillis() / 1000));
        response.addCookie(cookie);
        return ResponseEntity.noContent().build();
    }
}