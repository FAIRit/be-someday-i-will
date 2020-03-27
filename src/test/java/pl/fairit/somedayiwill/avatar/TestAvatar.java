package pl.fairit.somedayiwill.avatar;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class TestAvatar {
    public static Avatar fromMultipartFile(MultipartFile file) throws IOException {
        return Avatar.builder()
                .data(file.getBytes())
                .fileType(file.getContentType())
                .build();
    }
}
