package pl.fairit.somedayiwill.user;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto getUserDashboard(@CurrentUser UserPrincipal userPrincipal) {
        return userService.getUserDtoByAppUserId(userPrincipal.getId());
    }

    @DeleteMapping("/me/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@CurrentUser UserPrincipal userPrincipal) {
        userService.deleteByUserId(userPrincipal.getId());
    }
}
