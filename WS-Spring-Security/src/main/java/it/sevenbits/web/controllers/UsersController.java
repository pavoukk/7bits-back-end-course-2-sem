package it.sevenbits.web.controllers;

import it.sevenbits.core.model.User;
import it.sevenbits.core.repository.users.IUsersRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

/**
 * The users controller.
 */
@Controller
@RequestMapping("/users")
public class UsersController {
    private final IUsersRepository usersRepository;

    /**
     * A constructor. Gets users repository to work with it.
     *
     * @param usersRepository the users repository.
     */
    public UsersController(final IUsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(usersRepository.findAll());
    }

    /**
     * The method searches for user information filtered by parameters.
     *
     * @param username a parameter to filter users.
     * @return user's info.
     */
    @GetMapping(value = "/{username}")
    @ResponseBody
    public ResponseEntity<User> getUserInfo(final @PathVariable("username") String username) {
        return Optional
                .ofNullable(usersRepository.findByUserName(username))
                .map(user -> ResponseEntity.ok().body(user))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
