package pl.fairit.somedayiwill.avatar;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.MimeTypeUtils;

public class TestMultipartFile {
    public static MockMultipartFile aMockWithJpegFileType() {
        return new MockMultipartFile("file_name", "original_name", MimeTypeUtils.IMAGE_JPEG_VALUE, "value".getBytes());
    }

    public static MockMultipartFile aMockWithGifFileType() {
        return new MockMultipartFile("file_name", "original_name", MimeTypeUtils.IMAGE_GIF_VALUE, "value".getBytes());
    }

    static MockMultipartFile aMockWithPngFileType() {
        return new MockMultipartFile("file_name", "original_name", MimeTypeUtils.IMAGE_PNG_VALUE, "value".getBytes());
    }

    static MockMultipartFile aMockWithApplicationJsonFileType() {
        return new MockMultipartFile("file_name", "original_name", MimeTypeUtils.APPLICATION_JSON_VALUE, "value"
                .getBytes());
    }

    static MockMultipartFile aMockWithTextHtmlFileType() {
        return new MockMultipartFile("file_name", "original_name", MimeTypeUtils.TEXT_HTML_VALUE, "value".getBytes());
    }
}
