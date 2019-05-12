package it.sevenbits.web.service.user;

import it.sevenbits.core.repository.users.IUsersRepository;
import it.sevenbits.core.repository.users.exceptions.UserRepositoryException;
import it.sevenbits.web.model.users.request.UpdateUserRequest;
import org.springframework.http.ResponseEntity;

/**
 * A user service that works with users repository.
 */
public class UserService implements IUserService {
    private IUsersRepository usersRepository;

    /**
     * A constructor taking users repository.
     *
     * @param usersRepository the users repository.
     */
    public UserService(final IUsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public ResponseEntity<?> updateUser(final String id, final UpdateUserRequest updateUserRequest) {
        if (id == null || id.isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        if (updateUserRequest.isEnabled() == null &&
                (updateUserRequest.getAuthorities() == null || updateUserRequest.getAuthorities().isEmpty())) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        try {
            usersRepository.updateUser(id, updateUserRequest.isEnabled(), updateUserRequest.getAuthorities());
        } catch (UserRepositoryException e) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
        return ResponseEntity.noContent().build();
    }
}
