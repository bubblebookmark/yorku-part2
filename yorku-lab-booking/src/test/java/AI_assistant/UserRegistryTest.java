package AI_assistant;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserRegistryAITest {

    @Test
    void testSingletonInstance() {
        UserRegistry r1 = UserRegistry.getInstance();
        UserRegistry r2 = UserRegistry.getInstance();

        assertSame(r1, r2);
    }

    @Test
    void testRegisterAndRetrieveUser() {
        UserRegistry registry = UserRegistry.getInstance();

        User user = UserFactory.createUser("student", "reg@x.com", "pass", "100");
        registry.registerUser(user);

        User retrieved = registry.getUserByEmail("reg@x.com");

        assertNotNull(retrieved);
        assertEquals("reg@x.com", retrieved.getEmail());
    }

    @Test
    void testEmailRegistrationCheck() {
        UserRegistry registry = UserRegistry.getInstance();

        User user = UserFactory.createUser("guest", "check@x.com", "pass", "200");
        registry.registerUser(user);

        assertTrue(registry.isEmailRegistered("check@x.com"));
        assertFalse(registry.isEmailRegistered("missing@x.com"));
    }

    @Test
    void testOverwriteUserWithSameEmail() {
        UserRegistry registry = UserRegistry.getInstance();

        User u1 = UserFactory.createUser("student", "dup@x.com", "p1", "1");
        User u2 = UserFactory.createUser("faculty", "dup@x.com", "p2", "2");

        registry.registerUser(u1);
        registry.registerUser(u2);

        assertEquals(u2, registry.getUserByEmail("dup@x.com"));
    }
}