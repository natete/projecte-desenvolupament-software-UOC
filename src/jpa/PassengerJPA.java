package jpa;

import java.io.Serializable;

import javax.persistence.*;

/**
 * JPA Class PassengerJPA
 */
@Entity
@Table(name="passenger")
public class PassengerJPA implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nif;
	private String name;
	private String surname;
	private String phone;
	private String password;
	private String email;

	/**
	 * Class constructor methods
	 */
	public PassengerJPA() {
		super();
	}

	public PassengerJPA(String nif, String carRegistrationId, String brand, String model, String color) {
		this.nif = nif;
		this.name = name;
		this.surname = surname;
		this.phone = phone;
		this.password = password;
		this.email = email;

	}

	/**
	 * Methods get/set the fields of database
	 */
	
	@Id
	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
