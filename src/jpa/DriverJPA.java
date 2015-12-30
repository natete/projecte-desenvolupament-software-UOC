package jpa;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * JPA Class DriverJPA
 */
@Entity
@Table(name = "driver")
@PrimaryKeyJoinColumn(name = "userId")
@NamedQueries({
		@NamedQuery(name = "DriverJPA.driverLogin", query = "SELECT d FROM DriverJPA d WHERE d.email = :email AND d.password = :password"),
		@NamedQuery(name = "findMyDriver", query = "SELECT d FROM DriverJPA d WHERE d.nif = :nif"),
		@NamedQuery(name = "DriverJPA.getByNif", query = "SELECT d FROM DriverJPA d WHERE d.nif = :nif") })
public class DriverJPA extends UserJPA {

	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "driver")
	private Collection<CarJPA> cars;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "driver")
	private Collection<TripJPA> trips;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "driver")
	private Collection<DriverCommentJPA> driverComments;

	@OneToMany(mappedBy = "driver")
	private Collection<MessageJPA> messages;

	/**
	 * Class constructor methods
	 */
	public DriverJPA() {
		super();
	}

	/**
	 * Methods get/set the fields of database
	 */

	public Collection<CarJPA> getCars() {
		return cars;
	}

	public void setCars(Collection<CarJPA> cars) {
		this.cars = cars;
	}

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
