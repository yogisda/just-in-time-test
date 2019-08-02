package edu.hsalbsig.swarch.justintime.entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "JIT_USERROLE")
public class UserRole extends TraceableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "JIT_USERROLE_SEQ")
	@SequenceGenerator(name = "JIT_USERROLE_SEQ", sequenceName = "JIT_USERROLE_SEQUENCE", initialValue = 1, allocationSize = 1)
	private Integer roleId;

	@Column(nullable = false)
	private String description;

	@OneToMany(mappedBy = "userRole")
	private Collection<Employee> employees;

	public Collection<Employee> getEmployees() {
		return employees;
	}

	public void setEmployee(Collection<Employee> employees) {
		this.employees = employees;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
