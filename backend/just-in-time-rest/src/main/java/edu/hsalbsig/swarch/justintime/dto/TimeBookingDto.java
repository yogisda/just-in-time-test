package edu.hsalbsig.swarch.justintime.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TimeBookingDto {
	private Integer id;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private Date startTime;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private Date endTime;
	private String comments;
	private EmployeeDto employee;
	private MilestoneDto milestone;

	public TimeBookingDto(Integer id, Date startTime, Date endTime, String comments, EmployeeDto employee,
			MilestoneDto milestone) {
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
		this.comments = comments;
		this.employee = employee;
		this.milestone = milestone;
	}

	public TimeBookingDto() {

	}

	public MilestoneDto getMilestone() {
		return milestone;
	}

	public void setMilestone(MilestoneDto milestone) {
		this.milestone = milestone;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public EmployeeDto getEmployee() {
		return employee;
	}

	public void setEmployee(EmployeeDto employee) {
		this.employee = employee;
	}

	@Override
	public String toString() {
		return "TimeBookingDto [bookingId=" + id + ", startTime=" + startTime + ", endTime=" + endTime + ", comments="
				+ comments + ", employee=" + employee + "]";
	}
}
