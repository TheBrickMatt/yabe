package models;

import org.junit.Before;
import org.junit.Test;
import play.test.Fixtures;
import play.test.UnitTest;

import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;

public class TagTest extends UnitTest {

    @Before
    public void setup() {
        Fixtures.deleteDatabase();
    }

    @Test
    public void testTags() {
        populateTestBlogs();

        assertThat(Post.findTaggedPosts("Red").size(), is(2));
        assertThat(Post.findTaggedPosts("Blue").size(), is(1));
        assertThat(Post.findTaggedPosts("Green").size(), is(1));
    }

    @Test
    public void testMultipleTags() {
        populateTestBlogs();

        assertThat(Post.findTaggedPosts("Red", "Blue").size(), is(1));
        assertThat(Post.findTaggedPosts("Red", "Green").size(), is(1));
        assertThat(Post.findTaggedPosts("Red", "Green", "Blue").size(), is(0));
        assertThat(Post.findTaggedPosts("Green", "Blue").size(), is(0));
    }

    @Test
    public void testTagCloud() {
        populateTestBlogs();
        List<Map> cloud = Tag.getCloud();
        assertEquals(
                "[{tag=Blue, weight=1}, {tag=Green, weight=1}, {tag=Red, weight=2}]",
                cloud.toString()
        );
    }

    private void populateTestBlogs() {
        // Create a new user and save it
        User bob = new User("bob@gmail.com", "secret", "Bob").save();

        // Create a new post
        Post bobPost = new Post(bob, "My first post", "Hello world").save();
        Post anotherBobPost = new Post(bob, "Hop", "Hello world").save();

        // TODO: refactor so this assert is somewhere more reasonable
        assertThat(Post.findTaggedPosts("Red").size(), is(0));

        // Tag it now
        bobPost.tagWith("Red").tagWith("Blue").save();
        anotherBobPost.tagWith("Red").tagWith("Green").save();
    }
}