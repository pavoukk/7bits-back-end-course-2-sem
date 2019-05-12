package it.sevenbits.web.service.user;

import it.sevenbits.web.model.users.request.UpdateUserRequest;
import org.springframework.http.ResponseEntity;

/**
 * A service that works with users repository.
 */
public interface IUserService {
    /**
     * The method updates user info.
     * @param id user's ID.
     * @param updateUserRequest a request with info to update.
     * @return some response containing status code.
     */
    ResponseEntity<?> updateUser(String id, UpdateUserRequest updateUserRequest);
}
