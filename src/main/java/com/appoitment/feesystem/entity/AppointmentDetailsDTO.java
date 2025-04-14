package com.appoitment.feesystem.entity;

import lombok.Data;

@Data
public class AppointmentDetailsDTO {
    private Long id;
    private String studentId;
    private String name;
    private String date;
    private String time;
    private String status;
    private String confirmationNumber;
    private String department;
    private String purpose;
    private String notes;
} 