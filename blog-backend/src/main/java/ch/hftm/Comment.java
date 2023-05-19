package ch.hftm;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Comment")
public class Comment {
    @Id @GeneratedValue
    private Long id;
    private String comment;

    public Comment(String comment) {
        this.comment = comment;
    }
}
