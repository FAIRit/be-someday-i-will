package pl.fairit.somedayiwill.user;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.fairit.somedayiwill.security.user.CurrentUser;
import pl.fairit.somedayiwill.security.user.UserPrincipal;

@RestController
@RequestMapping("/users/me")
public class AppUserController {
    private final AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public AppUserDto getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return appUserService.getUserDtoByAppUserId(userPrincipal.getId());
    }

    @DeleteMapping
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@CurrentUser UserPrincipal userPrincipal) {
        appUserService.deleteUser(userPrincipal.getId());
    }
}
