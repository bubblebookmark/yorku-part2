package AI_assistant;

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

class LabManagerTest {

    private LabManager labManager;

    @BeforeEach
    void setUp() {
        labManager = new LabManager("manager@yorku.ca");
    }

    @Test
    void constructorLoadsEquipmentFromCsv() {
        assertEquals(10, labManager.getAllEquipment().size());

        Equipment microscope = labManager.getEquipment("EQ-001");
        assertNotNull(microscope);
        assertEquals("Lab A", microscope.getLocation());
        assertTrue(microscope.isAvailable());

        Equipment centrifuge = labManager.getEquipment("EQ-002");
        assertNotNull(centrifuge);
        assertFalse(centrifuge.isAvailable());
    }

    @Test
    void addEquipmentStoresEquipmentById() {
        Equipment equipment = new Equipment("EQ-999", "Test Device", "Test Lab");

        labManager.addEquipment(equipment);

        assertEquals(equipment, labManager.getEquipment("EQ-999"));
    }

    @Test
    void enableDisableAndMaintenanceUpdateAvailability() {
        labManager.disableEquipment("EQ-001");
        assertFalse(labManager.getEquipment("EQ-001").isAvailable());

        labManager.enableEquipment("EQ-001");
        assertTrue(labManager.getEquipment("EQ-001").isAvailable());

        labManager.markForMaintenance("EQ-001");
        assertFalse(labManager.getEquipment("EQ-001").isAvailable());
    }

    @Test
    void getAvailableEquipmentReturnsOnlyAvailableItems() {
        List<Equipment> availableEquipment = labManager.getAvailableEquipment();

        assertEquals(6, availableEquipment.size());
        assertTrue(availableEquipment.stream().allMatch(Equipment::isAvailable));
        assertFalse(availableEquipment.stream().anyMatch(eq -> "EQ-002".equals(eq.getId())));
    }

    @Test
    void getAllEquipmentIdsIncludesLoadedIds() {
        List<String> equipmentIds = labManager.getAllEquipmentIds();

        assertEquals(10, equipmentIds.size());
        assertTrue(equipmentIds.contains("EQ-001"));
        assertTrue(equipmentIds.contains("EQ-010"));
    }

    @Test
    void getNameAndEmailReturnConstructorValue() {
        assertEquals("manager@yorku.ca", labManager.getName());
        assertEquals("manager@yorku.ca", labManager.getEmail());
    }

    @Test
    void getPasswordDefaultsToNull() {
        assertNull(labManager.getPassword());
    }

    @Test
    void getEquipmentByIdMatchesGetEquipment() {
        Equipment equipment = labManager.getEquipment("EQ-003");

        assertEquals(equipment, labManager.getEquipmentById("EQ-003"));
    }

    @Test
    void equipmentOperationsIgnoreUnknownIds() {
        int initialAvailableCount = labManager.getAvailableEquipment().size();

        labManager.enableEquipment("UNKNOWN-ID");
        labManager.disableEquipment("UNKNOWN-ID");
        labManager.markForMaintenance("UNKNOWN-ID");

        assertNull(labManager.getEquipment("UNKNOWN-ID"));
        assertEquals(initialAvailableCount, labManager.getAvailableEquipment().size());
    }

    @Test
    void getAvailableEquipmentReflectsAvailabilityChanges() {
        labManager.disableEquipment("EQ-004");
        assertEquals(5, labManager.getAvailableEquipment().size());

        labManager.enableEquipment("EQ-002");
        assertEquals(6, labManager.getAvailableEquipment().size());
        assertTrue(
            labManager.getAvailableEquipment().stream().anyMatch(eq -> "EQ-002".equals(eq.getId()))
        );
    }

    @Test
    void addEquipmentWithExistingIdReplacesStoredEquipment() {
        Equipment replacement = new Equipment("EQ-001", "Replacement Device", "Updated Lab");

        labManager.addEquipment(replacement);

        assertEquals(replacement, labManager.getEquipment("EQ-001"));
        assertEquals("Updated Lab", labManager.getEquipment("EQ-001").getLocation());
    }
}
