package ch.hftm;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class BlogResourceTest {
    @Test
    public void testBlogEndpoint() {
        given()
            .when().get("/blogs")
            .then()
                .statusCode(200)
                .body("content[0]", is("This is my testing blog"));
    }
}
