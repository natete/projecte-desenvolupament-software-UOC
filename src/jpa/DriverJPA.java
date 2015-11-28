package jpa;
import java.io.Serializable;

import javax.persistence.*;

/**
 * JPA Class DriverJPA
 */
@Entity
@Table(name="driver")
public class DriverJPA implements Serializable {
	@OneToOne(cascade=CascadeType.PERSIST)

	private static final long serialVersionUID = 1L;

	private String nif;
	private String name;
	
		
	/**
	 * Class constructor methods
	 */
	public DriverJPA() {
		super();
	}
	public DriverJPA(String nif, String name) {		
		this.nif = nif;
		this.name = name;
			
	}
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
	

	
}
