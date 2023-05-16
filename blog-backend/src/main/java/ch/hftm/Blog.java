package ch.hftm;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Blog implements Comparable<Blog> {
    @Id @GeneratedValue
    private Long id;
    private String title;
    private String content;

    public Blog(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @Override
    public int compareTo(Blog t) {
        if(t.getId() == getId()) {
            return 0;
        } else {
            return 1;
        }
    }
 }
