package edu.hsalbsig.swarch.justintime.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "JIT_PROJECTROLE")
public class ProjectRole extends TraceableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "JIT_PROJECTROLE_SEQ")
	@SequenceGenerator(name = "JIT_PROJECTROLE_SEQ", sequenceName = "JIT_PROJECTROLE_SEQUENCE", initialValue = 1, allocationSize = 1)
	private Integer roleId;

	@Column(nullable = false)
	private String description;

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
