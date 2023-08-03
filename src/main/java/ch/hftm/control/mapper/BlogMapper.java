package ch.hftm.control.mapper;

import ch.hftm.control.dto.BlogDto.NewBlogDto;
import ch.hftm.entity.Blog;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;

@ApplicationScoped
public class BlogMapper {
    @Valid
    public Blog toValidBlog(NewBlogDto blogDto) {
        return new Blog(blogDto.title(), blogDto.content(), false);
    }
}
