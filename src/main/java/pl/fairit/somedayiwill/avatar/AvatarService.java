package pl.fairit.somedayiwill.avatar;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.fairit.somedayiwill.exceptions.ResourceNotFoundException;
import pl.fairit.somedayiwill.user.AppUser;
import pl.fairit.somedayiwill.user.AppUserRepository;

import java.io.IOException;

@Service
@Transactional
@Slf4j
public class AvatarService {

    private final AvatarRepository avatarRepository;
    private final AppUserRepository appUserRepository;

    public AvatarService(AvatarRepository avatarRepository, AppUserRepository appUserRepository) {
        this.avatarRepository = avatarRepository;
        this.appUserRepository = appUserRepository;
    }

    public void saveAvatar(MultipartFile file, final AppUser user) {
        try {
            var fileType = file.getContentType();
            if (fileType != null && !fileType.equals(MimeTypeUtils.IMAGE_JPEG_VALUE) && !fileType.equals(MimeTypeUtils.IMAGE_PNG_VALUE)) {
                throw new AvatarStorageException("Unsupported file type.");
            }
            var avatar = Avatar.builder()
                    .data(file.getBytes())
                    .fileType(file.getContentType())
                    .user(user)
                    .build();

            user.setAvatar(avatar);
            appUserRepository.save(user);
        } catch (IOException exp) {
            log.error("Could not store file");
        }
    }

    public void deleteUsersAvatar(final Long userId) {
        avatarRepository.deleteAvatarByUserId(userId);
    }

    public Avatar getUsersAvatar(final Long userId) {
        return avatarRepository.findAvatarByUserId(userId).orElseThrow(ResourceNotFoundException::new);
    }

}
