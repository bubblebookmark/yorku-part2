package human_tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserRegistryTest {

    @Test
    void testSingletonInstance() {
        UserRegistry r1 = UserRegistry.getInstance();
        UserRegistry r2 = UserRegistry.getInstance();

        assertSame(r1, r2);
    }

    @Test
    void testRegisterAndRetrieveUser() {
        UserRegistry registry = UserRegistry.getInstance();

        User user = UserFactory.createUser("student", "test@x.com", "pass", "100");
        registry.registerUser(user);

        User retrieved = registry.getUserByEmail("test@x.com");

        assertEquals(user, retrieved);
    }

    @Test
    void testIsEmailRegistered() {
        UserRegistry registry = UserRegistry.getInstance();

        User user = UserFactory.createUser("faculty", "exists@x.com", "pass", "101");
        registry.registerUser(user);

        assertTrue(registry.isEmailRegistered("exists@x.com"));
        assertFalse(registry.isEmailRegistered("no@x.com"));
    }
}