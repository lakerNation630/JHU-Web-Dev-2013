package jhu.web.java.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * This object encapsulates the information related to a user of the system.
 *
 */
@Entity
public class User {

	private String address;
	private String firstName;
	private String lastName;
	private String zipCode;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	public String getAddress() {
		return address;
	}
	public String getFirstName() {
		return firstName;
	}
	public Long getId() {
		return id;
	}
	public String getLastName() {
		return lastName;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
}
