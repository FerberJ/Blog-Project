package ch.hftm.control.dto;

public interface BlogDto {
    public record NewBlogDto(String title, String content, boolean likedByMe) { }

}