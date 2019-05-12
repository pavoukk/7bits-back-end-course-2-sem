package it.sevenbits.web.service.signin;

import it.sevenbits.core.model.User;
import it.sevenbits.core.repository.users.IUsersRepository;
import it.sevenbits.web.model.users.request.SignInRequest;
import it.sevenbits.web.service.signin.exceptions.SignInFailedException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class SignInServiceTest {
    private IUsersRepository usersRepositoryMock;
    private ISignInService signInService;
    private PasswordEncoder passwordEncoderMock;

    @Before
    public void setUp() {
        usersRepositoryMock = Mockito.mock(IUsersRepository.class);
        passwordEncoderMock = Mockito.mock(PasswordEncoder.class);
        signInService = new SignInService(usersRepositoryMock, passwordEncoderMock);
    }

    @Test
    public void testSignIn() {
        String username = "username";
        String password = "PASSWORD";
        String id = UUID.randomUUID().toString();
        List<String> authorities = new ArrayList<>();
        authorities.add("USER");

        User user = new User(id, username, password, authorities);
        SignInRequest signInRequest = new SignInRequest(username, password);

        Mockito.when(usersRepositoryMock.findByUserName(username, true)).thenReturn(user);
        Mockito.when(passwordEncoderMock.matches(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
        User userReturned = null;

        try {
            userReturned = signInService.signIn(signInRequest);
        } catch (SignInFailedException e) {
            fail();
        }
        Mockito.verify(usersRepositoryMock, Mockito.times(1)).findByUserName(username, true);

        assertNotEquals(userReturned, null);
        assertEquals(id, userReturned.getId());
        assertEquals(username, userReturned.getUsername());
        assertEquals(authorities, userReturned.getAuthorities());
    }

    @Test(expected = SignInFailedException.class)
    public void testSignInError() throws SignInFailedException {
        String username = "username";
        String password = "PASSWORD";
        SignInRequest signInRequest = new SignInRequest(username, password);

        Mockito.when(usersRepositoryMock.findByUserName(username, true)).thenReturn(null);


        signInService.signIn(signInRequest);
    }

    @Test(expected = SignInFailedException.class)
    public void testSignInPasswordsMismatch() throws SignInFailedException {
        String username = "username";
        String password = "PASSWORD";
        String id = UUID.randomUUID().toString();
        List<String> authorities = new ArrayList<>();
        authorities.add("USER");

        User user = new User(id, username, password, authorities);
        SignInRequest signInRequest = new SignInRequest(username, password);

        Mockito.when(usersRepositoryMock.findByUserName(username, true)).thenReturn(user);
        Mockito.when(passwordEncoderMock.matches(Mockito.anyString(), Mockito.anyString())).thenReturn(false);
        User userReturned = null;

            signInService.signIn(signInRequest);
    }

}