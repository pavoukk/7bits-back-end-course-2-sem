package it.sevenbits.web.controllers;

import it.sevenbits.core.model.User;
import it.sevenbits.core.repository.IUsersRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UsersController {
    private final IUsersRepository usersRepository;
    public UsersController(IUsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(usersRepository.findAll());
    }

    @GetMapping(value = "/{username}")
    @ResponseBody
    public ResponseEntity<User> getUserInfo(final @PathVariable("username") String username) {
        return Optional
                .ofNullable( usersRepository.findByUserName(username) )
                .map( user -> ResponseEntity.ok().body(user) )
                .orElseGet( () -> ResponseEntity.notFound().build() );
    }

}
