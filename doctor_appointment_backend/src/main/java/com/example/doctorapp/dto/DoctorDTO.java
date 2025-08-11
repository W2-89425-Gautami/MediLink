package com.example.doctorapp.dto;

public class DoctorDTO {
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String qualification;
    private int experience; // years of experience
    private Long specializationId; // Reference to Specialization entity
    private Long userId; // Reference to User entity

    // Constructors
    public DoctorDTO() {
    }

    public DoctorDTO(Long id, String fullName, String email, String phoneNumber, String qualification, int experience, Long specializationId, Long userId) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.qualification = qualification;
        this.experience = experience;
        this.specializationId = specializationId;
        this.userId = userId;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public Long getSpecializationId() {
        return specializationId;
    }

    public void setSpecializationId(Long specializationId) {
        this.specializationId = specializationId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
