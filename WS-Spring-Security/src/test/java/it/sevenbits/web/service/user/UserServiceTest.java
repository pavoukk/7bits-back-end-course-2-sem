package it.sevenbits.web.service.user;

import it.sevenbits.core.repository.users.IUsersRepository;
import it.sevenbits.core.repository.users.exceptions.UserRepositoryException;
import it.sevenbits.web.model.users.request.UpdateUserRequest;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class UserServiceTest {

    @Test
    public void updateUserTest() {
        IUsersRepository usersRepositoryMock = Mockito.mock(IUsersRepository.class);
        UserService userService = new UserService(usersRepositoryMock);

        String id = UUID.randomUUID().toString();
        Boolean enabled = true;
        List<String> authorities = new ArrayList<>();
        authorities.add("USER");
        UpdateUserRequest updateUserRequest = new UpdateUserRequest(enabled, authorities);

        userService.updateUser(id, updateUserRequest);

        try {
            Mockito.verify(usersRepositoryMock, Mockito.times(1)).updateUser(id, enabled, authorities);
        } catch (UserRepositoryException e) {
            fail();
        }
    }
}