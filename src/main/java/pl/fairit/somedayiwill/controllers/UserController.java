package pl.fairit.somedayiwill.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.fairit.somedayiwill.security.CurrentUser;
import pl.fairit.somedayiwill.security.UserPrincipal;
import pl.fairit.somedayiwill.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public String getUserDashboard(@CurrentUser UserPrincipal userPrincipal) {
        return userPrincipal.getUsername();
    }
}
