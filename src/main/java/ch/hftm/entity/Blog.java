package ch.hftm.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter

@NoArgsConstructor
@Entity
public class Blog  {
    @Id @GeneratedValue
    private Long id;
    @NotBlank @Size(min = 3, max = 50, message = "Title must be between 3 and 50 characters long.")
    private String title;
    private String content;
    private String author;
    private boolean likedByMe;

    @OneToMany(cascade = CascadeType.ALL)
    private List<BlogComment> comments;

    /*
    public Blog(String title, String content, boolean likedByMe) {
        this.title = title;
        this.content = content;
        this.likedByMe = likedByMe;
    }
    */

    public Blog(String title, String content, boolean likedByMe, String author) {
        this.title = title;
        this.content = content;
        this.likedByMe = likedByMe;
        this.author = author;
    }

    public void addComment(BlogComment comment) {
        comments.add(comment);
    }

    /*
    public void setComments(List<BlogComment> comments) {
        this.comments = comments;
    }
    */

    
 }
