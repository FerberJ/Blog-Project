package ch.hftm.control.mapper;

import ch.hftm.control.dto.CommentDto.NewBlogCommentDto;
import ch.hftm.entity.BlogComment;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BlogCommentMapper {
    public BlogComment toValidBlogComment(NewBlogCommentDto blogCommentDto) {
        return new BlogComment(blogCommentDto.comment());
    }
}
