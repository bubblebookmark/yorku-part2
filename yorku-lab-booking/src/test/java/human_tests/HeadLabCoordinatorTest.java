package human_tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.yorku.coordinator.HeadLabCoordinator;
import com.yorku.coordinator.LabManager;
import com.yorku.users.Student;
import com.yorku.users.User;

public class HeadLabCoordinatorTest {

    private HeadLabCoordinator head;

    @BeforeEach
    void setup() {
        head = HeadLabCoordinator.getInstance();
    }

 @Test
void testGenerateLabManager() {
    LabManager manager = head.generateLabManager("Alice", "alice@yorku.ca", "123", "555-5555");
    assertNotNull(manager);
    assertEquals("alice@yorku.ca", manager.getName());
}

    @Test
    void testRegisterValidStudent() throws Exception {
        Student s = new Student("s@yorku.ca", "Password1!", "ID1");
        assertTrue(head.registerUser(s));
    }

    @Test
    void testRegisterDuplicateEmail() throws Exception {
        Student s1 = new Student("dup@yorku.ca", "Password1!", "ID1");
        Student s2 = new Student("dup@yorku.ca", "Password1!", "ID2");
        head.registerUser(s1);
        assertThrows(Exception.class, () -> head.registerUser(s2));
    }

    @Test
    void testWeakPassword() {
        Student s = new Student("weak@yorku.ca", "weak", "ID1");
        assertThrows(Exception.class, () -> head.registerUser(s));
    }

    @Test
    void testMissingID() {
        Student s = new Student("noid@yorku.ca", "Password1!", "");
        assertThrows(Exception.class, () -> head.registerUser(s));
    }

    @Test
    void testPendingApprovalList() throws Exception {
        Student s = new Student("p@yorku.ca", "Password1!", "ID1");
        head.registerUser(s);
        assertFalse(head.getPendingApprovals().isEmpty());
    }

    @Test
    void testApproveUser() throws Exception {
        Student s = new Student("a@yorku.ca", "Password1!", "ID1");
        head.registerUser(s);
        head.approveUser(s);
        assertEquals(User.Status.APPROVED, s.getStatus());
    }

    @Test
    void testRejectUser() throws Exception {
        Student s = new Student("r@yorku.ca", "Password1!", "ID1");
        head.registerUser(s);
        head.rejectUser(s);
        assertEquals(User.Status.REJECTED, s.getStatus());
    }

    @Test
    void testGetPendingApprovalsReturnsCopy() throws Exception {
        Student s = new Student("copy@yorku.ca", "Password1!", "ID1");
        head.registerUser(s);
        assertNotSame(head.getPendingApprovals(), head.getPendingApprovals());
    }

    @Test
    void testSingletonInstance() {
        assertSame(head, HeadLabCoordinator.getInstance());
    }
}
