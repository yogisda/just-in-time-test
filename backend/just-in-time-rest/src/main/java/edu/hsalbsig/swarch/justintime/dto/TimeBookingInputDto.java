package edu.hsalbsig.swarch.justintime.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TimeBookingInputDto {
    private Integer id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date endTime;
    private String comments;
    private int employee;
    private int milestone;
    
    public TimeBookingInputDto() {
        // TODO Auto-generated constructor stub
    }

    public TimeBookingInputDto(Integer id, Date startTime, Date endTime, String comments, int employee, int milestone) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.comments = comments;
        this.employee = employee;
        this.milestone = milestone;
    }
    
    public Integer getId() {
        return this.id;
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

    public int getEmployee() {
        return employee;
    }

    public void setEmployee(int employee) {
        this.employee = employee;
    }

    public int getMilestone() {
        return milestone;
    }

    public void setMilestone(int milestone) {
        this.milestone = milestone;
    }
    
    
}
