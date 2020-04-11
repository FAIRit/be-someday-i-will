package pl.fairit.somedayiwill.avatar;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import pl.fairit.somedayiwill.exceptions.ResourceNotFoundException;
import pl.fairit.somedayiwill.user.AppUserRepository;
import pl.fairit.somedayiwill.user.TestUsers;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AvatarServiceTest {
    @Mock
    AvatarRepository avatarRepository;

    @Mock
    AppUserRepository userRepository;

    @InjectMocks
    AvatarService avatarService;

    private static Stream<MultipartFile> invalidAvatarArguments() {
        return Stream.of(
                TestMultipartFile.aMockWithGifFileType(),
                TestMultipartFile.aMockWithTextHtmlFileType(),
                TestMultipartFile.aMockWithApplicationJsonFileType()
        );
    }

    private static Stream<MultipartFile> validAvatarArguments() {
        return Stream.of(
                TestMultipartFile.aMockWithJpegFileType(),
                TestMultipartFile.aMockWithPngFileType()
        );
    }

    @Test
    void shouldReturnUserAvatarWhenExistingUserIdGiven() throws IOException {
        var user = TestUsers.aDefaultUser();
        var avatar = TestAvatar.fromMultipartFile(TestMultipartFile.aMockWithJpegFileType());
        avatar.setUser(user);

        when(avatarRepository.findAvatarByUserId(user.getId())).thenReturn(Optional.of(avatar));
        var result = avatarService.getUsersAvatar(user.getId());

        assertEquals(result, avatar);
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenUserAvatarDoesNotExist() {
        var user = TestUsers.aDefaultUser();

        when(avatarRepository.findAvatarByUserId(user.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> avatarService.getUsersAvatar(user.getId()));
    }

    @Test
    void shouldDeleteAvatarWhenUserIdGiven() {
        var userId = 3L;

        avatarService.deleteUsersAvatar(userId);

        verify(avatarRepository, times(1)).deleteAvatarByUserId(userId);
    }

    @ParameterizedTest
    @MethodSource("invalidAvatarArguments")
    void shouldThrowAvatarStorageException(final MultipartFile file) {
        var user = TestUsers.aDefaultUser();

        var exception = assertThrows(AvatarStorageException.class, () -> avatarService.saveAvatar(file, user));
        assertEquals("Unsupported file type.", exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("validAvatarArguments")
    void shouldSaveAvatars(final MultipartFile file) throws IOException {
        var user = TestUsers.aDefaultUser();

        var avatar = avatarService.saveAvatar(file, user);

        verify(userRepository, times(1)).save(user);
        assertArrayEquals(file.getBytes(), avatar.getData());
        assertEquals(file.getContentType(), avatar.getFileType());
        assertEquals(user, avatar.getUser());
    }
}
