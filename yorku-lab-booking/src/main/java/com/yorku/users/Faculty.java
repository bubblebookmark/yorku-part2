package com.yorku.users;
public class Faculty extends User{
    public Faculty(String email, String password, String idNumber) {
        super(email, password, idNumber);
    }

    @Override
    public double getHourlyRate() {
        return 15;
    }
}
