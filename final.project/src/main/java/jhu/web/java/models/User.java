package jhu.web.java.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * This object encapsulates the information related to a user of the system.
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name="User.findAll",query="Select u from User u"),
	@NamedQuery(name="User.findByUsername",query="Select u from User u Where u.username = :username")
})
public class User {

	private String address;
	private String firstName;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String lastName;
	private String password;
	/** The registered username of the user within the system*/
	private String username;
	private String zipCode;
	
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
	
	public String getPassword() {
		return password;
	}
	public String getUsername() {
		return username;
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
	public void setPassword(String password) {
		this.password = password;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
}
