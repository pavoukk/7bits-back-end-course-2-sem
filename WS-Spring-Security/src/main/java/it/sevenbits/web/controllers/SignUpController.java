package it.sevenbits.web.controllers;

import it.sevenbits.web.model.users.request.SignUpRequest;
import it.sevenbits.web.service.signup.SignUpService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * A controller that is needed to sign up users.
 */
@Controller
@RequestMapping("/signup")
public class SignUpController {
    private SignUpService signUpService;

    /**
     * A constructor.
     *
     * @param signUpService a service to work with repository.
     */
    public SignUpController(final SignUpService signUpService) {
        this.signUpService = signUpService;
    }

    /**
     * The method creates a new user using request's info.
     *
     * @param signUpRequest the request containing a new user's info.
     * @return a response containing a status code.
     */
    @PostMapping
    @ResponseBody
    ResponseEntity<?> create(@RequestBody final SignUpRequest signUpRequest) {
        signUpService.signUp(signUpRequest);
        return ResponseEntity
                .noContent()
                .build();
    }
}
