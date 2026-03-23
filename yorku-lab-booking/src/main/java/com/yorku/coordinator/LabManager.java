package com.yorku.coordinator;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yorku.equipment.Equipment;

public class LabManager {
    private String email, password, id;
    private Map<String, Equipment> equipmentMap = new HashMap<>();

    public LabManager(String name) {
        this.name = name;
        loadEquipmentFromCSV();
    }

    private void loadEquipmentFromCSV() {
        try (InputStream is = getClass().getResourceAsStream("/com/yorku/sample_equipment.csv")) {
            if (is == null) {
                System.out.println("sample_equipment.csv not found");
                return;
            }
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String line;
                br.readLine(); // skip header
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",", -1);
                    if (parts.length >= 6) {
                        String id = parts[0];
                        String name = parts[1];
                        String type = parts[2];
                        String location = parts[3];
                        String availability = parts[4];
                        String notes = parts[5];
                        String description = name + " (" + type + ") - " + notes;
                        Equipment eq = new Equipment(id, description, location);
                        if ("Available".equals(availability)) {
                            eq.setAvailable(true);
                        } else {
                            eq.setAvailable(false);
                        }
                        addEquipment(eq);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading equipment: " + e.getMessage());
        }
    }

    public String getName() { return email; }

    public void addEquipment(Equipment equipment) {
        equipmentMap.put(equipment.getId(), equipment);
        System.out.println("Equipment added: " + equipment.getDescription());
    }

    public void enableEquipment(String id) {
        Equipment eq = equipmentMap.get(id);
        if (eq != null) eq.setAvailable(true);
    }

    public void disableEquipment(String id) {
        Equipment eq = equipmentMap.get(id);
        if (eq != null) eq.setAvailable(false);
    }

    public void markForMaintenance(String id) {
        Equipment eq = equipmentMap.get(id);
        if (eq != null) eq.setAvailable(false);
    }

    public Equipment getEquipment(String id) {
        return equipmentMap.get(id);
    }

    // ✅ New method: return only available equipment
    public List<Equipment> getAvailableEquipment() {
        List<Equipment> availableList = new ArrayList<>();
        for (Equipment eq : equipmentMap.values()) {
            if (eq.isAvailable()) {
                availableList.add(eq);
            }
        }
        return availableList;
    }
    public Equipment getEquipmentById(String id) {
    return equipmentMap.get(id); // returns the Equipment object
    }

    public Collection<Equipment> getAllEquipment() {
    return equipmentMap.values(); // returns all Equipment objects
    }

    // Optional: return all equipment IDs for ComboBoxes
    public List<String> getAllEquipmentIds() {
        return new ArrayList<>(equipmentMap.keySet());
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}