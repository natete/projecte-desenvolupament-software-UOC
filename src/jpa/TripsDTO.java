package jpa;

import java.io.Serializable;
import java.util.List;

public class TripsDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<TripJPA> trips;
	private Long total;

	public List<TripJPA> getTrips() {
		return trips;
	}

	public void setTrips(List<TripJPA> trips) {
		this.trips = trips;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}
}
