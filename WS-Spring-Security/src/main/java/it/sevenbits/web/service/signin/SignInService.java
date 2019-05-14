package it.sevenbits.web.service.signin;

import it.sevenbits.core.model.User;
import it.sevenbits.core.repository.users.IUsersRepository;
import it.sevenbits.web.model.users.request.SignInRequest;
import it.sevenbits.web.service.signin.exceptions.SignInFailedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * A sign in service. Checks if user's authentication data correct.
 */
@Service
public class SignInService implements ISignInService {
    private final IUsersRepository users;
    private final PasswordEncoder passwordEncoder;

    /**
     * A constructor. Takes users repository and password encoder.
     *
     * @param users           a repository
     * @param passwordEncoder a password encoder.
     */
    public SignInService(final IUsersRepository users, final PasswordEncoder passwordEncoder) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Gets user data and compares it with database data.
     *
     * @param signInRequest a request containing data.
     * @return a user if all tests are successful.
     * @throws SignInFailedException if something goes wrong,
     * for example user not found or the request has incorrect data.
     */
    @Override
    public User signIn(final SignInRequest signInRequest) throws SignInFailedException {
        User user = users.findByUserName(signInRequest.getUsername(), true);

        if (user == null) {
            throw new SignInFailedException(
                    "User '" + signInRequest.getUsername() + "' not found"
            );
        }

        if (!passwordEncoder.matches(signInRequest.getPassword(), user.getPassword())) {
            throw new SignInFailedException("Wrong password");
        }

        return new User(user.getId(), user.getUsername(), user.getAuthorities());
    }


}
