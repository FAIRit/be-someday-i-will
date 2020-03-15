package pl.fairit.somedayiwill.security.user;

import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.given;

@SpringBootTest()
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class AuthControllerRestAssuredTest {
    @Test
    public void whenRequestedPostThenUserCreated() {
        var faker = new Faker();
        var requestBody = new StringBuffer()
                .append("{\"name\":\"")
                .append(faker.name().name())
                .append("\", \"email\": \"")
                .append(faker.internet().emailAddress())
                .append("\", \"password\": \"")
                .append(faker.internet().password(8, 16, true, true, true))
                .append("\"}")
                .toString();

        given()
                .body(requestBody)
                .contentType(ContentType.JSON)
                .when()
                .post("/auth/signup")
                .then()
                .statusCode(201);
    }
}
