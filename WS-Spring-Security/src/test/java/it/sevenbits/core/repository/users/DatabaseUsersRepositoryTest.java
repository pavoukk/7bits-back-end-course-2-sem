package it.sevenbits.core.repository.users;

import it.sevenbits.core.model.User;
import it.sevenbits.core.repository.users.exceptions.UserRepositoryException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;

import org.mockito.Mockito.*;


import static org.junit.Assert.*;

public class DatabaseUsersRepositoryTest {
    private JdbcOperations jdbcOperationsMock;
    private IUsersRepository usersRepository;

    @Before
    public void setUp() {
        jdbcOperationsMock = Mockito.mock(JdbcOperations.class);
        usersRepository = new DatabaseUsersRepository(jdbcOperationsMock);
    }

    @Test
    public void findByIdTest() {
//        Mockito.when(jdbcOperationsMock.query(
//                anyString(),
//                any(RowMapper.class),
//                anyBoolean(),
//                anyString()
//        ))
//                .then(answer -> {
//
//                });
        String id = UUID.randomUUID().toString();
        usersRepository.findById(id, true);
        Mockito.verify(jdbcOperationsMock, Mockito.times(1)).queryForMap(
                Mockito.eq("SELECT ID, username, PASSWORD FROM users" +
                        " WHERE enabled = ? AND ID = ?"),
                anyBoolean(),
                anyString()
        );
        Mockito.verify(jdbcOperationsMock, Mockito.times(1)).query(
                Mockito.eq("SELECT ID, AUTHORITY FROM authorities" +
                        " WHERE ID = ?"),
                any(RowMapper.class),
                anyString()
        );
    }

    @Test
    public void findByUserNameTest() {
        String username = "username";
        Map<String, Object> properties = new HashMap<>();
        properties.put("username", "username");
        properties.put("PASSWORD", "PASSWORD");
        properties.put("ID", UUID.randomUUID().toString());

        Mockito.when(jdbcOperationsMock.queryForMap(anyString(), anyBoolean(), anyString())).thenReturn(properties);
        usersRepository.findByUserName(username, true);

        Mockito.verify(jdbcOperationsMock, Mockito.times(1)).queryForMap(
                Mockito.eq("SELECT ID, username, PASSWORD FROM users" +
                        " WHERE enabled = ? AND username = ?"),
                anyBoolean(),
                anyString()
        );

        Mockito.verify(jdbcOperationsMock, Mockito.times(1)).query(
                Mockito.eq("SELECT ID, AUTHORITY FROM authorities" +
                        " WHERE ID = ?"),
                any(RowMapper.class),
                anyString()
        );
    }

    @Test
    public void updateUserTest() {
        String userId = UUID.randomUUID().toString();
        Map<String, Object> rawUser = new HashMap<>();
        rawUser.put("ID", userId);
        rawUser.put("username", "username");
        rawUser.put("PASSWORD", "PASSWORD");
        Mockito.when(jdbcOperationsMock.queryForMap(anyString(), anyBoolean(), anyString()))
                .thenReturn(rawUser);

        List<String> authorities = new ArrayList<>();
        authorities.add("USER");
        Mockito.when(jdbcOperationsMock.query(anyString(), any(RowMapper.class), anyString()))
                .thenReturn(authorities);
        boolean enabled = true;

        try {
            usersRepository.updateUser(userId, true, authorities);
        } catch (UserRepositoryException e) {
            fail();
        }
        Mockito.verify(jdbcOperationsMock, Mockito.times(1)).update(
                Mockito.eq("UPDATE users SET enabled = ? WHERE ID = ?"),
                Mockito.eq(enabled),
                Mockito.eq(userId));

        Mockito.verify(jdbcOperationsMock, Mockito.times(1)).update(
                Mockito.eq("DELETE FROM authorities WHERE ID = ?"),
                Mockito.eq(userId));

        Mockito.verify(jdbcOperationsMock, Mockito.times(1)).update(
                Mockito.eq("INSERT INTO authorities (ID, AUTHORITY) VALUES(?, ?)"),
                Mockito.eq(userId),
                Mockito.eq(authorities.get(0))
        );
    }

    @Test
    public void createANewUserTest() {
        String username = "username";
        String password = "PASSWORD";
        try {
            usersRepository.createANewUser(username, password);
        } catch (UserRepositoryException e) {
            fail();
        }
        Mockito.verify(jdbcOperationsMock, Mockito.times(1)).update(
                Mockito.eq("INSERT INTO users (username, PASSWORD, enabled, ID) VALUES(?, ?, ?, ?)"),
                Mockito.eq(username),
                Mockito.eq(password),
                anyBoolean(),
                anyString());

        Mockito.verify(jdbcOperationsMock, Mockito.times(1)).update(
                Mockito.eq("INSERT INTO authorities (AUTHORITY, ID) VALUES(?, ?)"),
                Mockito.eq("USER"),
                anyString());
    }
}