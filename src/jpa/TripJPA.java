package jpa;
import java.io.Serializable;

import javax.persistence.*;

/**
 * JPA Class TripJPA
 */
@Entity
@Table(name="trip")
public class TripJPA implements Serializable {
	@OneToOne(cascade=CascadeType.PERSIST)

	private static final long serialVersionUID = 1L;

	private String tripId;
	private String description;
	private String driverId;
	private String passengerId;
	
		
	/**
	 * Class constructor methods
	 */
	public TripJPA() {
		super();
	}
	public TripJPA(String tripId, String description, String driverId, String passengerId) {		
		this.tripId = tripId;
		this.description = description;
		this.driverId = driverId;
		this.passengerId = passengerId;
			
	}
	@Id
	public String getTripId() {
		return tripId;
	}
	public void setTripId(String tripId) {
		this.tripId = tripId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDriverId() {
		return driverId;
	}
	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}
	public String getPassengerId() {
		return passengerId;
	}
	public void setPassengerId(String passengerId) {
		this.passengerId = passengerId;
	}
	
	
	

	
}
