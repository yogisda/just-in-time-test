package edu.hsalbsig.swarch.justintime.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ProjectDto {
	private Integer id;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private Date startDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private Date endDate;
	private String name;
	private String description;
	private boolean isFinished;
	private Integer estimatedHours;
	private Integer customer;

	public ProjectDto() {

	}

	public ProjectDto(Integer id, Date startDate, Date endDate, String name, String description, boolean isFinished,
			Integer estimatedHours, Integer customer) {
		this.id = id;
		this.startDate = startDate;
		this.endDate = endDate;
		this.name = name;
		this.description = description;
		this.isFinished = isFinished;
		this.estimatedHours = estimatedHours;
		this.customer = customer;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	public boolean isFinished() {
		return isFinished;
	}

	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}

	public Integer getEstimatedHours() {
		return estimatedHours;
	}

	public void setEstimatedHours(Integer estimatedHours) {
		this.estimatedHours = estimatedHours;
	}

	public Integer getCustomer() {
		return customer;
	}

	public void setCustomer(Integer customer) {
		this.customer = customer;
	}
}
