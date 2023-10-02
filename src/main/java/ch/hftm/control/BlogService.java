package ch.hftm.control;

import java.util.List;
import java.util.Optional;

import ch.hftm.control.dto.BlogDto.NewBlogDto;
import ch.hftm.control.dto.CommentDto.NewBlogCommentDto;
import ch.hftm.control.mapper.BlogCommentMapper;
import ch.hftm.control.mapper.BlogMapper;
import ch.hftm.entity.Blog;
import ch.hftm.entity.BlogComment;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@Dependent
public class BlogService {
    @Inject
    BlogRepository blogRepository;

    public List<Blog> getBlogs() {
        var blogs = blogRepository.listAll();
        return blogs;
    }

    public List<Blog> getBlogs(String searchString) {
        var blogs = blogRepository.find("title like ?1 or content like ?1", "%" + searchString + "%").list();
        return blogs;
    }

    public Optional<Blog> getBlog(long id) {
        var blog = blogRepository.findByIdOptional(id);
        return blog;
    } 
    

    @Transactional
    public void removeBlog(Blog blog) {
        blogRepository.delete(blog);
    }

    @Transactional
    public void updateLikedByMe(Blog blog) {
        blogRepository.update("likedByMe = ?1 where id = ?2", !blog.isLikedByMe(), blog.getId());
    }

    @Transactional
    public long addBlogDto(NewBlogDto blogDto, String author) {
        Blog blog = new BlogMapper().toValidBlog(blogDto, author);
        blogRepository.persist(blog);
        return blog.getId();
    }

    @Transactional
    public long addBlogCommentDto(NewBlogCommentDto blogCommentDto, long id) {
        Blog blog = getBlog(id).orElseThrow();
        BlogComment comment = new BlogCommentMapper().toValidBlogComment(blogCommentDto);
        blog.addComment(comment);
        blogRepository.persist(blog);
        return blog.getId();
    }


}