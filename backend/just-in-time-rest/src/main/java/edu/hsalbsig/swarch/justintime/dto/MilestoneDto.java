package edu.hsalbsig.swarch.justintime.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MilestoneDto {
	private Integer id;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private Date endDate;
	private String name;
	private String description;
	private int estimatedHours;
	private Integer project;

	public MilestoneDto() {

	}

	public MilestoneDto(Integer id, Date endDate, String name, String description, int estimatedHours) {
		this.id = id;
		this.endDate = endDate;
		this.name = name;
		this.description = description;
		this.estimatedHours = estimatedHours;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public int getEstimatedHours() {
		return estimatedHours;
	}

	public void setEstimatedHours(int estimatedHours) {
		this.estimatedHours = estimatedHours;
	}

	public Integer getProject() {
		return project;
	}

	public void setProject(Integer project) {
		this.project = project;
	}
}
