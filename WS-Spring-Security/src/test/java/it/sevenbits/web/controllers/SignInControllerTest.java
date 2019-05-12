package it.sevenbits.web.controllers;

import it.sevenbits.core.model.User;
import it.sevenbits.web.model.users.request.SignInRequest;
import it.sevenbits.web.model.users.response.SignInResponse;
import it.sevenbits.web.security.service.JwtTokenService;
import it.sevenbits.web.service.signin.SignInService;
import it.sevenbits.web.service.signin.exceptions.SignInFailedException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

import static org.junit.Assert.*;

public class SignInControllerTest {
    private SignInController signInController;
    private SignInService signInServiceMock;
    private JwtTokenService jwtTokenServiceMock;
    @Before
    public void setUp() {
        signInServiceMock = Mockito.mock(SignInService.class);
        jwtTokenServiceMock = Mockito.mock(JwtTokenService.class);
        signInController = new SignInController(signInServiceMock, jwtTokenServiceMock);

    }

    @Test
    public void createTest() {
        String username = "username";
        String password = "PASSWORD";
        SignInRequest signInRequest = new SignInRequest(username, password);
        User user = new User(UUID.randomUUID().toString(), username, password, new ArrayList<>());
        String token = "some token";

        try {
            Mockito.when(signInServiceMock.signIn(signInRequest)).thenReturn(user);
        } catch (SignInFailedException e) {
            fail();
        }
        Mockito.when(jwtTokenServiceMock.createToken(Mockito.any(User.class))).thenReturn(token);

        ResponseEntity<SignInResponse> responseEntity = signInController.create(signInRequest);

        try {
            Mockito.verify(signInServiceMock, Mockito.times(1)).signIn(signInRequest);
        } catch (SignInFailedException e) {
            fail();
        }

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(token, Objects.requireNonNull(responseEntity.getBody()).getToken());
    }

    @Test
    public void createExceptionTest() {
        String username = "user";
        String password = "not found";
        SignInRequest signInRequest = new SignInRequest(username, password);

        try {
            Mockito.when(signInServiceMock.signIn(signInRequest)).thenThrow(SignInFailedException.class);
        } catch (SignInFailedException e) {
            fail();
        }

        ResponseEntity<SignInResponse> responseEntity = signInController.create(signInRequest);
        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }
}