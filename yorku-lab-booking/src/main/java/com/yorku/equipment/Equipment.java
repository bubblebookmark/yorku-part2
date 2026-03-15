package com.yorku.equipment;
public class Equipment implements Cloneable {

    private String id;
    private String description;
    private String location;
    private boolean available = true;

    public Equipment(String id, String description, String location) {
        this.id = id;
        this.description = description;
        this.location = location;
    }

    public Equipment clone() {
        try {
            return (Equipment) super.clone();
        } catch(CloneNotSupportedException e) {
            return null;
        }
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean status) {
        this.available = status;
    }

    public String getId() {
        return id;
    }
}