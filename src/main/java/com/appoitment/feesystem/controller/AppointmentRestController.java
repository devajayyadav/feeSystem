package com.appoitment.feesystem.controller;


import com.appoitment.feesystem.service.AppointmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AppointmentRestController {

    private final AppointmentService service;

    public AppointmentRestController(AppointmentService service) {
        this.service = service;
    }

    @GetMapping("/booked-times")
    public List<String> getBookedTimes(@RequestParam String date) {
        return service.getBookedTimes(date);
    }
}

