package ch.hftm.control.dto;

import jakarta.validation.constraints.NotBlank;

public interface CommentDto {
    public record NewBlogCommentDto(@NotBlank(message = "Comment must not be blank.") String comment) { } 
}
