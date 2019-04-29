package it.sevenbits.web.service.signin;

import it.sevenbits.core.model.User;
import it.sevenbits.web.model.users.request.SignInRequest;
import it.sevenbits.web.service.signin.exceptions.SignInFailedException;

/**
 * The interface describes sign in service. Contains one method.
 */
public interface ISignInService {
    /**
     * The method gets user data containing in signInRequest and
     * compares it with database data.
     * @param signInRequest the user data.
     * @return the user.
     * @throws SignInFailedException if some test has failed.
     */
    User signIn(SignInRequest signInRequest) throws SignInFailedException;
}
