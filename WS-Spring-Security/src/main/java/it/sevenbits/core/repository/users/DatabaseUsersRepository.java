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
    private final String AUTHORITY = "authority";
    private final String USERNAME = "username";
    private final String PASSWORD = "password";
    private final String ID = "id";

    /**
     * A constructor. Gets JdbcOperations object to work with database.
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
                    resultSet ->  {
                        rawUser.put(PASSWORD, resultSet.getString(PASSWORD));
                        rawUser.put(ID, resultSet.getString(ID));
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
                    String authority = resultSet.getString(AUTHORITY);
                    authorities.add(authority);
                },
                rawUser.get(ID)
        );

        String password = String.valueOf(rawUser.get(PASSWORD));
        return authorities.isEmpty() ? null : new User(username, password, authorities);
    }

    @Override
    public List<User> findAll() {
        List<User> users = Collections.synchronizedList(new ArrayList<>());
         Map<String, String> rawUser = new ConcurrentHashMap<>();
         Map<String, String> userId = new ConcurrentHashMap<>();
                 jdbcOperations.query("SELECT id, username, password FROM users " +
                "WHERE enabled = true", resultSet -> {
             String username = resultSet.getString(USERNAME);
             String password = resultSet.getString(PASSWORD);
             String id = resultSet.getString(ID);
             rawUser.put(username, password);
             userId.put(username, id);
         });


        for (String username: rawUser.keySet()) {
            List<String> authorities = new ArrayList<>();

            jdbcOperations.query("SELECT id, authority FROM authorities " +
                    "WHERE id = ?", resultSet -> {
                String authority = resultSet.getString(AUTHORITY);
                authorities.add(authority);
            }, userId.get(username));

            users.add(new User(username, rawUser.get(username),
                    authorities));
        }
        return users;
    }
}
