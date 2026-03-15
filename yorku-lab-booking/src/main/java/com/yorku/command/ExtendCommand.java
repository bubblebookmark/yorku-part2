package com.yorku.command;
import com.yorku.booking.BookingService;
public class ExtendCommand implements Command {

    private BookingService service;

    public ExtendCommand(BookingService service) {
        this.service = service;
    }

    @Override
    public void execute() {
        service.extend();
    }
}