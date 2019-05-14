package it.sevenbits.web.controllers;

import it.sevenbits.core.model.User;
import it.sevenbits.core.repository.users.IUsersRepository;
import it.sevenbits.web.model.users.request.UpdateUserRequest;
import it.sevenbits.web.service.user.IUserService;
import it.sevenbits.web.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

/**
 * The users controller.
 */
@Controller
@RequestMapping("/users")
public class UsersController {
    private final IUsersRepository usersRepository;
    private IUserService userService;

    /**
     * A constructor. Gets users repository to work with it.
     *
     * @param usersRepository the users repository.
     */
    public UsersController(final IUsersRepository usersRepository) {
        this.usersRepository = usersRepository;
        this.userService = new UserService(usersRepository);
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(usersRepository.findAll());
    }

    /**
     * The method searches for user information filtered by parameters.
     *
     * @param id a parameter to filter users.
     * @return user's info.
     */
    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<User> getUserInfo(@PathVariable("id") final String id) {
        return Optional
                .ofNullable(usersRepository.findById(id, true))
                .map(user -> ResponseEntity.ok().body(user))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * The method updates user info.
     *
     * @param id                an user's id.
     * @param updateUserRequest an update user request containing info to update.
     * @return a response containing a status code.
     */
    @PatchMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<?> updateUserInfo(@PathVariable("id") final String id, @RequestBody final UpdateUserRequest updateUserRequest) {
        return userService.updateUser(id, updateUserRequest);
    }

}
