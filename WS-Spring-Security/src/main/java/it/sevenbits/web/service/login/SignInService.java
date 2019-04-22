package it.sevenbits.web.service.login;

import it.sevenbits.core.model.User;
import it.sevenbits.core.repository.IUsersRepository;
import it.sevenbits.web.model.SignInRequest;
import it.sevenbits.web.service.login.exceptions.LoginFailedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SignInService implements ISignInService {
    private final IUsersRepository users;
    private final PasswordEncoder passwordEncoder;

    public SignInService(IUsersRepository users, PasswordEncoder passwordEncoder){
        this.users = users;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User signIn(SignInRequest signInRequest) throws LoginFailedException {
        User user = users.findByUserName(signInRequest.getUsername());

        if (user == null) {
            throw new LoginFailedException(
                    "User '" + signInRequest.getUsername() + "' not found"
            );
        }

        if (!passwordEncoder.matches(signInRequest.getPassword(), user.getPassword())) {
            throw new LoginFailedException("Wrong password");
        }

        return new User(user.getUsername(), user.getAuthorities());
    }


}
