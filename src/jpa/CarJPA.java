package jpa;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * JPA Class CarJPA
 */
@Entity
@Table(name = "car")
@NamedQueries({
		@NamedQuery(name = "CarJPA.findCarsByDriver", query = "SELECT c FROM CarJPA c WHERE c.driver = :driver"),
		@NamedQuery(name = "CarJPA.findCarsByDriverId", query = "SELECT c FROM CarJPA c WHERE c.driver.nif = :nif"),
		@NamedQuery(name = "CarJPA.findCarById", query = "SELECT c FROM CarJPA c WHERE c.carRegistrationId = :carRegistrationId"),
		@NamedQuery(name = "CarJPA.findCarByBrandModelColour", query = "SELECT c FROM CarJPA c WHERE c.brand = :brand AND c.model = :model AND c.color = :colour"), })
public class CarJPA implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String carRegistrationId;

	@Column(name = "brand")
	private String brand;

	@Column(name = "model")
	private String model;

	@Column(name = "color")
	private String color;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "car")
	private Collection<TripJPA> trips;

	@ManyToOne
	@JoinColumn(name = "driver")
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

	public Collection<TripJPA> getTrips() {
		return trips;
	}

	public void setTrips(Collection<TripJPA> trips) {
		this.trips = trips;
	}

	public DriverJPA getDriver() {
		return driver;
	}

	public void setDriver(DriverJPA driver) {
		this.driver = driver;
	}
}
