package pl.fairit.somedayiwill.avatar;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.fairit.somedayiwill.exceptions.AvatarStorageException;
import pl.fairit.somedayiwill.exceptions.ResourceNotFoundException;
import pl.fairit.somedayiwill.user.AppUser;
import pl.fairit.somedayiwill.user.UserRepository;

import java.io.IOException;

@Service
@Transactional
public class AvatarService {

    private final AvatarRepository avatarRepository;
    private final UserRepository userRepository;

    public AvatarService(AvatarRepository avatarRepository, UserRepository userRepository) {
        this.avatarRepository = avatarRepository;
        this.userRepository = userRepository;
    }

    public Avatar saveAvatar(MultipartFile file, final AppUser user) {
        try {
            Avatar avatar = Avatar.builder()
                    .data(file.getBytes())
                    .fileType(file.getContentType())
                    .user(user)
                    .build();

            user.setAvatar(avatar);
            userRepository.save(user);
            return avatar;
        } catch (IOException exp) {
            throw new AvatarStorageException("Could not store file");
        }
    }

    public void deleteAvatarByUserId(final Long userId) {
        avatarRepository.deleteAvatarByUserId(userId);
    }

    public Avatar getUserAvatar(final Long userId) {
        return avatarRepository.findAvatarByUserId(userId).orElseThrow(ResourceNotFoundException::new);
    }

}
