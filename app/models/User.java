package models;


import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="blog_user")
public class User extends Model {

    public String email;
    public String password;
    @Required
    public String fullName;
    public boolean isAdmin;

    public User(String email, String password, String fullName) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
    }

    public static User connect(String email, String password) {
        return find("byEmailAndPassword", email, password).first();
    }

}
