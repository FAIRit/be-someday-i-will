package pl.fairit.somedayiwill.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.get;

@SpringBootTest()
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class AppUserControllerRestAssuredTest {
    @Test
    public void shouldReturnUnauthorizedWhenNoCredentialsProvided() {
        get("/users/me")
                .then()
                .assertThat()
                .statusCode(401);
    }
}

