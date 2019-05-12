package it.sevenbits.web.service.signup;

import it.sevenbits.core.repository.users.IUsersRepository;
import it.sevenbits.core.repository.users.exceptions.UserRepositoryException;
import it.sevenbits.web.model.users.request.SignUpRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.Assert.*;

public class SignUpServiceTest {
    @Test
    public void signUp() {
        PasswordEncoder passwordEncoderMock = Mockito.mock(PasswordEncoder.class);
        IUsersRepository usersRepositoryMock = Mockito.mock(IUsersRepository.class);
        SignUpService signUpService = new SignUpService(usersRepositoryMock, passwordEncoderMock);

        String username = "username";
        String password = "password";
        SignUpRequest signUpRequest = new SignUpRequest(username, password);

        Mockito.when(passwordEncoderMock.encode(Mockito.anyString())).thenReturn(password);
        assertEquals(HttpStatus.NO_CONTENT, signUpService.signUp(signUpRequest).getStatusCode());
        try {
            Mockito.verify(usersRepositoryMock, Mockito.times(1)).createANewUser(username, password);
        } catch (UserRepositoryException e) {
            fail();
        }
    }
}