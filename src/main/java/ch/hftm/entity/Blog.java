package ch.hftm.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Blog  {
    @Id @GeneratedValue
    private Long id;
    private String title;
    private String content;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> comments;

    public Blog(String title, String content) {
        this.title = title;
        this.content = content;
    }
 }
