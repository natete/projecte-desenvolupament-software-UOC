package jpa;

import java.io.Serializable;

import javax.persistence.*;

/**
 * JPA Class CarJPA
 */
@Entity
@Table(name="car")
public class CarJPA implements Serializable {

	private static final long serialVersionUID = 1L;

	private String carRegistrationId;
	private String brand;
	private String model;
	private String color;
//	private Collection<TripJPA> trips;
	private DriverJPA driver;
	
	/**
	 * Class constructor methods
	 */
	public CarJPA() {
		super();
	}

	public CarJPA(String carRegistrationId, String brand, String model, String color) {
		this.carRegistrationId = carRegistrationId;
		this.brand = brand;
		this.model = model;
		this.color = color;
	}

	/**
	 * Methods get/set the fields of database
	 */
	
	@Id
	public String getCarRegistrationId() {
		return carRegistrationId;
	}

	public void setCarRegistrationId(String carRegistrationId) {
		this.carRegistrationId = carRegistrationId;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	// persistent relationships
	/*@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER )
	@JoinColumn(name = "car")
	public Collection<TripJPA> getTripsByCar() {
		return trips;
	}
	
	public void setTripsByCar(Collection<TripJPA> trips) {
		this.trips = trips;
	}
*/
	@ManyToOne
	@JoinColumn (name="driver")
	public DriverJPA getDriver() {
		return driver;
	}
	
	public void setDriver(DriverJPA driver) {
		this.driver = driver;
	}
}
