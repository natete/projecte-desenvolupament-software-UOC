package jpa;

import java.io.Serializable;

import javax.persistence.*;

/**
 * JPA Class DriverCommentJPA
 */
@Entity
@Table(name = "drivercomment")
public class DriverCommentJPA implements Serializable {

	private static final long serialVersionUID = 1L;

	private String comment;
	private int rating;
	private DriverJPA driver;
	private PassengerJPA passenger;

	/**
	 * Class constructor methods
	 */
	public DriverCommentJPA() {
		super();
	}

	public DriverCommentJPA(DriverJPA driver, PassengerJPA passenger, String comment, int rating) {
		this.driver = driver;
		this.passenger = passenger;
		this.comment = comment;
		this.rating = rating;

	}

	/**
	 * Methods get/set the fields of database
	 */
	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}

	/**
	 * Methods get/set persistent relationships
	 */
	
	@Id
	@ManyToOne
	@JoinColumn(name = "driverId")
	public DriverJPA getDriver() {
		return driver;
	}

	
	public void setDriver(DriverJPA driver) {
		this.driver = driver;
	}
	
	@Id
	@ManyToOne
    @JoinColumn (name="passengerId")
	public PassengerJPA getPassenger() {
		return passenger;
	}
	
	public void setPassenger(PassengerJPA passenger) {
		this.passenger = passenger;
	}
}
