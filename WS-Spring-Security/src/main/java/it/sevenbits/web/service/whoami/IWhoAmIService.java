package it.sevenbits.web.service.whoami;

import it.sevenbits.core.model.User;

/**
 * A service that determines the current user.
 */
public interface IWhoAmIService {
    /**
     * The method determines a current user.
     * @return the user.
     */
    User whoAmI();
}
