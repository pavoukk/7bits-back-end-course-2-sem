package it.sevenbits.web.service.whoami;

import it.sevenbits.core.model.User;
import it.sevenbits.core.repository.users.IUsersRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * A service that gets a current user's information.
 */
@Service
public class WhoAmIService implements IWhoAmIService {
    private IUsersRepository usersRepository;

    /**
     * A constructor that takes users repository.
     * @param usersRepository the users repository.
     */
    public WhoAmIService(final IUsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public User whoAmI() {
        return usersRepository
                .findByUserName((String
                        .valueOf(SecurityContextHolder
                                .getContext()
                                .getAuthentication()
                                .getPrincipal())), true);
    }
}
