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
@Table(name = "JIT_CUSTOMER")
public class Customer extends TraceableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "JIT_CUSTOMER_SEQ")
	@SequenceGenerator(name = "JIT_CUSTOMER_SEQ", sequenceName = "JIT_CUSTOMER_SEQUENCE", initialValue = 1, allocationSize = 1)
	private Integer customerId;

	@Column(nullable = false)
	private String name;

	private String street;

	private String zipcode;

	private String city;

	@OneToMany(mappedBy = "customer")
	private Collection<Project> project;

	public Collection<Project> getProject() {
		return project;
	}

	public void setProject(Collection<Project> project) {
		this.project = project;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}
