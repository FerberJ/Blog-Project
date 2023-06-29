package ch.hftm;

import org.junit.jupiter.api.Test;

import ch.hftm.control.BlogService;
import ch.hftm.entity.Blog;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class BlogResourceTest {
    @Inject
    BlogService blogService;

    @Test
    void testBlogEndpoint() {
        Blog blog = new Blog("Testing Blog", "This is my testing blog in BlogResourceTest", true);
        blogService.addBlog(blog);

        given()
            .when().get("/blogs")
            .then()
                .statusCode(200)
                .body("content[0]", is("This is my testing blog in BlogResourceTest"));
    }
}
