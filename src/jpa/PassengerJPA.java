package jpa;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "passenger")
public class PassengerJPA extends PersonJPA {

	private static final long serialVersionUID = 1L;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "passenger")
	private List<DriverCommentJPA> comments;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "passengers_trips", joinColumns = {
			@JoinColumn(name = "passenger_id", nullable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "trip_id", nullable = false) })
	private List<TripJPA> trips;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "passenger")
	public List<DriverCommentJPA> getComments() {
		return comments;
	}

	public void setComments(List<DriverCommentJPA> comments) {
		this.comments = comments;
	}

	public List<TripJPA> getTrips() {
		return trips;
	}

	public void setTrips(List<TripJPA> trips) {
		this.trips = trips;
	}
}
