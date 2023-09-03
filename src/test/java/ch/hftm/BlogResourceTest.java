package ch.hftm;

import org.junit.jupiter.api.Test;

import ch.hftm.control.BlogService;
import ch.hftm.control.dto.BlogDto.NewBlogDto;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class BlogResourceTest {
    @Inject
    BlogService blogService;

    /*
    @Test
    void testBlogEndpoint() {
        NewBlogDto blog = new NewBlogDto("hello", "content");

        //blog = new Blog("Testing Blog", "This is my testing blog in BlogResourceTest", true);
        blogService.addBlogDto(blog, "test");

        given()
            .when().get("/blogs")
            .then()
                .statusCode(200)
                .body("content[0]", is("This is my testing blog in BlogResourceTest"));
    }

    */
}
