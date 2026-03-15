package com.yorku.command;
import com.yorku.booking.BookingService;
public class CancelCommand implements Command {

    private BookingService service;

    public CancelCommand(BookingService service) {
        this.service = service;
    }

    @Override
    public void execute() {
        service.cancel();
    }
}