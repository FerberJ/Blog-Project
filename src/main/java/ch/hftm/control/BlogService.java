package ch.hftm.control;

import java.util.List;

import org.jboss.logging.Logger;

import ch.hftm.entity.Blog;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@Dependent
public class BlogService {
    @Inject
    BlogRepository blogRepository;

    @Inject
    Logger logger;

    public List<Blog> getBlogs() {
        var blogs = blogRepository.listAll();
        logger.info("Returning " + blogs.size() + " blogs");
        return blogs;
    }

    public Blog getBlog(long id) {
        var blog = blogRepository.findById(id);
        logger.info("Returning Blog " + blog.getTitle());
        return blog;
    } 

    @Transactional
    public void addBlog(Blog blog) {
        logger.info("Adding blog " + blog.getTitle());
        blogRepository.persist(blog);
    }

    @Transactional
    public void removeBlog(Blog blog) {
        logger.info("Removing blog " + blog.getTitle());
        blogRepository.delete(blog);
    }
}