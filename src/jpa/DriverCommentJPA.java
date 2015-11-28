package jpa;
import java.io.Serializable;

import javax.persistence.*;

/**
 * JPA Class DriverCommentJPA
 */
@Entity
@Table(name="drivercomment")
public class DriverCommentJPA implements Serializable {
	@OneToOne(cascade=CascadeType.PERSIST)

	private static final long serialVersionUID = 1L;

	private String driverId;
	private String passengerId;
	private String comment;
	private int ratting;
	private DriverJPA driver;
	private PassengerJPA passenger;

		
	/**
	 * Class constructor methods
	 */
	public DriverCommentJPA() {
		super();
	}
	public DriverCommentJPA(String driverId, String passengerId, String comment, int ratting) {		
		this.driverId = driverId;
		this.passengerId = passengerId;
		this.comment = comment;
		this.ratting = ratting;
		
	}
	

	/**
	 *  Methods get/set the fields of database
	 */
	@Id
	public String getDriverId() {
		return driverId;
	}
	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}
	@Id
	public String getPassengerId() {
		return passengerId;
	}
	public void setPassengerId(String passengerId) {
		this.passengerId = passengerId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getRatting() {
		return ratting;
	}
	public void setRatting(int ratting) {
		this.ratting = ratting;
	}
	

	/**
	 * Methods get/set persistent relationships
	 */
	@ManyToOne
	@JoinColumn (name="driverId")
	public DriverJPA getDriver() {
		return driver;
	}
	public  void setDriver(DriverJPA driver) {
		this.driver = driver;
	}
	
	@ManyToOne
	@JoinColumn (name="passengerId")
	public PassengerJPA getPassenger() {
		return passenger;
	}
	public  void setPassenger(PassengerJPA passenger) {
		this.passenger = passenger;
	}
		

}
