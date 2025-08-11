package com.example.doctorapp.repository;

import com.example.doctorapp.entity.AvailabilitySlot;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AvailabilitySlotRepository extends JpaRepository<AvailabilitySlot, Long> {
    List<AvailabilitySlot> findByDoctorId(Long doctorId);
}
