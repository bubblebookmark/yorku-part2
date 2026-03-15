package com.yorku.booking;
import com.yorku.equipment.Equipment;
import com.yorku.users.User;
public class BookingFacade {

    private BookingService bookingService = new BookingService();

    public void reserveEquipment(User user, Equipment equipment, int hours) {

        if(!equipment.isAvailable()) {
            System.out.println("Equipment not available.");
            return;
        }

        double deposit = user.getHourlyRate();

        System.out.println("Deposit paid: $" + deposit);

        bookingService.reserve();

        double totalCost = user.getHourlyRate() * hours;
        System.out.println("Total cost: $" + totalCost);
    }
}