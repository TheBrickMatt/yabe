package models;

import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class Comment extends Model {

    public String author;
    public Date postedAt;
    @Lob
    public String comment;

    @ManyToOne
    public Post post;

    public Comment(Post post, String author, String content) {
        this.author = author;
        this.post = post;
        this.postedAt = new Date();
    }

}