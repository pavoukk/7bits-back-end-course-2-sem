package it.sevenbits.web.controllers;

import it.sevenbits.core.model.User;
import it.sevenbits.core.repository.users.IUsersRepository;
import it.sevenbits.web.model.users.request.UpdateUserRequest;
import it.sevenbits.web.service.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class UsersControllerTest {
    private IUsersRepository usersRepositoryMock;

    private UsersController usersController;

    @Before
    public void setUp() {
        usersRepositoryMock = Mockito.mock(IUsersRepository.class);
        usersController = new UsersController(usersRepositoryMock);
    }

    @Test
    public void getAllUsersTest() {
        List<User> users = new ArrayList<>();

        Mockito.when(usersRepositoryMock.findAll()).thenReturn(users);

        List<User> usersReturned = usersController.getAllUsers().getBody();
        Mockito.verify(usersRepositoryMock, Mockito.times(1)).findAll();

        assertEquals(users, usersReturned);
    }

    @Test
    public void getUserInfoTest() {
        String id = UUID.randomUUID().toString();
        User user = new User(id, "username", "PASSWORD", new ArrayList<>());

        Mockito.when(usersRepositoryMock.findById(id, true)).thenReturn(user);

        ResponseEntity<User> responseEntity = usersController.getUserInfo(id);
        Mockito.verify(usersRepositoryMock, Mockito.times(1)).findById(id, true);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(user, responseEntity.getBody());
    }
}