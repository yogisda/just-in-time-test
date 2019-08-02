package edu.hsalbsig.swarch.justintime.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeDto implements IValidateable {
	private Integer id;
	private String name;
	private String email;
	private float hoursPerDay;

	public EmployeeDto() {

	}

	public EmployeeDto(Integer id, String name, String email, float hoursPerDay) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.hoursPerDay = hoursPerDay;
	}

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public float getHoursPerDay() {
		return hoursPerDay;
	}

	public void setHoursPerDay(float hoursPerDay) {
		this.hoursPerDay = hoursPerDay;
	}

	@Override
	public void validate() throws DataValidationException {
		if (name == null || name.isBlank()) {
			throw new DataValidationException();
		}

		if (email == null || email.isBlank()) {
			throw new DataValidationException();
		}

		if (hoursPerDay < 0) {
			throw new DataValidationException();
		}
	}
}