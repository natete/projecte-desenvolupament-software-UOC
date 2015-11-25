package jpa;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * JPA Class PetJPA
 */
@Entity
@Table(name = "car")
public class CarJPA implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer carRegistrationId;
	private String brand;
	private String model;
	private String color;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "driver_id")
	private DriverJPA driver;

	/**
	 * Class constructor methods
	 */
	public CarJPA() {
		super();
	}

	public CarJPA(Integer carRegistrationId, String brand, String model, String color) {
		this.carRegistrationId = carRegistrationId;
		this.brand = brand;
		this.model = model;
		this.color = color;

	}

	/**
	 * Methods get/set the fields of database
	 */
	public Integer getCarRegistrationId() {
		return carRegistrationId;
	}

	public void setCarRegistrationId(Integer carRegistrationId) {
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

	public DriverJPA getDriver() {
		return driver;
	}

	public void setDriver(DriverJPA driver) {
		this.driver = driver;
	}
}
