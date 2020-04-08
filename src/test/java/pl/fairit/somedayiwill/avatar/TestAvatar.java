package pl.fairit.somedayiwill.avatar;

import org.springframework.web.multipart.MultipartFile;
import pl.fairit.somedayiwill.user.AppUser;

import java.io.IOException;
import java.util.Random;

public class TestAvatar {
    public static Avatar fromMultipartFile(MultipartFile file) throws IOException {
        return Avatar.builder()
                .data(file.getBytes())
                .fileType(file.getContentType())
                .build();
    }

    public static Avatar fromMultipartFileWithUser(MultipartFile file, AppUser user) throws IOException {
        return Avatar.builder()
                .user(user)
                .data(file.getBytes())
                .fileType(file.getContentType())
                .build();
    }
}
