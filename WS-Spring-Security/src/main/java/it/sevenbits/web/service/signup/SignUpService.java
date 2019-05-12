package it.sevenbits.web.service.signup;

import it.sevenbits.core.repository.users.IUsersRepository;
import it.sevenbits.core.repository.users.exceptions.UserRepositoryException;
import it.sevenbits.web.model.users.request.SignUpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * A service working with users repository to add new users.
 */
@Service
public class SignUpService implements ISignUpService {
    private final int userAlreadyExistsError = 409;
    private final IUsersRepository users;
    private PasswordEncoder passwordEncoder;

    /**
     * A constructor.
     *
     * @param users an users repository.
     * @param passwordEncoder a password encoder.
     */
    public SignUpService(final IUsersRepository users, final PasswordEncoder passwordEncoder) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public ResponseEntity<?> signUp(final SignUpRequest signUpRequest) {
        String username = signUpRequest.getUsername();
        String password = signUpRequest.getPassword();
        password = passwordEncoder.encode(password);
        try {
            users.createANewUser(username, password);
        } catch (UserRepositoryException e) {
            return ResponseEntity
                    .status(userAlreadyExistsError)
                    .build();
        }
        return ResponseEntity
                .noContent()
                .build();
    }
}
