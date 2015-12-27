package jpa;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * JPA Class PassengerJPA
 */
@Entity
@Table(name = "passenger")
@PrimaryKeyJoinColumn(name = "userId")
@NamedQueries({
		@NamedQuery(name = "PassengerJPA.passengerLogin", query = "SELECT p FROM PassengerJPA p WHERE p.email = :email AND p.password = :password"),
		@NamedQuery(name = "PassengerJPA.getByNif", query = "SELECT p FROM PassengerJPA p WHERE p.nif = :nif") })
public class PassengerJPA extends UserJPA {

	private static final long serialVersionUID = 1L;

	@ManyToMany
	@JoinTable(name = "passengers_trips", joinColumns = {
			@JoinColumn(name = "passenger_id", nullable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "trip_id", nullable = false) })
	private Collection<TripJPA> trips;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "passenger")
	private Collection<DriverCommentJPA> driverComments;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "passenger")
	private Collection<MessageJPA> messages;

	/**
	 * Class constructor methods
	 */
	public PassengerJPA() {
		super();
	}

	/**
	 * Methods get/set the fields of database
	 */

	public Collection<TripJPA> getTrips() {
		return trips;
	}

	public void setTrips(Collection<TripJPA> trips) {
		this.trips = trips;
	}

	public Collection<DriverCommentJPA> getDriverComments() {
		return driverComments;
	}

	public void setDriverComments(Collection<DriverCommentJPA> driverComments) {
		this.driverComments = driverComments;
	}

	public Collection<MessageJPA> getMessages() {
		return messages;
	}

	public void setMessages(Collection<MessageJPA> messages) {
		this.messages = messages;
	}
}
