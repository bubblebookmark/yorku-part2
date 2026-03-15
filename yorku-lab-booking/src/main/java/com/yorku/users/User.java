package com.yorku.users;
public abstract class User {
    protected String email;
    protected String password;
    protected String idNumber;

    public User(String email, String password, String idNumber) {
        this.email = email;
        this.password = password;
        this.idNumber = idNumber;
    }

    public abstract double getHourlyRate();

    public String getEmail() {
        return email;
    }
}
