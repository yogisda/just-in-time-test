package edu.hsalbsig.swarch.justintime.entity;

import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "JIT_PROJECT")
public class Project extends TraceableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "JIT_PROJECT_SEQ")
	@SequenceGenerator(name = "JIT_PROJECT_SEQ", sequenceName = "JIT_PROJECT_SEQUENCE", initialValue = 1, allocationSize = 1)
	private Integer projectId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date startDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date endDate;

	@Column(nullable = false)
	private String name;

	private String description;

	private boolean isFinished;

	private int estimatedHours;

	@ManyToOne
	@JoinColumn(name = "customerId")
	private Customer customer;

	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
	private Collection<Milestone> milestones;

	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
	private Collection<ProjectMembership> projectMembership;

	public Collection<ProjectMembership> getProjectMembership() {
		return projectMembership;
	}

	public void setProjectMembership(Collection<ProjectMembership> projectMembership) {
		this.projectMembership = projectMembership;
	}

	public Collection<Milestone> getMilestones() {
		return milestones;
	}

	public void setMilestones(Collection<Milestone> milestones) {
		this.milestones = milestones;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
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

	public int getEstimatedHours() {
		return estimatedHours;
	}

	public void setEstimatedHours(int estimatedHours) {
		this.estimatedHours = estimatedHours;
	}

}
