package it.sevenbits.core.repository;

import it.sevenbits.core.model.User;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DatabaseUsersRepository implements IUsersRepository {
    private final JdbcOperations jdbcOperations;
    private final String AUTHORITY = "authority";
    private final String USERNAME = "username";
    private final String PASSWORD = "password";

    public DatabaseUsersRepository(final JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }


    @Override
    public User findByUserName(String username) {
        Map<String, Object> rawUser;
        try {
            rawUser = jdbcOperations.queryForMap(
                    "SELECT username, password FROM users" +
                            " WHERE enabled = true AND username = ?",
                    username
            );
        } catch (IncorrectResultSizeDataAccessException e){
            return null;
        }


        List<String> authorities = new ArrayList<>();
        jdbcOperations.query(
                "SELECT username, authority FROM authorities" +
                        " WHERE username = ?",
                resultSet -> {
                    String authority = resultSet.getString(AUTHORITY);
                    authorities.add(authority);
                },
                username
        );

        String password = String.valueOf(rawUser.get(PASSWORD));
        return new User(username, password, authorities);
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        Map<String, Object> rawUser = jdbcOperations.queryForMap("SELECT username, password FROM users u" +
                "WHERE u.enabled = true");


        for (String username: rawUser.keySet()) {
            List<String> authorities = new ArrayList<>();

            jdbcOperations.query("SELECT username, authority FROM authorities" +
                    "WHERE username = ?", resultSet -> {
                String authority = resultSet.getString(AUTHORITY);
                authorities.add(authority);
            }, username);

            users.add(new User(String.valueOf(rawUser.get(USERNAME)),
                    String.valueOf(rawUser.get(PASSWORD)),
                    authorities));
        }
        return users;
    }
}
