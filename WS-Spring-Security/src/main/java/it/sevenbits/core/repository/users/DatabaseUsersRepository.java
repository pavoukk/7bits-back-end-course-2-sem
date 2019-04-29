package it.sevenbits.core.repository.users;

import it.sevenbits.core.model.User;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A repository for users.
 */
public class DatabaseUsersRepository implements IUsersRepository {
    private final JdbcOperations jdbcOperations;
    private final String authority = "authority";
    private final String userName = "username";
    private final String password = "password";
    private final String id = "id";

    /**
     * A constructor. Gets JdbcOperations object to work with database.
     *
     * @param jdbcOperations is needed to work with database.
     */
    public DatabaseUsersRepository(final JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }


    @Override
    public User findByUserName(final String username) {
        Map<String, String> rawUser = new ConcurrentHashMap<>();
        try {
            jdbcOperations.query(
                    "SELECT id, username, password FROM users" +
                            " WHERE enabled = true AND username = ?",
                    resultSet -> {
                        rawUser.put(password, resultSet.getString(password));
                        rawUser.put(id, resultSet.getString(id));
                    },
                    username);
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }

        List<String> authorities = new ArrayList<>();
        jdbcOperations.query(
                "SELECT id, authority FROM authorities" +
                        " WHERE id = ?",
                resultSet -> {
                    String authority = resultSet.getString(this.authority);
                    authorities.add(authority);
                },
                rawUser.get(id)
        );

        String password = rawUser.get(this.password);
        String id = rawUser.get(this.id);
        return authorities.isEmpty() ? null : new User(id, username, password, authorities);
    }

    @Override
    public List<User> findAll() {
        List<User> users = Collections.synchronizedList(new ArrayList<>());
        Map<String, String> rawUser = new ConcurrentHashMap<>();
        Map<String, String> userId = new ConcurrentHashMap<>();
        jdbcOperations.query("SELECT id, username, password FROM users " +
                "WHERE enabled = true", resultSet -> {
            String id = resultSet.getString(this.id);
            String username = resultSet.getString(userName);
            String password = resultSet.getString(this.password);
            rawUser.put(username, password);
            userId.put(username, id);
        });


        for (String username : rawUser.keySet()) {
            List<String> authorities = new ArrayList<>();
            jdbcOperations.query("SELECT id, authority FROM authorities " +
                    "WHERE id = ?", resultSet -> {
                String authority = resultSet.getString(this.authority);
                authorities.add(authority);
            }, userId.get(username));

            users.add(new User(userId.get(username), username, rawUser.get(username),
                    authorities));
        }
        return users;
    }
}
