package com.example.doctorapp.dto;

import com.example.doctorapp.entity.AvailabilitySlot;

import lombok.Data;

@Data
public class TimeSlotDTO {
    private Long id;
    private String startTime;
    private String endTime;
    private boolean isBooked;
    private AvailabilitySlot availabilitySlot;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public boolean isBooked() {
		return isBooked;
	}
	public void setBooked(boolean isBooked) {
		this.isBooked = isBooked;
	}

	public AvailabilitySlot getAvailabilitySlot() {
		return availabilitySlot;
	}
	public void setAvailabilitySlot(AvailabilitySlot availabilitySlot) {
		this.availabilitySlot = availabilitySlot;
	}
}