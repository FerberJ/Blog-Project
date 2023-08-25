package ch.hftm.control.mapper;

import ch.hftm.control.dto.BlogDto.NewBlogDto;
import ch.hftm.entity.Blog;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BlogMapper {
    public Blog toValidBlog(NewBlogDto blogDto, String author) {
        return new Blog(blogDto.getTitle(), blogDto.getContent(), false, author);
    }
}
