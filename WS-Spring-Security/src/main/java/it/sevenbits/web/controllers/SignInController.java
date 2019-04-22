package it.sevenbits.web.controllers;

import it.sevenbits.core.model.User;
import it.sevenbits.web.model.SignInRequest;
import it.sevenbits.web.model.SignInResponse;
import it.sevenbits.web.security.service.JwtTokenService;
import it.sevenbits.web.service.login.SignInService;
import it.sevenbits.web.service.login.exceptions.LoginFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/signin")
public class SignInController {
    private final SignInService signInService;
    private final JwtTokenService jwtTokenService;

    public SignInController(SignInService signInService, JwtTokenService jwtTokenService) {
        this.signInService = signInService;
        this.jwtTokenService = jwtTokenService;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<SignInResponse> create(@RequestBody SignInRequest request) {
        User user = null;

        try {
            user = signInService.signIn(request);
        } catch (LoginFailedException e) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .build();
        }

        String token = jwtTokenService.createToken(user);
        return ResponseEntity
                .ok()
                .body(new SignInResponse(token));
    }
}
