package jpa;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * JPA Class PassengerJPA
 */
@Entity
@Table(name="passenger")
@NamedQuery(name="findPassenger", query="SELECT p " +
										"FROM PassengerJPA p " +
										"WHERE p.email = :email AND " +
										"      p.password = :password")

public class PassengerJPA implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nif;
	private String name;
	private String surname;
	private String phone;
	private String password;
	private String email;
	private Collection<TripJPA> trips;
	private Collection<DriverCommentJPA> driverComments;
	private Collection<MessageJPA> messages;
	
	/**
	 * Class constructor methods
	 */
	public PassengerJPA() {
		super();
	}

	public PassengerJPA(String nif, String name, String surname, String phone, String password, String email) {
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

	@ManyToMany
	@JoinTable(name = "passengers_trips", joinColumns = {
			@JoinColumn(name = "passenger_id", nullable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "trip_id", nullable = false) })
	public Collection<TripJPA> getTrips() {
		return trips;
	}

	public void setTrips(Collection<TripJPA> trips) {
		this.trips = trips;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "passenger")
	public Collection<DriverCommentJPA> getDriverComments() {
		return driverComments;
	}
	
	public void setDriverComments(Collection<DriverCommentJPA> driverComments) {
		this.driverComments = driverComments;
	}
	
	// persistent relationships
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "passenger")
	public Collection<MessageJPA> getMessages() {
		return messages;
	}
	
	public void setMessages(Collection<MessageJPA> messages) {
		this.messages = messages;
	}
}
/*
package jpa;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "passenger")
@NamedQueries({
		@NamedQuery(name = "findPassenger", query = "SELECT p " + "FROM PassengerJPA p WHERE p.email = :email AND "
				+ "p.password = :password"),
		@NamedQuery(name = "PassengerJPA.getPassengerById", query = "SELECT p FROM PassengerJPA p WHERE p.nif = :passengerId") })
public class PassengerJPA implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nif;
	private String name;
	private String surname;
	private String phone;
	private String password;
	private String email;
	private Collection<TripJPA> trips;
	private Collection<DriverCommentJPA> driverComments;
	private Collection<MessageJPA> messages;

	public PassengerJPA() {
		super();
	}

	public PassengerJPA(String nif, String name, String surname, String phone, String password, String email) {
		this.nif = nif;
		this.name = name;
		this.surname = surname;
		this.phone = phone;
		this.password = password;
		this.email = email;

	}

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

	@ManyToMany(mappedBy = "passengers")
	public Collection<TripJPA> getTrips() {
		return trips;
	}

	public void setTrips(Collection<TripJPA> trips) {
		this.trips = trips;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "passenger")
	public Collection<DriverCommentJPA> getDriverComments() {
		return driverComments;
	}

	public void setDriverComments(Collection<DriverCommentJPA> driverComments) {
		this.driverComments = driverComments;
	}

	// persistent relationships
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "passenger")
	public Collection<MessageJPA> getMessages() {
		return messages;
	}

	public void setMessages(Collection<MessageJPA> messages) {
		this.messages = messages;
	}
}
*/