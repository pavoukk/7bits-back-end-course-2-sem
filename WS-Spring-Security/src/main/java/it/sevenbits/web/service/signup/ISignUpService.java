package it.sevenbits.web.service.signup;

import it.sevenbits.web.model.users.request.SignUpRequest;
import org.springframework.http.ResponseEntity;

/**
 * A service that is needed to sign up users.
 */
public interface ISignUpService {
    /**
     * The method signs up a user.
     *
     * @param signUpRequest a request containing user info.
     * @return some response containing status code.
     */
    ResponseEntity<?> signUp(SignUpRequest signUpRequest);
}
