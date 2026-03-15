package com.yorku.users;
public class UserFactory {
    public static User createUser(String type, String email, String password, String id) {
        return switch (type) {
            case "student" -> new Student(email, password, id);
            case "faculty" -> new Faculty(email, password, id);
            case "researcher" -> new Researcher(email, password, id);
            case "guest" -> new Guest(email, password, id);
            default -> throw new IllegalArgumentException("Invalid user type");
        };
    }
}