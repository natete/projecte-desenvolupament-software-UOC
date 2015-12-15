
package managedbean;

import java.io.Serializable;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;

import ejb.UserFacadeRemote;

/**
 * Managed Bean RegisterDriverMBean
 */
@ManagedBean(name = "registerdriver")
@SessionScoped
public class RegisterDriverMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private UserFacadeRemote registerDriverRemote;

	private String nif;
	private String name;
	private String surname;
	private String phone;
	private String password;
	private String email;
	private String errorMessage;
	private FacesMessage message;

	/**
	 * Constructor method
	 */
	public RegisterDriverMBean() throws Exception {
		this.setNif(nif);
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

	public String setDataDriver() throws Exception {
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		registerDriverRemote = (UserFacadeRemote) ctx
				.lookup("java:app/CAT-PDP-GRUP6.jar/UserFacadeBean!ejb.UserFacadeRemote");
		if (this.nif.equals("")) {
			// Bring the error message using the Faces Context
			errorMessage = "NIF is missing";
			// Add View Faces Message
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, errorMessage);
			// Add the message into context for a specific component
			FacesContext.getCurrentInstance().addMessage("form:errorView", message);
		}
		if (this.name.equals("")) {
			// Bring the error message using the Faces Context
			errorMessage = "Name is missing";
			// Add View Faces Message
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, errorMessage);
			// Add the message into context for a specific component
			FacesContext.getCurrentInstance().addMessage("form:errorView", message);
		}
		if (this.surname.equals("")) {
			// Bring the error message using the Faces Context
			errorMessage = "Surname is missing";
			// Add View Faces Message
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, errorMessage);
			// Add the message into context for a specific component
			FacesContext.getCurrentInstance().addMessage("form:errorView", message);
		}
		if (this.email.equals("")) {
			// Bring the error message using the Faces Context
			errorMessage = "Email is missing";
			// Add View Faces Message
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, errorMessage);
			// Add the message into context for a specific component
			FacesContext.getCurrentInstance().addMessage("form:errorView", message);
		}
		if (this.password.equals("")) {
			// Bring the error message using the Faces Context
			errorMessage = "Password is missing";
			// Add View Faces Message
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, errorMessage);
			// Add the message into context for a specific component
			FacesContext.getCurrentInstance().addMessage("form:errorView", message);
		}
		if (registerDriverRemote.existsDriver(nif, email, null) == true) {
			// Bring the error message using the Faces Context
			errorMessage = "Driver already exists";
			// Add View Faces Message
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, errorMessage);
			// Add the message into context for a specific component
			FacesContext.getCurrentInstance().addMessage("form:errorView", message);
		}
		if (registerDriverRemote.existsPassengerEmail(nif, name, surname, email, null) == true) {
			// Bring the error message using the Faces Context
			errorMessage = "Passenger already exists with some email or some nif and different name-surname";
			// Add View Faces Message
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, errorMessage);
			// Add the message into context for a specific component
			FacesContext.getCurrentInstance().addMessage("form:errorView", message);
		}

		Pattern patN = Pattern.compile("([0-9]{8})([A-Za-z])");
		Matcher matN = patN.matcher(this.nif);
		if (!(this.phone.equals("")) && !(matN.matches())) {
			// Bring the error message using the Faces Context
			errorMessage = "NIF format not valid. Ej.: 12345678a or 12345678A";
			// Add View Faces Message
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, errorMessage);
			// Add the message into context for a specific component
			FacesContext.getCurrentInstance().addMessage("form:errorView", message);
		}

		Pattern patP = Pattern.compile("\\d{9}");
		Matcher matP = patP.matcher(this.phone);
		if (!(this.phone.equals("")) && !(matP.matches())) {
			// Bring the error message using the Faces Context
			errorMessage = "Phone format not valid. Ej.: 123456789";
			// Add View Faces Message
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, errorMessage);
			// Add the message into context for a specific component
			FacesContext.getCurrentInstance().addMessage("form:errorView", message);
		}

		Pattern patE = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		Matcher matE = patE.matcher(this.email);
		if (!(this.email.equals("")) && !(matE.matches())) {
			// Bring the error message using the Faces Context
			errorMessage = "Email format not valid. Ej.: example@domain.com";
			// Add View Faces Message
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, errorMessage);
			// Add the message into context for a specific component
			FacesContext.getCurrentInstance().addMessage("form:errorView", message);
		}

		if (errorMessage != null) {
			return "errorView";
		} else {
			registerDriverRemote.registerDriver(nif, name, surname, phone, password, email);
			this.setNif("");
			this.setName("");
			this.setSurname("");
			this.setPhone("");
			this.setPassword("");
			this.setEmail("");

			return "findTripsView";
		}
	}

}
