package edu.hsalbsig.swarch.justintime.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class ProjectMembershipId implements Serializable {
	private static final long serialVersionUID = -8295638038931113726L;

	private Integer employeeId;
	private Integer projectId;

	public ProjectMembershipId() {

	}

	public ProjectMembershipId(Integer employeeId, Integer projectId) {
		this.employeeId = employeeId;
		this.projectId = projectId;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((employeeId == null) ? 0 : employeeId.hashCode());
		result = prime * result + ((projectId == null) ? 0 : projectId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProjectMembershipId other = (ProjectMembershipId) obj;
		if (employeeId == null) {
			if (other.employeeId != null)
				return false;
		} else if (!employeeId.equals(other.employeeId))
			return false;
		if (projectId == null) {
			if (other.projectId != null)
				return false;
		} else if (!projectId.equals(other.projectId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProjectMembershipId [employeeId=" + employeeId + ", projectId=" + projectId + "]";
	}

}
