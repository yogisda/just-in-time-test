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
@Table(name = "JIT_MILESTONE")
public class Milestone extends TraceableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "JIT_MILESTONE_SEQ")
	@SequenceGenerator(name = "JIT_MILESTONE_SEQ", sequenceName = "JIT_MILESTONE_SEQUENCE", initialValue = 1, allocationSize = 1)
	private Integer milestoneId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date endDate;

	@Column(nullable = false)
	private String name;
	private String description;
	private int estimatedHours;

	@ManyToOne
	@JoinColumn(name = "projectId", nullable = false)
	private Project project;

	@OneToMany(mappedBy = "milestone", cascade = CascadeType.ALL)
	private Collection<TimeBooking> timebookings;

	public Collection<TimeBooking> getTimebookings() {
		return timebookings;
	}

	public void setTimebookings(Collection<TimeBooking> timebookings) {
		this.timebookings = timebookings;
	}

	public Integer getMilestoneId() {
		return milestoneId;
	}

	public void setMilestoneId(Integer milestoneId) {
		this.milestoneId = milestoneId;
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

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public int getEstimatedHours() {
		return estimatedHours;
	}

	public void setEstimatedHours(int estimatedHours) {
		this.estimatedHours = estimatedHours;
	}

	@Override
	public String toString() {
		return "Milestone [milestoneId=" + milestoneId + ", endDate=" + endDate + ", name=" + name + ", description="
				+ description + ", estimatedHours=" + estimatedHours + "]";
	}
}
