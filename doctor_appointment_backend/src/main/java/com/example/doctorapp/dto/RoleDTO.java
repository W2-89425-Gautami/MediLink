package com.example.doctorapp.dto;

import lombok.Data;

@Data
public class RoleDTO {
    private Integer id;
    private String name;  // Example: ROLE_ADMIN, ROLE_DOCTOR, ROLE_USER
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
