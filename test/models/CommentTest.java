package models;

import org.junit.Before;
import org.junit.Test;
import play.test.Fixtures;
import play.test.UnitTest;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;

public class CommentTest extends UnitTest {

    @Before
    public void setUp() throws Exception {
        Fixtures.deleteDatabase();
    }

    @Test
    public void testComment() {
        // Given a user
        User author = new User("a@books.com", "sekret", "A Booker").save();

        // and a post by that user
        Post post = new Post(author, "blog two", "blogety blog").save();

        // when a commment is created for the post
        new Comment(post, "user 1", "liked").save();
        // and another commetn is created by another user
        new Comment(post, "user 2", "hated").save();

        // and the comments are retreived from the database
        List<Comment> comments = Comment.find("byPost", post).fetch();

        // then there is two comments
        assertThat(comments.size(), is(2));
        // and the author is the commentor
        // and the comment matches the comment
        // and the posted date is not null
    }


}