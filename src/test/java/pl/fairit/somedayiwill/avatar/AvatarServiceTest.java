package pl.fairit.somedayiwill.avatar;

import com.github.javafaker.App;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.MimeTypeUtils;
import pl.fairit.somedayiwill.user.AppUser;
import pl.fairit.somedayiwill.user.AppUserRepository;

@ExtendWith(MockitoExtension.class)
class AvatarServiceTest {

    @Mock
    AvatarRepository avatarRepository;

    @Mock
    AppUserRepository userRepository;

    @InjectMocks
    AvatarService avatarService;

    @Test(expected = AvatarStorageException.class)
    public void shouldThrowAvatarStorageExceptionWhenUnsupportedFileTypeGiven() {
        MockMultipartFile multipartFile = new MockMultipartFile("fileName", "originalFileName", MimeTypeUtils.IMAGE_GIF_VALUE, "fileContent".getBytes());
        AppUser appUser = new AppUser();

//        avatarService.saveAvatar(multipartFile, appUser);
    }

}
