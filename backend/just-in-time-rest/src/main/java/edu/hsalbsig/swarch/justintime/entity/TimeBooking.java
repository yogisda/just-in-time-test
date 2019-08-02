package edu.hsalbsig.swarch.justintime.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import edu.hsalbsig.swarch.justintime.entity.Employee;
import edu.hsalbsig.swarch.justintime.entity.Milestone;
import edu.hsalbsig.swarch.justintime.entity.TraceableEntity;

@Entity
@Table(name = "JIT_TIMEBOOKING")
public class TimeBooking extends TraceableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "JIT_TIMEBOOKING_SEQ")
	@SequenceGenerator(name = "JIT_TIMEBOOKING_SEQ", sequenceName = "JIT_TIMEBOOKING_SEQUENCE", initialValue = 1, allocationSize = 1)
	private Integer bookingId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date startTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date endTime;

	private String comments;

	@ManyToOne
	@JoinColumn(name = "employeeId", nullable = false)
	private Employee employee;

	@ManyToOne
	@JoinColumn(name = "milestoneId")
	private Milestone milestone;

	public Integer getBookingId() {
		return bookingId;
	}

	public void setBookingId(Integer bookingId) {
		this.bookingId = bookingId;
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

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Milestone getMilestone() {
		return milestone;
	}

	public void setMilestone(Milestone milestone) {
		this.milestone = milestone;
	}

}
