package pl.fairit.somedayiwill.avatar;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.fairit.somedayiwill.user.AppUser;
import pl.fairit.somedayiwill.security.user.CurrentUser;
import pl.fairit.somedayiwill.security.user.UserPrincipal;
import pl.fairit.somedayiwill.user.AppUserService;

@RestController
@RequestMapping("/users/me/avatar")
public class AvatarController {

    private final AvatarService avatarService;
    private final AppUserService appUserService;

    public AvatarController(AvatarService avatarService, AppUserService appUserService) {
        this.avatarService = avatarService;
        this.appUserService = appUserService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Resource> getAvatar(@CurrentUser UserPrincipal userPrincipal) {
        final Avatar userAvatar = avatarService.getUsersAvatar(userPrincipal.getId());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(userAvatar.getFileType()))
                .body(new ByteArrayResource(userAvatar.getData()));
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadAvatar(@RequestParam("file") MultipartFile file, @CurrentUser UserPrincipal userPrincipal) {
        final AppUser existingUser = appUserService.getExistingUser(userPrincipal.getId());
        avatarService.saveAvatar(file, existingUser);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAvatar(@CurrentUser UserPrincipal userPrincipal) {
        avatarService.deleteUsersAvatar(userPrincipal.getId());
    }
}
