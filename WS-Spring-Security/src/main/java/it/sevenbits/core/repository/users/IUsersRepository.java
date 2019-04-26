package it.sevenbits.core.repository.users;

import it.sevenbits.core.model.User;

import java.util.List;

/**
 * An interface for users repository.
 */
public interface IUsersRepository {
    /**
     * The method searches for a user that has the same name as username.
     *
     * @param username the user's name.
     * @return the found user.
     */
    User findByUserName(String username);

    /**
     * The method returns all users from a database.
     *
     * @return all users.
     */
    List<User> findAll();
}
