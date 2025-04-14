package com.appoitment.feesystem.controller;

import com.appoitment.feesystem.entity.Appointment;
import com.appoitment.feesystem.entity.AppointmentDetailsDTO;
import com.appoitment.feesystem.service.AppointmentService;
import com.appoitment.feesystem.service.PdfService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class AppointmentController {

    private final AppointmentService service;
    private final PdfService pdfService;

    public AppointmentController(AppointmentService service, PdfService pdfService) {
        this.service = service;
        this.pdfService = pdfService;
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
    public String saveAppointment(@ModelAttribute Appointment appointment, Model model) {
        Appointment savedAppointment = service.saveAppointment(appointment);
        AppointmentDetailsDTO appointmentDetails = convertToDTO(savedAppointment);
        model.addAttribute("appointmentDetails", appointmentDetails);
        return "appointment-confirmation";
    }

    @GetMapping("/delete/{id}")
    public String deleteAppointment(@PathVariable Long id) {
        service.deleteAppointment(id);
        return "redirect:../list";
    }

    @GetMapping("/download-pdf/{id}")
    public ResponseEntity<byte[]> downloadAppointmentPdf(@PathVariable Long id) throws IOException {
        Appointment appointment = service.getAppointmentById(id);
        AppointmentDetailsDTO appointmentDetails = convertToDTO(appointment);
        byte[] pdfBytes = pdfService.generateAppointmentPdf(appointmentDetails);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "appointment_" + id + ".pdf");
        headers.setContentLength(pdfBytes.length);

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }

    private AppointmentDetailsDTO convertToDTO(Appointment appointment) {
        AppointmentDetailsDTO dto = new AppointmentDetailsDTO();
        dto.setId(appointment.getId());
        dto.setStudentId(appointment.getStudentId());
        dto.setName(appointment.getName());
        dto.setDate(appointment.getDate());
        dto.setTime(appointment.getTime());
        dto.setStatus(appointment.getStatus());
        dto.setConfirmationNumber(appointment.getConfirmationNumber());
        dto.setDepartment(appointment.getDepartment());
        dto.setPurpose(appointment.getPurpose());
        dto.setNotes(appointment.getNotes());
        return dto;
    }
}
