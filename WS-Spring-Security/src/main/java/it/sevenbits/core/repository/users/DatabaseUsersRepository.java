package it.sevenbits.core.repository.users;

import it.sevenbits.core.model.User;
import it.sevenbits.core.repository.users.exceptions.UserRepositoryException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.UUID;

/**
 * A repository for users.
 */
public class DatabaseUsersRepository implements IUsersRepository {
    private final JdbcOperations jdbcOperations;
    /**
     * A constant value for authority.
     */
    public static final String AUTHORITY = "authority";
    /**
     * A constant value for username.
     */
    public static final String USERNAME = "username";
    /**
     * A constant value for password.
     */
    public static final String PASSWORD = "password";
    /**
     * A constant value for id.
     */
    public static final String ID = "id";

    /**
     * A constructor. Gets JdbcOperations object to work with database.
     *
     * @param jdbcOperations is needed to work with database.
     */
    public DatabaseUsersRepository(final JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }


    @Override
    public User findById(final String userId, final boolean enabled) {
        Map<String, Object> rawUser;
        try {
            rawUser = jdbcOperations.queryForMap(
                    "SELECT id, username, password FROM users" +
                            " WHERE enabled = ? AND id = ?",
                    enabled, userId);
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }

        List<String> authorities;
        authorities = jdbcOperations.query(
                "SELECT id, authority FROM authorities" +
                        " WHERE id = ?",
                new RowMapper<String>() {
                    @Override
                    public String mapRow(final ResultSet resultSet, final int i) throws SQLException {
                        return resultSet.getString(DatabaseUsersRepository.AUTHORITY);
                    }
                },
                userId
        );
        String password = (String) rawUser.get(DatabaseUsersRepository.PASSWORD);
        String username = (String) rawUser.get(DatabaseUsersRepository.USERNAME);
        return authorities.isEmpty() ? null : new User(userId, username, password, authorities);
    }

    @Override
    public User findByUserName(final String username, final boolean enabled) {
        Map<String, Object> rawUser = new ConcurrentHashMap<>();
        try {
            rawUser = jdbcOperations.queryForMap(
                    "SELECT id, username, password FROM users" +
                            " WHERE enabled = ? AND username = ?",
                    enabled,
                    username);
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }

        String userId = (String) rawUser.get(DatabaseUsersRepository.ID);
        List<String> authorities;
        authorities = jdbcOperations.query(
                "SELECT id, authority FROM authorities" +
                        " WHERE id = ?",
                new RowMapper<String>() {
                    @Override
                    public String mapRow(final ResultSet resultSet, final int i) throws SQLException {
                        return resultSet.getString(DatabaseUsersRepository.AUTHORITY);
                    }
                },
                userId);

        String password = (String) rawUser.get(DatabaseUsersRepository.PASSWORD);
        String id = (String) rawUser.get(DatabaseUsersRepository.ID);
        return authorities.isEmpty() ? null : new User(id, username, password, authorities);
    }

    @Override
    public List<User> findAll() {
        List<User> users = Collections.synchronizedList(new ArrayList<>());
        Map<String, String> rawUser = new ConcurrentHashMap<>();
        Map<String, String> userId = new ConcurrentHashMap<>();
        jdbcOperations.query("SELECT id, username, password FROM users " +
                "WHERE enabled = true", new RowMapper<Object>() {
            @Override
            public Object mapRow(final ResultSet resultSet, final int i) throws SQLException {

                String id = resultSet.getString(DatabaseUsersRepository.ID);
                String username = resultSet.getString(DatabaseUsersRepository.USERNAME);
                String password = resultSet.getString(DatabaseUsersRepository.PASSWORD);
                rawUser.put(username, password);
                userId.put(username, id);
                return rawUser;
            }
        });


        for (String username : rawUser.keySet()) {
            List<String> authorities = new ArrayList<>();
            jdbcOperations.query("SELECT id, authority FROM authorities " +
                    "WHERE id = ?", new RowMapper<Object>() {
                @Override
                public String mapRow(final ResultSet resultSet, final int i) throws SQLException {
                    String authority = resultSet.getString(DatabaseUsersRepository.AUTHORITY);
                    authorities.add(authority);
                    return authority;
                }
            }, userId.get(username));


            users.add(new User(userId.get(username), username, rawUser.get(username),
                    authorities));
        }
        return users;
    }

    @Override
    public void updateUser(final String userId, final Boolean enabled, final List<String> authorities)
            throws UserRepositoryException {
        if (findById(userId, true) == null && findById(userId, false) == null) {
            throw new UserRepositoryException("The user doesn't exist");
        }

        if (enabled != null) {
            jdbcOperations.update("UPDATE users SET enabled = ? WHERE id = ?", enabled, userId);
        }

        if (authorities != null && !authorities.isEmpty()) {
            jdbcOperations.update("DELETE FROM authorities WHERE id = ?", userId);
            for (String el : authorities) {
                jdbcOperations.update("INSERT INTO authorities (id, authority) VALUES(?, ?)", userId, el);
            }
        }
    }

    @Override
    public void createANewUser(final String username, final String userPassword) throws UserRepositoryException {
        if (findByUserName(username, true) != null || findByUserName(username, false) != null) {
            throw new UserRepositoryException("The user already exists");
        }
        String userId = UUID.randomUUID().toString();
        jdbcOperations.update("INSERT INTO users (username, password, enabled, id) VALUES(?, ?, ?, ?)",
                username, userPassword, true, userId);
        jdbcOperations.update("INSERT INTO authorities (authority, id) VALUES(?, ?)", "USER", userId);
    }


}
