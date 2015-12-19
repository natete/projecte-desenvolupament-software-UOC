
package managedbean;

import java.io.Serializable;
import java.util.Properties;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.Context;
import javax.naming.InitialContext;

import ejb.UserFacadeRemote;

/**
 * Managed Bean RegisterPassengerMBean
 */
@ManagedBean(name = "registerpassenger")
@SessionScoped
public class RegisterPassengerMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private UserFacadeRemote registerPassengerRemote;

	private String nif;
	private String name;
	private String surname;
	private String phone;
	private String password;
	private String email;

	private String errorMessage;
	// private FacesMessage message;

	/**
	 * Constructor method
	 * 
	 * @throws Exception
	 */
	public RegisterPassengerMBean() {

	}

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

	public String getErrorMessage() {
		return errorMessage;
	}

	public String setDataPassenger() throws Exception {

		String result;

		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		registerPassengerRemote = (UserFacadeRemote) ctx
				.lookup("java:app/CAT-PDP-GRUP6.jar/UserFacadeBean!ejb.UserFacadeRemote");

		if (registerPassengerRemote.existsPassenger(nif, email) == true) {
			errorMessage = "Passenger already exists";
			result = "errorView";
		} else if (registerPassengerRemote.existsDriverEmail(nif, name, surname, email) == true) {
			errorMessage = "Driver already exists with some email or some nif and different name-surname";

			result = "errorView";
		} else {
			registerPassengerRemote.registerPassenger(nif, name, surname, phone, password, email);
			this.setNif("");
			this.setName("");
			this.setSurname("");
			this.setPhone("");
			this.setPassword("");
			this.setEmail("");

			result = "findTripsView";
		}

		return result;
	}
}
