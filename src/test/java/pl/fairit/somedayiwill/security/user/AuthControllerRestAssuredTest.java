package pl.fairit.somedayiwill.security.user;

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
        given()
                .body("{\"name\":\"name\"," +
                        "\"email\": \"email@email.com\"," +
                        "\"password\": \"Password1\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/auth/signup")
                .then()
                .statusCode(201);
    }

    //    Response response =
//            given()
//                    .headers(
//                            "Authorization",
//                            "Bearer " + bearerToken,
//                            "Content-Type",
//                            ContentType.JSON,
//                            "Accept",
//                            ContentType.JSON)
//                    .when()
//                    .get(url)
//                    .then()
//                    .contentType(ContentType.JSON)
//                    .extract()
//                    .response();

}
