package jpa;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.*;

/**
 * JPA Class DriverJPA
 */
@Entity
@Table(name="driver")
public class DriverJPA implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nif;
	private String name;
	private String surname;
	private String phone;
	private String password;
	private String email;
//	private Collection<TripJPA> trips;
//	private Collection<DriverCommentsJPA> driverComments;
//	private Collection<MessageJPA> messages;
	
	/**
	 * Class constructor methods
	 */
	public DriverJPA() {
		super();
	}

	public DriverJPA(String nif, String name, String surname, String phone, String password, String email) {
		this.nif = nif;
		this.name = name;
		this.surname = surname;
		this.phone = phone;
		this.password = password;
		this.email = email;

	}

	/**
	 * Methods get/set the fields of database
	 */
	
	@Id
	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	// persistent relationships
	/*@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER )
	@JoinColumn(name = "driver")
	public Collection<TripJPA> getTripsByDriver() {
		return trips;
	}
	
	public void setTripsByDriver(Collection<TripJPA> trips) {
		this.trips = trips;
	}*/

	/*@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER )
	@JoinColumn(name = "driver")
	public Collection<DriverCommentJPA> getDriverCommentsByDriver() {
		return driverComments;
	}
	
	public void setDriverCommentsByDriver(Collection<DriverCommentsJPA> driverComments) {
		this.driverComments = driverComments;
	}*/
	// persistent relationships

	/*@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER )
	@JoinColumn(name = "driver")
	public Collection<messageJPA> getMessagesByDriver() {
		return messages;
	}
	
	public void setMessagesByDriver(Collection<MessageJPA> messages) {
		this.messages = messages;
	}*/

}
