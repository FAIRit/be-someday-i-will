package pl.fairit.somedayiwill.avatar;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.MimeTypeUtils;
import pl.fairit.somedayiwill.exceptions.ResourceNotFoundException;
import pl.fairit.somedayiwill.user.AppUser;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AvatarServiceMockitoTest {
    @Mock
    AvatarRepository avatarRepository;

    @InjectMocks
    AvatarService avatarService;

    @Test
    public void shouldReturnUserAvatarWhenUserIdGiven() {
        var appUser = retrieveAppUser();
        var avatar = Avatar.builder()
                .data("fileContent".getBytes())
                .fileType(MimeTypeUtils.IMAGE_JPEG_VALUE)
                .user(appUser)
                .build();

        when(avatarRepository.findAvatarByUserId(appUser.getId())).thenReturn(java.util.Optional.ofNullable(avatar));

        var result = avatarService.getUsersAvatar(appUser.getId());
        assertEquals(result, avatar);
    }

    @Test
    public void shouldThrowResourceNotFoundExceptionWhenUserAvatarDoesNotExist() {
        var appUser = retrieveAppUser();

        when(avatarRepository.findAvatarByUserId(appUser.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> avatarService.getUsersAvatar(appUser.getId()));
    }

    @Test
    public void shouldThrowAvatarStorageExceptionWhenUnsupportedFileTypeGiven() {
        var appUser = retrieveAppUser();
        var file = new MockMultipartFile("file_name", "original_name", MimeTypeUtils.IMAGE_GIF_VALUE, "value".getBytes());

        assertThrows(AvatarStorageException.class, () -> avatarService.saveAvatar(file, appUser));
    }

    private AppUser retrieveAppUser() {
        return AppUser.builder()
                .id(5L)
                .build();
    }
}
