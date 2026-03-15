package com.yorku.command;
import com.yorku.booking.BookingService;
public class ReserveCommand implements Command {

    private BookingService service;

    public ReserveCommand(BookingService service) {
        this.service = service;
    }

    @Override
    public void execute() {
        service.reserve();
    }
}