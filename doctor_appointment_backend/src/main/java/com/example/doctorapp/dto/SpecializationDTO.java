package com.example.doctorapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpecializationDTO {
    private Long id;

    @NotBlank(message = "Specialization name is required")
    private String name;

    private String description;


	public SpecializationDTO(Long id, @NotBlank(message = "Specialization name is required") String name,
			String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
