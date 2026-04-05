package AI_assistant;

import com.yorku.equipment.Equipment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EquipmentAITest {

    @Test
    void testSetDescriptionCorrectness() {
        Equipment eq = new Equipment("E1", "Drill", "Room A");

        eq.setDescription("Hammer");
        assertEquals("Hammer", eq.getDescription());
    }

    @Test
    void testCloneNotSameObject() {
        Equipment eq1 = new Equipment("E1", "Drill", "Room A");
        Equipment eq2 = eq1.clone();

        assertNotNull(eq2);
        assertNotSame(eq1, eq2);
    }

    @Test
    void testClonePreservesData() {
        Equipment eq1 = new Equipment("E1", "Drill", "Room A");
        Equipment eq2 = eq1.clone();

        assertEquals("E1", eq2.getId());
        assertEquals("Drill", eq2.getDescription());
        assertEquals("Room A", eq2.getLocation());
    }

    @Test
    void testSetAvailable() {
        Equipment eq = new Equipment("E1", "Drill", "Room A");

        eq.setAvailable(false);
        assertFalse(eq.isAvailable());
    }
}