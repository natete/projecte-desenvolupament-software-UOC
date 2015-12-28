package jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sun.istack.internal.NotNull;

@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({ @NamedQuery(name = "UserJPA.getByNif", query = "SELECT u FROM UserJPA u WHERE u.nif = :nif"),
		@NamedQuery(name = "UserJPA.getByEmail", query = "SELECT u FROM UserJPA u WHERE u.email = :email"),
		@NamedQuery(name = "UserJPA.existOtherUser", query = "SELECT u FROM UserJPA u WHERE u.nif <> :nif AND u.email = :email") })
public abstract class UserJPA implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@Column(name = "nif", unique = true)
	private String nif;

	@NotNull
	@Column(name = "name")
	private String name;

	@NotNull
	@Column(name = "surname")
	private String surname;

	@Column(name = "phone")
	private String phone;

	@NotNull
	@Column(name = "password")
	private String password;

	@NotNull
	@Column(name = "email", unique = true)
	private String email;

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

	@Transient
	public String getFullName() {
		return name + " " + surname;
	}
}
