package com.appoitment.feesystem.controller;


import com.appoitment.feesystem.entity.Appointment;
import com.appoitment.feesystem.service.AppointmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class AppointmentController {

    private final AppointmentService service;

    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("appointments", service.getAllAppointments());
        model.addAttribute("appointment", new Appointment());
        return "index";
    }
    @GetMapping("/list")
    public String viewlist(Model model) {
        model.addAttribute("appointments", service.getAllAppointments());
        model.addAttribute("appointment", new Appointment());
        return "Appoitment_list";
    }

    @PostMapping("/save")
    public String saveAppointment(@ModelAttribute Appointment appointment) {
        service.saveAppointment(appointment);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteAppointment(@PathVariable Long id) {
        service.deleteAppointment(id);
        return "redirect:/";
    }
    // edit the appoitment 
    // @GetMapping("/edit/{id}")
    // public String editAppointment(@PathVariable Long id, Model model) {
    //     Appointment appointment = service.getAppointmentById(id);
    //     model.addAttribute("appointment", appointment);
    //     model.addAttribute("appointments", service.getAllAppointments());
    //     return "index";
    // }
}

