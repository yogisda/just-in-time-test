package edu.hsalbsig.swarch.justintime.dto;

public class ProjectMembershipDto {
	private Integer project;
	private EmployeeDto employee;
	private float capacity;
	private RoleDto role;

	public ProjectMembershipDto() {

	}

	public ProjectMembershipDto(Integer project, EmployeeDto employee, float capacity, RoleDto role) {
		this.project = project;
		this.employee = employee;
		this.capacity = capacity;
		this.role = role;
	}

	public Integer getProject() {
		return project;
	}

	public void setProject(Integer project) {
		this.project = project;
	}

	public EmployeeDto getEmployee() {
		return employee;
	}

	public void setEmployee(EmployeeDto employeeDto) {
		this.employee = employeeDto;
	}

	public float getCapacity() {
		return capacity;
	}

	public void setCapacity(float capacity) {
		this.capacity = capacity;
	}

	public RoleDto getRole() {
		return role;
	}

	public void setRole(RoleDto role) {
		this.role = role;
	}
}
