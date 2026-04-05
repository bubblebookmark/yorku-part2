package AI_assistant;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    void testUserInitialization() {
        User user = UserFactory.createUser("student", "ai@x.com", "password", "U1");

        assertEquals("ai@x.com", user.getEmail());
        assertEquals("password", user.getPassword());
        assertEquals("U1", user.getIdNumber());
        assertEquals(User.Status.PENDING_APPROVAL, user.getStatus());
        assertTrue(user.isUniversityAffiliated());
    }

    @Test
    void testStatusUpdates() {
        User user = UserFactory.createUser("faculty", "ai2@x.com", "pass", "U2");

        user.setStatus(User.Status.APPROVED);
        assertEquals(User.Status.APPROVED, user.getStatus());

        user.setStatus(User.Status.REJECTED);
        assertEquals(User.Status.REJECTED, user.getStatus());
    }

    @Test
    void testUniversityAffiliationFlag() {
        User user = UserFactory.createUser("researcher", "ai3@x.com", "pass", "U3");
        assertTrue(user.isUniversityAffiliated());
    }
}