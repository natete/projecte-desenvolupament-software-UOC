package jpa;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * JPA Class DriverJPA
 */
@Entity
@Table(name = "driver")
@NamedQueries({
	@NamedQuery(name = "findDriver", query = "SELECT d " + "FROM DriverJPA d " + "WHERE d.email = "
			+ ":email" + " AND " + "d.password = :password"),
	@NamedQuery(name = "findMyDriver", query = "SELECT d " + "FROM DriverJPA d " + "WHERE d.nif = :nif")
})
public class DriverJPA implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nif;
	private String name;
	private String surname;
	private String phone;
	private String password;
	private String email;
	private Collection<CarJPA> cars;
	private Collection<TripJPA> trips;
	private Collection<DriverCommentJPA> driverComments;
	private Collection<MessageJPA> messages;

	/**
	 * Class constructor methods
	 */
	public DriverJPA() {
		super();
	}

	public DriverJPA(String nif, String name, String surname, String phone, String password,
			String email) {
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

	@OneToMany(mappedBy = "driver")
	public Collection<CarJPA> getCars() {
		return cars;
	}

	public void setCars(Collection<CarJPA> cars) {
		this.cars = cars;
	}

	// persistent relationships
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "driver")
	public Collection<TripJPA> getTrips() {
		return trips;
	}

	public void setTrips(Collection<TripJPA> trips) {
		this.trips = trips;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "driver")
	public Collection<DriverCommentJPA> getDriverComments() {
		return driverComments;
	}

	public void setDriverComments(Collection<DriverCommentJPA> driverComments) {
		this.driverComments = driverComments;
	}

	// persistent relationships
	@OneToMany(mappedBy = "driver")
	public Collection<MessageJPA> getMessages() {
		return messages;
	}

	public void setMessages(Collection<MessageJPA> messages) {
		this.messages = messages;
	}

	@Transient
	public int getRating() {
		int result = 0;

		if (driverComments != null && driverComments.size() > 0) {
			for (DriverCommentJPA driverCommentJPA : driverComments) {
				result += driverCommentJPA.getRating();
			}

			result = result / driverComments.size();
		}
		return result;
	}
}
