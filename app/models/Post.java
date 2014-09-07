package models;

import play.db.jpa.Model;

import javax.persistence.*;
import java.util.*;

@Entity
public class Post extends Model {

    public String title;
    public Date postedAt;

    @Lob
    public String content;

    @ManyToOne
    public User author;

    @OneToMany(mappedBy="post", cascade = CascadeType.ALL)
    public List<Comment> comments;

    @ManyToMany(cascade = CascadeType.PERSIST)
    public Set<Tag> tags;

    public Post(User author, String title, String content) {
        this.comments = new ArrayList<>();
        this.tags = new TreeSet<>();
        this.author = author;
        this.title = title;
        this.content = content;
        this.postedAt = new Date();
    }

    public Post addComment(String author, String content) {
        Comment comment = new Comment(this, author, content).save();
        comments.add(comment);
        this.save();
        return this;
    }

    public Post previous() {
        return Post.find("postedAt < ? order by postedAt desc", postedAt).first();
    }

    public Post next() {
        return Post.find("postedAt > ? order by postedAt asc", postedAt).first();
    }

    public Post tagWith(String name) {
        tags.add(Tag.findOrCreateByName(name));
        return this;
    }

    public static List<Post> findTaggedPosts(String tag) {
        return Post.find("select distinct p from Post p join p.tags as t where t.name = :tagName").setParameter("tagName", tag).fetch();
    }

    public static List<Post> findTaggedPosts(String... tags) {
        return Post.find("select distinct p from Post p join p.tags as t where t.name in :tagNames group by p.id, p.author, p.title, p.content,p.postedAt having count(t.id) = :size").bind("tagNames", tags).bind("size", tags.length).fetch();
    }

}
