package com.example.doctorapp.repository;

import com.example.doctorapp.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {

	List<TimeSlot> findByAvailabilitySlotId(Long availabilitySlotId);
  
}
