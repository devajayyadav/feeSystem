package com.appoitment.feesystem.service;




import com.appoitment.feesystem.Repository.AppointmentRepository;
import com.appoitment.feesystem.entity.Appointment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    private final AppointmentRepository repository;

    public AppointmentService(AppointmentRepository repository) {
        this.repository = repository;
    }

    public List<Appointment> getAllAppointments() {
        return repository.findAll();
    }

    public Appointment saveAppointment(Appointment appointment) {
        return repository.save(appointment);
    }

    public void deleteAppointment(Long id) {
        repository.deleteById(id);
    }

    public Appointment getAppointmentById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Appointment not found"));
    }

    public List<String> getBookedTimes(String date) {
        return repository.findByDate(date)
                .stream()
                .map(Appointment::getTime)
                .collect(Collectors.toList());
    }
}
