package edu.hsalbsig.swarch.justintime.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "JIT_PROJECTMEMBERSHIP")
public class ProjectMembership extends TraceableEntity {
	@EmbeddedId
	private ProjectMembershipId projectMembershipId = new ProjectMembershipId();

	private float capacity;

	@ManyToOne
	@JoinColumn(name = "roleId", nullable = false)
	private ProjectRole role;

	@ManyToOne
	@MapsId("employeeId")
	private Employee employee;

	@ManyToOne
	@MapsId("projectId")
	private Project project;

	public ProjectMembershipId getProjectMembershipId() {
		return projectMembershipId;
	}

	public void setProjectMembershipId(ProjectMembershipId projectMembershipId) {
		this.projectMembershipId = projectMembershipId;
	}

	public float getCapacity() {
		return capacity;
	}

	public void setCapacity(float capacity) {
		this.capacity = capacity;
	}

	public ProjectRole getRole() {
		return role;
	}

	public void setRole(ProjectRole role) {
		this.role = role;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
}
