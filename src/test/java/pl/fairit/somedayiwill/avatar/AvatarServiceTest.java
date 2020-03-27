package pl.fairit.somedayiwill.avatar;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.MimeTypeUtils;
import pl.fairit.somedayiwill.exceptions.ResourceNotFoundException;
import pl.fairit.somedayiwill.user.AppUserRepository;
import pl.fairit.somedayiwill.user.TestUsers;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AvatarServiceTest {
    @Mock
    AvatarRepository avatarRepository;

    @Mock
    AppUserRepository userRepository;

    @InjectMocks
    AvatarService avatarService;

    @Test
    public void shouldSaveAvatarWhenValidFileProvidedAndAppUserExist() {
        var fileToSave = TestMultipartFile.aValidMultipartFileMock();
        var user = TestUsers.aDefaultUser();

        avatarService.saveAvatar(fileToSave, user);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void shouldThrowAvatarStorageExceptionWhenUnsupportedFileTypeGiven() {
        var user = TestUsers.aDefaultUser();
        var fileToSave = TestMultipartFile.anInvalidMultipartFileMock();

        assertThrows(AvatarStorageException.class, () -> avatarService.saveAvatar(fileToSave, user));
    }

    @Test
    public void shouldReturnUserAvatarWhenExistingUserIdGiven() throws IOException {
        var user = TestUsers.aDefaultUser();
        var avatar = TestAvatar.fromMultipartFile(TestMultipartFile.aValidMultipartFileMock());
        avatar.setUser(user);

        when(avatarRepository.findAvatarByUserId(user.getId())).thenReturn(Optional.ofNullable(avatar));
        var result = avatarService.getUsersAvatar(user.getId());

        assertEquals(result, avatar);
    }

    @Test
    public void shouldThrowResourceNotFoundExceptionWhenUserAvatarDoesNotExist() {
        var user = TestUsers.aDefaultUser();

        when(avatarRepository.findAvatarByUserId(user.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> avatarService.getUsersAvatar(user.getId()));
    }

    @Test
    public void shouldDeleteAvatarWhenUserIdGiven() {
        var userId = 3L;

        avatarService.deleteUsersAvatar(userId);

        verify(avatarRepository, times(1)).deleteAvatarByUserId(userId);
    }
}
