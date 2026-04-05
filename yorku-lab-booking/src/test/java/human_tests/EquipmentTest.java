package human_tests;

import com.yorku.equipment.Equipment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EquipmentTest {

    @Test
    void testConstructorInitialValues() {
        Equipment eq = new Equipment("E1", "Drill", "Room A");

        assertEquals("E1", eq.getId());
        assertEquals("Drill", eq.getDescription());
        assertEquals("Room A", eq.getLocation());
        assertTrue(eq.isAvailable());
    }

    @Test
    void testSetAvailableFalse() {
        Equipment eq = new Equipment("E1", "Drill", "Room A");

        eq.setAvailable(false);
        assertFalse(eq.isAvailable());
    }

    @Test
    void testSetAvailableTrue() {
        Equipment eq = new Equipment("E1", "Drill", "Room A");

        eq.setAvailable(false);
        eq.setAvailable(true);
        assertTrue(eq.isAvailable());
    }

    @Test
    void testSetDescription() {
        Equipment eq = new Equipment("E1", "Drill", "Room A");

        eq.setDescription("Hammer");
        assertEquals("Hammer", eq.getDescription());
    }

    @Test
    void testSetLocation() {
        Equipment eq = new Equipment("E1", "Drill", "Room A");

        eq.setLocation("Room B");
        assertEquals("Room B", eq.getLocation());
    }

    @Test
    void testCloneCreatesNewObject() {
        Equipment eq1 = new Equipment("E1", "Drill", "Room A");
        Equipment eq2 = eq1.clone();

        assertNotNull(eq2);
        assertNotSame(eq1, eq2); // different object
    }

    @Test
    void testCloneCopiesValues() {
        Equipment eq1 = new Equipment("E1", "Drill", "Room A");
        Equipment eq2 = eq1.clone();

        assertEquals(eq1.getId(), eq2.getId());
        assertEquals(eq1.getDescription(), eq2.getDescription());
        assertEquals(eq1.getLocation(), eq2.getLocation());
        assertEquals(eq1.isAvailable(), eq2.isAvailable());
    }

    @Test
    void testCloneIndependence() {
        Equipment eq1 = new Equipment("E1", "Drill", "Room A");
        Equipment eq2 = eq1.clone();

        eq2.setDescription("Hammer");
        eq2.setLocation("Room B");
        eq2.setAvailable(false);

        // Original should not change
        assertEquals("Drill", eq1.getDescription());
        assertEquals("Room A", eq1.getLocation());
        assertTrue(eq1.isAvailable());
    }

    @Test
    void testEmptyStrings() {
        Equipment eq = new Equipment("", "", "");

        assertEquals("", eq.getId());
        assertEquals("", eq.getDescription());
        assertEquals("", eq.getLocation());
    }

    @Test
    void testNullValues() {
        Equipment eq = new Equipment(null, null, null);

        assertNull(eq.getId());
        assertNull(eq.getDescription());
        assertNull(eq.getLocation());
    }

    @Test
    void testMultipleUpdates() {
        Equipment eq = new Equipment("E1", "Drill", "Room A");

        eq.setDescription("Hammer");
        eq.setDescription("Saw");

        assertEquals("Saw", eq.getDescription());
    }

    @Test
    void testLocationUpdateMultipleTimes() {
        Equipment eq = new Equipment("E1", "Drill", "Room A");

        eq.setLocation("Room B");
        eq.setLocation("Room C");

        assertEquals("Room C", eq.getLocation());
    }
}