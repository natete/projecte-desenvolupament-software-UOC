
package managedbean;

import java.io.Serializable;
import java.util.*;

import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;

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

	private FacesMessage message;

	/**
	 * Constructor method
	 * 
	 * @throws Exception
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

	public String setDataDriver() throws Exception {
		String errorMessage = null;
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		registerDriverRemote = (UserFacadeRemote) ctx.lookup("java:app/CAT-PDP-GRUP6.jar/UserFacadeBean!ejb.UserFacadeRemote");
		if (this.nif.equals("")) {
			// Bring the error message using the Faces Context
			errorMessage = "NIF is missing";
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
		if (registerDriverRemote.existsUser(nif, email) == true) {
			// Bring the error message using the Faces Context
			errorMessage = "User already exists";
			// Add View Faces Message
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, errorMessage);
			// Add the message into context for a specific component
			FacesContext.getCurrentInstance().addMessage("form:errorView", message);
		}
		if (!(this.email.equals("")) && (!(this.email.contains("@")))) {
			// Bring the error message using the Faces Context
			errorMessage = "Email must contains \"@\"";
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

			return "index";
		}
	}

}
