package models;

import org.junit.Before;
import org.junit.Test;
import play.test.Fixtures;
import play.test.UnitTest;

import static org.hamcrest.CoreMatchers.*;

public class UserTest extends UnitTest {

    @Before
    public void setUp() throws Exception {
        Fixtures.deleteDatabase();
    }

    @Test
    public void createAndRetrieveUser() {
        // Create and save user
        new User("foo@bar.com", "soopersekret", "Foo Bar").save();

        User foo = User.find("byEmail", "foo@bar.com").first();

        assertThat(foo, is(notNullValue()));
        assertThat(foo.fullName, is(equalTo("Foo Bar")));
        assertThat(foo.password, is(equalTo("soopersekret")));
    }

    @Test
    public void testConnectWithEmailAndPassword() {
        // Create and save user
        new User("foo@bar.com", "soopersekret", "Foo Bar").save();

        assertThat(User.connect("foo@bar.com", "soopersekret"), is(notNullValue()));
        assertThat(User.connect("bar@foo", "soopersekret"), is(nullValue()));

    }

}