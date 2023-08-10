package ch.hftm.control.dto;

/*
public interface BlogDto {
    public record NewBlogDto(String title, String content, boolean likedByMe) { }

}
*/

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public interface BlogDto {
    @Getter
    @Setter
    @NoArgsConstructor
    public class NewBlogDto {
        @NotBlank
        @Size(min = 3, max = 50, message = "Title must be between 3 and 50 characters long.")
        private String title;
        private String content;
    }
}