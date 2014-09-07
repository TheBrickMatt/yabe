package models;

import play.db.jpa.Model;

import javax.persistence.Entity;
import java.util.List;
import java.util.Map;

@Entity
public class Tag extends Model implements Comparable<Tag> {

    public Tag(String name) {
        this.name = name;
    }

    public String name;

    @Override
    public int compareTo(Tag o) {
        return name.compareTo(o.name);
    }

    public static Tag findOrCreateByName(String name) {
        Tag tag = Tag.find("byName", name).first();
        if ( tag == null ) {
            tag = new Tag(name);  // Save? or is this cascaded from Post.save()?
        }
        return tag;
    }

    public static List<Map> getCloud() {
        List<Map> result = Tag.find(
                "select new map(t.name as tag, count(p.id) as weight) from Post p join p.tags as t group by t.name order by t.name"
        ).fetch();
        return result;
    }

}
