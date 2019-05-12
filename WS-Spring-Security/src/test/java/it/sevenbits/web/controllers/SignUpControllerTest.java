package it.sevenbits.web.controllers;

import it.sevenbits.web.model.users.request.SignUpRequest;
import it.sevenbits.web.service.signup.SignUpService;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

public class SignUpControllerTest {

    @Test
    public void createTest() {
        String username = "username";
        String password = "PASSWORD";
        SignUpRequest signUpRequest = new SignUpRequest(username, password);

        SignUpService signUpServiceMock = Mockito.mock(SignUpService.class);
        SignUpController signUpController = new SignUpController(signUpServiceMock);

        Mockito.when(signUpServiceMock.signUp(signUpRequest)).thenReturn(ResponseEntity.noContent().build());

        assertEquals(HttpStatus.NO_CONTENT, signUpController.create(signUpRequest).getStatusCode());
    }
}