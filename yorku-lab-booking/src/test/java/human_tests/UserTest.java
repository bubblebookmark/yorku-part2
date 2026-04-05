package human_tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    void testDefaultStatusIsPending() {
        User user = UserFactory.createUser("student", "a@x.com", "pass", "1");
        assertEquals(User.Status.PENDING_APPROVAL, user.getStatus());
    }

    @Test
    void testSetStatus() {
        User user = UserFactory.createUser("faculty", "b@x.com", "pass", "2");
        user.setStatus(User.Status.APPROVED);
        assertEquals(User.Status.APPROVED, user.getStatus());
    }

    @Test
    void testGetters() {
        User user = UserFactory.createUser("researcher", "c@x.com", "pass", "3");

        assertEquals("c@x.com", user.getEmail());
        assertEquals("pass", user.getPassword());
        assertEquals("3", user.getIdNumber());
        assertTrue(user.isUniversityAffiliated());
    }

    @Test
    void testFactoryInvalidType() {
        assertThrows(IllegalArgumentException.class, () -> {
            UserFactory.createUser("invalid", "x@x.com", "pass", "9");
        });
    }
}