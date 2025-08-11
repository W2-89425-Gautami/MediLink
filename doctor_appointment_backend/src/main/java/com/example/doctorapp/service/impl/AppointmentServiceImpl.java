package com.example.doctorapp.service.impl;

import com.example.doctorapp.entity.Appointment;
import com.example.doctorapp.entity.Doctor;
import com.example.doctorapp.entity.Patient;
import com.example.doctorapp.exception.ResourceNotFoundException;
import com.example.doctorapp.dto.AppointmentDTO;
import com.example.doctorapp.repository.AppointmentRepository;
import com.example.doctorapp.repository.DoctorRepository;
import com.example.doctorapp.repository.PatientRepository;
import com.example.doctorapp.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public AppointmentDTO createAppointment(AppointmentDTO dto) {
        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
            .orElseThrow(() -> new ResourceNotFoundException("Doctor", "id", dto.getDoctorId()));
        Patient patient = patientRepository.findById(dto.getPatientId())
            .orElseThrow(() -> new ResourceNotFoundException("Patient", "id", dto.getPatientId()));

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setAppointmentDate(dto.getAppointmentDate());
        appointment.setStatus(dto.getStatus());

        return mapToDto(appointmentRepository.save(appointment));
    }

    @Override
    public AppointmentDTO getAppointmentById(Long id) {
        Appointment appt = appointmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", id));
        return mapToDto(appt);
    }

    @Override
    public List<AppointmentDTO> getAllAppointments() {
        return appointmentRepository.findAll()
            .stream().map(this::mapToDto)
            .collect(Collectors.toList());
    }

    @Override
    public AppointmentDTO updateAppointment(Long id, AppointmentDTO dto) {
        Appointment appt = appointmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", id));

        appt.setAppointmentDate(dto.getAppointmentDate());
        appt.setStatus(dto.getStatus());

        return mapToDto(appointmentRepository.save(appt));
    }

    @Override
    public void cancelAppointment(Long id) {
        Appointment appt = appointmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", id));
        appointmentRepository.delete(appt);
    }

    private AppointmentDTO mapToDto(Appointment appt) {
        AppointmentDTO dto = new AppointmentDTO();
        dto.setId(appt.getId());
        dto.setDoctorId(appt.getDoctor().getId());
        dto.setPatientId(appt.getPatient().getId());
        dto.setAppointmentDate(appt.getAppointmentDate());
        dto.setStatus(appt.getStatus());
        return dto;
    }
}
