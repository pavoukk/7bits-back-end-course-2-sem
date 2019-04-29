package it.sevenbits.web.controllers;

import it.sevenbits.core.model.User;
import it.sevenbits.core.repository.users.IUsersRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Who am I controller. Is used to get information about current user.
 */
@Controller
@RequestMapping("/whoami")
public class WhoAmIController {
    private final IUsersRepository usersRepository;

    /**
     * A constructor. Gets users repository to find the current user.
     *
     * @param usersRepository the repository.
     */
    public WhoAmIController(final IUsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    /**
     * The method gets info about current user.
     *
     * @return current user.
     */
    @GetMapping
    @ResponseBody
    public ResponseEntity<User> whoAmI() {
        return ResponseEntity
                .ok()
                .body(usersRepository
                        .findByUserName((String
                                .valueOf(SecurityContextHolder
                                        .getContext()
                                        .getAuthentication()
                                        .getPrincipal()))));
    }
}
