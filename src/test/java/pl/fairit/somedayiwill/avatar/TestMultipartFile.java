package pl.fairit.somedayiwill.avatar;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.MimeTypeUtils;

public class TestMultipartFile {
    public static MockMultipartFile aValidMultipartFileMock() {
        return new MockMultipartFile("file_name", "original_name", MimeTypeUtils.IMAGE_JPEG_VALUE, "value".getBytes());
    }

    public static MockMultipartFile anInvalidMultipartFileMock() {
        return new MockMultipartFile("file_name", "original_name", MimeTypeUtils.IMAGE_GIF_VALUE, "value".getBytes());
    }
}
