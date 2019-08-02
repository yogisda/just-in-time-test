package edu.hsalbsig.swarch.justintime.entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "JIT_EMPLOYEE")
public class Employee extends TraceableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "JIT_EMPLOYEE_SEQ")
	@SequenceGenerator(name = "JIT_EMPLOYEE_SEQ", sequenceName = "JIT_EMPLOYEE_SEQUENCE", initialValue = 1, allocationSize = 1)
	private Integer employeeId;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String passwordHash;

	private float hoursPerDay;

	@ManyToOne
	@JoinColumn(name = "roleId", nullable = false)
	private UserRole userRole;

	@OneToMany(mappedBy = "employee")
	private Collection<TimeBooking> timebookings;
	
	@OneToMany(mappedBy = "employee")
	private Collection<ProjectMembership> projectMemberships;

	public Collection<TimeBooking> getTimebookings() {
		return timebookings;
	}

	public void setTimebookings(Collection<TimeBooking> timebookings) {
		this.timebookings = timebookings;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
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

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public float getHoursPerDay() {
		return hoursPerDay;
	}

	public void setHoursPerDay(float hoursPerDay) {
		this.hoursPerDay = hoursPerDay;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

    public Collection<ProjectMembership> getProjectMemberships() {
        return projectMemberships;
    }

    public void setProjectMemberships(Collection<ProjectMembership> projectMemberships) {
        this.projectMemberships = projectMemberships;
    }

}
