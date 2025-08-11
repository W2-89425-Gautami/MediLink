package com.example.doctorapp.service;

import com.example.doctorapp.dto.AppointmentDTO;
import java.util.List;

public interface AppointmentService {
    AppointmentDTO createAppointment(AppointmentDTO appointmentDto);
    AppointmentDTO getAppointmentById(Long id);
    List<AppointmentDTO> getAllAppointments();
    AppointmentDTO updateAppointment(Long id, AppointmentDTO appointmentDto);
   // void cancelAppointment(Long id);
	//void cancelAppointment(Long id);
	void cancelAppointment(Long id);
}
