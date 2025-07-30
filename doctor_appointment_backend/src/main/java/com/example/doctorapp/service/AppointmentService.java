package com.example.doctorapp.service;

import com.example.doctorapp.dto.AppointmentDTO;
import java.util.List;

public interface AppointmentService {
    AppointmentDTO bookAppointment(AppointmentDTO appointmentDTO);
    List<AppointmentDTO> getAllAppointments();
    AppointmentDTO getAppointmentById(Long id);
    void cancelAppointment(Long id);
}
