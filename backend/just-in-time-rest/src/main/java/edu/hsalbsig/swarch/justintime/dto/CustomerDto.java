package edu.hsalbsig.swarch.justintime.dto;

public class CustomerDto {
	private Integer id;
	private String name;
	private String street;
	private String zipcode;
	private String city;

	public CustomerDto() {
	}

	public CustomerDto(Integer id, String name, String street, String zipcode, String city) {
		this.id = id;
		this.name = name;
		this.street = street;
		this.zipcode = zipcode;
		this.city = city;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
