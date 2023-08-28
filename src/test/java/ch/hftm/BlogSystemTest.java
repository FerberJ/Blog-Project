package ch.hftm;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.keycloak.client.KeycloakTestClient;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

@QuarkusTest
class BlogSystemTest {

    private final String ADMIN = "alice";
    private final String USER = "bob";
    private final String OTHER_USER = "steve";

    private KeycloakTestClient keycloakClient = new KeycloakTestClient();
    private String jsonPayloadBlog = """
            {
                "title": "HHH",
                "content": "string"
              }
              """;
    private String jsonPayloadComment = """
            {
                "comment": "Hello World"
              }
              """;

    @Test
    @Order(1)
    public void adminGet() {
        RestAssured.given().auth().oauth2(getAccessToken(ADMIN))
                .when().get("/blogs")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(2)
    public void userGet() {
        RestAssured.given().auth().oauth2(getAccessToken(USER))
                .when().get("/blogs")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(3)
    public void everyoneGet() {
        RestAssured.given()
                .when().get("/blogs")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(4)
    public void adminPost() {
        RestAssured.given().auth().oauth2(getAccessToken(ADMIN))
                .contentType(ContentType.JSON)
                .body(jsonPayloadBlog)
                .when().post("/blogs")
                .then()
                .statusCode(201);
    }

    @Test
    @Order(5)
    public void userPost() {
        RestAssured.given().auth().oauth2(getAccessToken(USER))
                .contentType(ContentType.JSON)
                .body(jsonPayloadBlog)
                .when().post("/blogs")
                .then()
                .statusCode(201);
    }

    @Test
    @Order(6)
    public void everyonePost() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(jsonPayloadBlog)
                .when().post("/blogs")
                .then()
                .statusCode(401);
    }

    @Test
    @Order(7)
    public void adminGetId() {
        String id = createBlog(ADMIN);

        RestAssured.given().auth().oauth2(getAccessToken(ADMIN))
                .when().get("/blogs/" + id)
                .then()
                .statusCode(200);
    }

    @Test
    @Order(8)
    public void userGetId() {
        String id = createBlog(USER);

        RestAssured.given().auth().oauth2(getAccessToken(ADMIN))
                .when().get("/blogs/" + id)
                .then()
                .statusCode(200);
    }

    @Test
    @Order(9)
    public void everyoneGetId() {
        String id = createBlog(USER);

        RestAssured.given()
                .when().get("/blogs/" + id)
                .then()
                .statusCode(200);
    }

    @Test
    @Order(10)
    public void adminDeleteOwnBlog() {
        String id = createBlog(ADMIN);

        RestAssured.given().auth().oauth2(getAccessToken(ADMIN))
                .when().delete("/blogs/" + id)
                .then()
                .statusCode(204);
    }

    @Test
    @Order(11)
    public void userDeleteOwnBlog() {
        String id = createBlog(USER);

        RestAssured.given().auth().oauth2(getAccessToken(USER))
                .when().delete("/blogs/" + id)
                .then()
                .statusCode(204);
    }

    @Test
    @Order(12)
    public void adminDeleteOtherBlog() {
        String id = createBlog(USER);

        RestAssured.given().auth().oauth2(getAccessToken(ADMIN))
                .when().delete("/blogs/" + id)
                .then()
                .statusCode(204);
    }

    @Test
    @Order(13)
    public void userDeleteOtherBlog() {
        String id = createBlog(ADMIN);

        RestAssured.given().auth().oauth2(getAccessToken(USER))
                .when().delete("/blogs/" + id)
                .then()
                .statusCode(403);
    }

    @Test
    @Order(14)
    public void everyoneDeleteOtherBlog() {
        String id = createBlog(ADMIN);

        RestAssured.given()
                .when().delete("/blogs/" + id)
                .then()
                .statusCode(401);
    }

    @Test
    @Order(15)
    public void adminCreateComment() {
        String id = createBlog(OTHER_USER);

        RestAssured.given().auth().oauth2(getAccessToken(ADMIN))
                .contentType(ContentType.JSON)
                .body(jsonPayloadComment)
                .when().post("/blogs/{id}/comments", id)
                .then()
                .statusCode(201);
    }

    @Test
    @Order(16)
    public void userCreateComment() {
        String id = createBlog(OTHER_USER);

        RestAssured.given().auth().oauth2(getAccessToken(USER))
                .contentType(ContentType.JSON)
                .body(jsonPayloadComment)
                .when().post("/blogs/{id}/comments", id)
                .then()
                .statusCode(201);
    }

    @Test
    @Order(17)
    public void everyoneCreateComment() {
        String id = createBlog(OTHER_USER);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(jsonPayloadComment)
                .when().post("/blogs/{id}/comments", id)
                .then()
                .statusCode(401);
    }

    @Test
    @Order(18)
    public void adminPutLikedByMe() {
        String id = createBlog(OTHER_USER);

        RestAssured.given().auth().oauth2(getAccessToken(ADMIN))
                 .when().put("/blogs/{id}/likedByMe", id)
                .then()
                .statusCode(201);
    }

    @Test
    @Order(19)
    public void userPutLikedByMe() {
        String id = createBlog(OTHER_USER);

        RestAssured.given().auth().oauth2(getAccessToken(USER))
                 .when().put("/blogs/{id}/likedByMe", id)
                .then()
                .statusCode(201);
    }

    @Test
    @Order(20)
    public void everyonePutLikedByMe() {
        String id = createBlog(OTHER_USER);

        RestAssured.given()
                 .when().put("/blogs/{id}/likedByMe", id)
                .then()
                .statusCode(401);
    }

    protected String getAccessToken(String username) {
        return keycloakClient.getAccessToken(username);
    }

    private String createBlog(String username) {
        RestAssured.given().auth().oauth2(getAccessToken(username))
                .contentType(ContentType.JSON)
                .body(jsonPayloadBlog)
                .when().post("/blogs");

        String response = RestAssured.given()
                .when()
                .auth()
                .oauth2(getAccessToken(username)) // Use a method to get the access token
                .get("/blogs")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract()
                .response()
                .asString();

        return JsonPath.from(response).getString("id[-1]"); // Adjust as per your JSON structure
    }
}
