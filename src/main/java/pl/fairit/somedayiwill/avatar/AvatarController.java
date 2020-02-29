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
import pl.fairit.somedayiwill.user.CurrentUser;
import pl.fairit.somedayiwill.user.UserPrincipal;
import pl.fairit.somedayiwill.user.UserService;

@RestController
@RequestMapping("/users/me/avatar")
public class AvatarController {

    private final AvatarService avatarService;
    private final UserService userService;

    public AvatarController(AvatarService avatarService, UserService userService) {
        this.avatarService = avatarService;
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Resource> getAvatar(@CurrentUser UserPrincipal userPrincipal) {
        final Avatar userAvatar = avatarService.getUserAvatar(userPrincipal.getId());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(userAvatar.getFileType()))
                .body(new ByteArrayResource(userAvatar.getData()));
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Resource> uploadAvatar(@RequestParam("file") MultipartFile file, @CurrentUser UserPrincipal userPrincipal) {
        final AppUser existingUser = userService.findExistingUser(userPrincipal.getId());
        final Avatar savedAvatar = avatarService.saveAvatar(file, existingUser);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(savedAvatar.getFileType()))
                .body(new ByteArrayResource(savedAvatar.getData()));
    }

    @DeleteMapping
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAvatar(@CurrentUser UserPrincipal userPrincipal) {
        avatarService.deleteAvatarByUserId(userPrincipal.getId());
    }


}
