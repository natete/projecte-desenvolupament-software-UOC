package jpa;
import java.io.Serializable;

import javax.persistence.*;

/**
 * JPA Class PetJPA
 */
@Entity
@Table(name="carsharing.car")
public class CarJPA implements Serializable {
	@OneToOne(cascade=CascadeType.PERSIST)

	private static final long serialVersionUID = 1L;

	private String nif;
	private String carRegistrationId;
	private String brand;
	private String model;
	private String color;
	
	/**
	 * Class constructor methods
	 */
	public CarJPA() {
		super();
	}
	public CarJPA(String nif, String carRegistrationId, String brand, String model, String color) {		
		this.nif = nif;
		this.carRegistrationId = carRegistrationId;
		this.brand = brand;
		this.model = model;
		this.color = color;
		
	}

	/**
	 *  Methods get/set the fields of database
	 */
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}	
	@Id
	public String getCarRegistrationId() {
		return carRegistrationId;
	}
	public  void setCarRegistrationId(String carRegistrationId) {
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
	public  void setModel(String model) {
		this.model = model;
	}
	public String getColor() {
		return color;
	}	
	public void setColor(String color) {
		this.color = color;
	}
		
	
}
