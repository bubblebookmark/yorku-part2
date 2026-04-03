package human_tests;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.yorku.coordinator.LabManager;
import com.yorku.equipment.Equipment;

public class LabMangerTest {
    private LabManager labManager;

    @BeforeEach
    void setUp() {
        labManager = new LabManager("manager@yorku.ca");
    }
    @Test //test 1
    void loadsEquipmentFromCsv() {
        assertFalse(labManager.getAllEquipment().size() == 20);
        assertNotNull(labManager.getEquipment("EQ-001"));
    }
    @Test //test 2
    void addEquipmenttoCsv() {
        Equipment signal = new Equipment("EQ-250", "Signal montior", "Lab A");
        labManager.addEquipment(signal);
        assertTrue(labManager.getAllEquipment().size() == 11);
        assertEquals(signal, labManager.getEquipment("EQ-250"));
    }
     @Test //test 3
    void canDisableAndEnableEquipment() {
        labManager.enableEquipment("EQ-010");
        assertTrue(labManager.getEquipment("EQ-010").isAvailable());

        labManager.disableEquipment("EQ-010");
        assertFalse(labManager.getEquipment("EQ-010").isAvailable());
    }
    @Test //test 4
    void canMarkforMaintaince(){
        labManager.markForMaintenance("EQ-010");
        assertFalse(labManager.getEquipment("EQ-010").isAvailable());
    }
    @Test //test 5
    void checkthatepuipmentstatusisCorrect(){
        List<Equipment> availableEquipment = labManager.getAvailableEquipment();
        assertTrue(!availableEquipment.contains(labManager.getEquipment("EQ-002")));
    }
    @Test //test 6
    void getsAllEquipmentIds(){
        List<String> id = labManager.getAllEquipmentIds();

        assertTrue(id.contains("EQ-001"));
        assertTrue(id.contains("EQ-002"));
        assertTrue(id.contains("EQ-003"));
        assertTrue(id.contains("EQ-004"));
        assertTrue(id.contains("EQ-005"));
        assertTrue(id.contains("EQ-006"));
        assertTrue(id.contains("EQ-007"));
        assertTrue(id.contains("EQ-008"));
        assertTrue(id.contains("EQ-009"));
        assertTrue(id.contains("EQ-010"));
    }
    @Test //test 7
    void getsEquipmentById(){
        Equipment equipment = labManager.getEquipmentById("EQ-003");

        assertNotNull(equipment);
        assertEquals("EQ-003", equipment.getId());
    }

    @Test //test 8
    void getsManagerEmail(){
        assertEquals("manager@yorku.ca", labManager.getEmail());
    }

    @Test //test 9
    void returnsNullForUnknownEquipment(){
        assertNull(labManager.getEquipment("EQ-999"));
    }
}
