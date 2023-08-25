package ch.hftm;


import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.keycloak.client.KeycloakTestClient;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@QuarkusTest
class BlogSystemTest {
    
    KeycloakTestClient keycloakClient = new KeycloakTestClient();

    @Test
    public void adminGet() {
        RestAssured.given().auth().oauth2(getAccessToken("alice"))
            .when().get("/blogs")
            .then()
                .statusCode(200);
    }

    @Test
    public void userGet() {
        RestAssured.given().auth().oauth2(getAccessToken("bob"))
            .when().get("/blogs")
            .then()
                .statusCode(200);
    }

    @Test
    public void everyoneGet() {
        RestAssured.given()
            .when().get("/blogs")
            .then()
                .statusCode(200);
    }

    @Test 
    public void adminPost() {

        String jsonPayload = """
            {
                "title": "HHH",
                "content": "string"
              }        
              """;

        RestAssured.given().auth().oauth2(getAccessToken("alice"))
            .contentType(ContentType.JSON)
            .body(jsonPayload)
            .when().post("/blogs")
            .then()
                .statusCode(201);
    }

    protected String getAccessToken(String username) {
        return keycloakClient.getAccessToken(username);
    }
}
