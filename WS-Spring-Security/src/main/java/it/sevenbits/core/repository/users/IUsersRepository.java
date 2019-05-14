package it.sevenbits.core.repository.users;

import it.sevenbits.core.model.User;
import it.sevenbits.core.repository.users.exceptions.UserRepositoryException;

import java.util.List;

/**
 * An interface for users repository.
 */
public interface IUsersRepository {
    /**
     * The method searches for a user that has the same id.
     *
     * @param id      the id.
     * @param enabled is an account status.
     * @return some user if exists.
     */
    User findById(String id, boolean enabled);

    /**
     * The method searches for a user that has the same name as username.
     *
     * @param username the user's name.
     * @param enabled  is an account status.
     * @return some user if exists.
     */
    User findByUserName(String username, boolean enabled);

    /**
     * The method returns all users from a database.
     *
     * @return all users.
     */
    List<User> findAll();

    /**
     * The method updates user info.
     *
     * @param userId      is an user's id.
     * @param enabled     is an account status.
     * @param authorities is a list of user's authorities.
     * @throws UserRepositoryException if something goes wrong, for example the user doesn't exist.
     */
    void updateUser(String userId, Boolean enabled, List<String> authorities) throws UserRepositoryException;

    /**
     * The method creates a new user.
     *
     * @param username     a new username.
     * @param userPassword a new password.
     * @throws UserRepositoryException if something goes wrong, for example the user already exists.
     */
    void createANewUser(String username, String userPassword) throws UserRepositoryException;
}
