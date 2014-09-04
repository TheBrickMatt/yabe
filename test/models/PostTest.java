package models;

import org.junit.Before;
import org.junit.Test;
import play.test.Fixtures;
import play.test.UnitTest;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;


public class PostTest extends UnitTest {

    @Before
    public void setUp() throws Exception {
        // Not sure why this is done in @Before and not in @After
        Fixtures.deleteDatabase();
    }

    @Test
    public void createPost() {
        // Given an Author
        User author = new User("arther@books.com", "sekret", "AB Booker").save();

        // And a Posting by that Author
        new Post(author, "blog one", "blog blob blog blob").save();

        // When all posts are counted
        // there should be 1 post
        assertThat(Post.count(), is(1L));

        // and give bob's posts
        List<Post> bobPosts = Post.find("byAuthor", author).fetch();

        // there should be 1 bob post
        assertThat(bobPosts.size(), is(1));
        Post firstPost = bobPosts.get(0);
        assertThat(firstPost, is(not(nullValue())));
        assertThat(firstPost.author, is(author));
        assertThat(firstPost.title, is("blog one"));
        assertThat(firstPost.content, is("blog blob blog blob"));
        assertThat(firstPost.postedAt, not(nullValue()));

    }
}