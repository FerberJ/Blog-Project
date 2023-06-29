package ch.hftm.control;

import java.util.List;
import java.util.Optional;

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

    public List<Blog> getBlogs(String searchString) {
        var blogs = blogRepository.find("title like ?1 or content like ?1", "%" + searchString + "%").list();
        logger.info("Returning " + blogs.size() + " blogs");
        return blogs;
    }

    public Optional<Blog> getBlog(long id) {
        var blog = blogRepository.findByIdOptional(id);
        //      logger.info("Returning Blog " + blog.getTitle());
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

    @Transactional
    public void updateLikedByMe(Blog blog) {
        logger.info("Updating likedByMe for blog " + blog.getTitle());
        blogRepository.update("likedByMe = ?1 where id = ?2", !blog.isLikedByMe(), blog.getId());
    }

    public Blog updateBlog(Blog blogToUpdate) {
        logger.info("Updating blog " + blogToUpdate.getTitle());
        return blogRepository.getEntityManager().merge(blogToUpdate);
    }
}