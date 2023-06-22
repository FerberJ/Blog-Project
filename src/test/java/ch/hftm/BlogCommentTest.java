package ch.hftm;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ch.hftm.entity.BlogComment;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class BlogCommentTest {
    
    @Test
    void testGetComment() {
        String comment = "This is a test comment.";
        BlogComment blogComment = new BlogComment(comment);

        assertEquals(comment, blogComment.getComment());
    }
}
