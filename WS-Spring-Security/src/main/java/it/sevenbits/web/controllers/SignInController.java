package it.sevenbits.web.controllers;

import it.sevenbits.core.model.User;
import it.sevenbits.web.model.users.request.SignInRequest;
import it.sevenbits.web.model.users.response.SignInResponse;
import it.sevenbits.web.security.service.JwtTokenService;
import it.sevenbits.web.service.signin.SignInService;
import it.sevenbits.web.service.signin.exceptions.SignInFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * A sign in controller.
 */
@Controller
@RequestMapping("/signin")
public class SignInController {
    private final SignInService signInService;
    private final JwtTokenService jwtTokenService;

    /**
     * A constructor that gets some beans.
     *
     * @param signInService   a service to work with repository.
     * @param jwtTokenService a service to work with tokens.
     */
    public SignInController(final SignInService signInService, final JwtTokenService jwtTokenService) {
        this.signInService = signInService;
        this.jwtTokenService = jwtTokenService;
    }

    /**
     * A method that works with POST requests. It creates some data
     * that must be stored on client's side.
     *
     * @param request some request.
     * @return some data to be stored on client's side.
     */
    @PostMapping
    @ResponseBody
    public ResponseEntity<SignInResponse> create(@RequestBody final SignInRequest request) {
        User user = null;

        try {
            user = signInService.signIn(request);
        } catch (SignInFailedException e) {
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
