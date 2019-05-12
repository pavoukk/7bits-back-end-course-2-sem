package it.sevenbits.web.controllers;

import it.sevenbits.core.model.User;
import it.sevenbits.web.service.whoami.WhoAmIService;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.*;

public class WhoAmIControllerTest {

    @Test
    public void whoAmITest() {
        WhoAmIService whoAmIServiceMock = Mockito.mock(WhoAmIService.class);
        WhoAmIController whoAmIController = new WhoAmIController(whoAmIServiceMock);
        User user = new User(UUID.randomUUID().toString(), "username", "PASSWORD", new ArrayList<>());

        Mockito.when(whoAmIServiceMock.whoAmI()).thenReturn(user);

        ResponseEntity<User> responseEntity = whoAmIController.whoAmI();
        Mockito.verify(whoAmIServiceMock, Mockito.times(1)).whoAmI();

        assertEquals(user, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}