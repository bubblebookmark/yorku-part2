package com.yorku.command;
import com.yorku.booking.BookingFacade;
import com.yorku.equipment.Equipment;
import com.yorku.users.User;

public class ReserveCommand implements Command {

    private BookingFacade booking;
    private User user;
    private Equipment equipment;
    private int hours;

    // NEW constructor
    public ReserveCommand(BookingFacade booking, User user, Equipment equipment, int hours) {
        this.booking = booking;
        this.user = user;
        this.equipment = equipment;
        this.hours = hours;
    }

    @Override
    public void execute() {
       booking.reserveEquipment(user, equipment, hours);
    }
}