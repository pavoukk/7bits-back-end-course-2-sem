package it.sevenbits.web.controllers;

import it.sevenbits.core.model.User;
import it.sevenbits.web.service.whoami.WhoAmIService;
import org.springframework.http.ResponseEntity;
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
    private WhoAmIService whoAmIService;

    /**
     * A constructor. Gets users repository to find the current user.
     * @param whoAmIService is a service that gets current user's information.
     *
     */
    public WhoAmIController(final WhoAmIService whoAmIService) {
        this.whoAmIService = whoAmIService;
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
                .body(whoAmIService.whoAmI());
    }
}
