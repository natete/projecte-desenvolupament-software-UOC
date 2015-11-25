package jpa;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "driver")
public class DriverJPA extends PersonJPA {

	private static final long serialVersionUID = 1L;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "driver")
	List<CarJPA> cars;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "driver")
	List<TripJPA> trips;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "driver")
	private List<DriverCommentJPA> comments;

	public List<CarJPA> getCars() {
		return cars;
	}

	public void setCars(List<CarJPA> cars) {
		this.cars = cars;
	}

	public List<TripJPA> getTrips() {
		return trips;
	}

	public void setTrips(List<TripJPA> trips) {
		this.trips = trips;
	}

	public List<DriverCommentJPA> getComments() {
		return comments;
	}

	public void setComment(List<DriverCommentJPA> comments) {
		this.comments = comments;
	}

	public float getGlobalRating() {
		float globalRating = 0;

		for (DriverCommentJPA comment : comments) {
			globalRating += comment.getRating();
		}
		return globalRating / comments.size();
	}
}
