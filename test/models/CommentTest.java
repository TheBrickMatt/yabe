package models;

import org.junit.Before;
import org.junit.Test;
import play.test.Fixtures;
import play.test.UnitTest;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;

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
        Comment c1 = comments.get(0);
        Comment c2 = comments.get(1);
        assertThat(c1.author, is("user 1"));
        assertThat(c1.comment, is("liked"));
        assertThat(c1.postedAt, not(nullValue()));
        assertThat(c2.author, is("user 2"));
        assertThat(c2.comment, is("hated"));
        assertThat(c2.postedAt, not(nullValue()));
        // and the comment matches the comment
        // and the posted date is not null
    }

    @Test
    public void useTheCommentsRelation() {
        // Create a new user and save it
        User bob = new User("bob@gmail.com", "secret", "Bob").save();

        // Create a new post
        Post bobPost = new Post(bob, "My first post", "Hello world").save();

        // Post a first comment
        bobPost.addComment("Jeff", "Nice post");
        bobPost.addComment("Tom", "I knew that !");

        // Count things
        assertEquals(1, User.count());
        assertEquals(1, Post.count());
        assertEquals(2, Comment.count());

        // Retrieve Bob's post
        bobPost = Post.find("byAuthor", bob).first();
        assertNotNull(bobPost);

        // Navigate to comments
        assertEquals(2, bobPost.comments.size());
        assertEquals("Jeff", bobPost.comments.get(0).author);

        // Delete the post
        bobPost.delete();

        // Check that all comments have been deleted
        assertEquals(1, User.count());
        assertEquals(0, Post.count());
        assertEquals(0, Comment.count());
    }
}