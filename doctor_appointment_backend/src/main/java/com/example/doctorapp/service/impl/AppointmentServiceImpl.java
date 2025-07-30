package com.example.doctorapp.service.impl;

import com.example.doctorapp.dto.AppointmentDTO;
import com.example.doctorapp.entity.Appointment;
import com.example.doctorapp.entity.Doctor;
import com.example.doctorapp.entity.Patient;
import com.example.doctorapp.repository.AppointmentRepository;
import com.example.doctorapp.repository.DoctorRepository;
import com.example.doctorapp.repository.PatientRepository;
import com.example.doctorapp.service.AppointmentService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    @Override
    public AppointmentDTO bookAppointment(AppointmentDTO dto) {
        Doctor doctor = doctorRepository.findById(dto.getDoctorId()).orElseThrow();
        Patient patient = patientRepository.findById(dto.getPatientId()).orElseThrow();

        Appointment appointment = new Appointment();
        appointment.setDate(dto.getDate());
        appointment.setTime(dto.getTime());
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        return toDTO(appointmentRepository.save(appointment));
    }

    @Override
    public List<AppointmentDTO> getAppointmentsByPatientId(Long patientId) {
        return appointmentRepository.findByPatientId(patientId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<AppointmentDTO> getAppointmentsByDoctorId(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    private AppointmentDTO toDTO(Appointment appointment) {
        AppointmentDTO dto = new AppointmentDTO();
        dto.setId(appointment.getId());
        dto.setDate(appointment.getDate());
        dto.setTime(appointment.getTime());
        dto.setDoctorId(appointment.getDoctor().getId());
        dto.setPatientId(appointment.getPatient().getId());
        return dto;
    }
}
