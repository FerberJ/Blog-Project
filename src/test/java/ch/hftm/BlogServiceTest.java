package ch.hftm;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import ch.hftm.control.BlogService;
import ch.hftm.entity.Blog;
import ch.hftm.entity.BlogComment;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
class BlogServiceTest {
    @Inject
    BlogService blogService;

    
    @Test
    void listingAndAddingBlogs() {
        // Arrange

        Blog blog = new Blog("Testing Blog", "This is my testing blog");

        int blogsBefore;
        List<Blog> blogs;
       
        // Act
        blogsBefore = blogService.getBlogs().size();

        blogService.addBlog(blog);

        blogs = blogService.getBlogs();

        // Assert
        assertEquals(blogsBefore + 1, blogs.size());
        assertEquals(blog.getId(), blogs.get(blogs.size()-1).getId());      
    }

    @Test
    void addCommentsToBlog() {
        Blog blog = new Blog("First Blog", "This is my testing blog");
        List<Blog> blogs;
        List<BlogComment> comments = new ArrayList<>();
        comments.add(new BlogComment("First Comment"));
        comments.add(new BlogComment("Second Comment"));

        blog.setComments(comments);

        blogService.addBlog(blog);
        blogs = blogService.getBlogs();

        assertEquals("First Comment", blogs.get(blogs.size()-1).getComments().get(0).getComment());
        assertEquals(comments.size(), blogs.get(blogs.size()-1).getComments().size());
    }
}
