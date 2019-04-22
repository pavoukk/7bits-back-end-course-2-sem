package it.sevenbits.web.service.login;

import it.sevenbits.core.model.User;
import it.sevenbits.web.model.SignInRequest;
import it.sevenbits.web.service.login.exceptions.LoginFailedException;

public interface ISignInService {
    User signIn(SignInRequest signInRequest) throws LoginFailedException;
}
