
package managedbean;

import java.io.Serializable;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpSession;

import ejb.UserFacadeRemote;

import jpa.UserDTO;
import jpa.UserDTO.Role;

/**
 * Managed Bean UpdatePersonalDataMBean
 */
@ManagedBean(name = "updatepersonaldata")
@ViewScoped
public class UpdatePersonalDataMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private UserFacadeRemote updatePersonalDataRemote;
	
	private UserDTO loggedUser = SessionBean.getLoggedUser(); 
	
	
	private String nif = loggedUser.getId();
	private String name;
	private String surname;
	private String phone;
	private String password;
	private String email;

	private String errorMessage;
	private FacesMessage message;
	private UserFacadeRemote loginRemote;
	private UserDTO user;
	
	@ManagedProperty(value = "#{login}")
	private LoginMBean loginMBean;
	
	/**
	 * @param loginMBean the loginMBean to set
	 */
	public void setLoginMBean(LoginMBean loginMBean) {
		this.loginMBean = loginMBean;
	}

	/**
	 * Constructor method
	 */
	public UpdatePersonalDataMBean() throws Exception
	{
		this.setNif(nif);
	}

	public String getNif() {
		return loggedUser.getId();
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getName() {
		if (loggedUser.getRoles().contains(Role.DRIVER)) {
			return updatePersonalDataRemote.findDriver(nif).getName();
		} else {
			return updatePersonalDataRemote.findPassenger(nif).getName();
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		if (loggedUser.getRoles().contains(Role.DRIVER)) {
			return updatePersonalDataRemote.findDriver(nif).getSurname();
		} else {
			return updatePersonalDataRemote.findPassenger(nif).getSurname();
		}
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPhone() {
		if (loggedUser.getRoles().contains(Role.DRIVER)) {
			return updatePersonalDataRemote.findDriver(nif).getPhone();
		} else {
			return updatePersonalDataRemote.findPassenger(nif).getPhone();
		}
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		if (loggedUser.getRoles().contains(Role.DRIVER)) {
			return updatePersonalDataRemote.findDriver(nif).getPassword();
		} else {
			return updatePersonalDataRemote.findPassenger(nif).getPassword();
		}
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		if (loggedUser.getRoles().contains(Role.DRIVER)) {
			return updatePersonalDataRemote.findDriver(nif).getEmail();
		} else {
			return updatePersonalDataRemote.findPassenger(nif).getEmail();
		}
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String setDataUser() throws Exception {
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		updatePersonalDataRemote = (UserFacadeRemote) ctx.lookup("java:app/CAT-PDP-GRUP6.jar/UserFacadeBean!ejb.UserFacadeRemote");
		loginRemote = (UserFacadeRemote) ctx.lookup("java:app/CAT-PDP-GRUP6.jar/UserFacadeBean!ejb.UserFacadeRemote");

		if (nif.equals("")) {
			// Bring the error message using the Faces Context
			errorMessage = "Nif is missing";
			// Add View Faces Message
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, errorMessage);
			// Add the message into context for a specific component
			FacesContext.getCurrentInstance().addMessage("form:errorView", message);
		}
		if (name.equals("")) {
			// Bring the error message using the Faces Context
			errorMessage = "Name is missing";
			// Add View Faces Message
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, errorMessage);
			// Add the message into context for a specific component
			FacesContext.getCurrentInstance().addMessage("form:errorView", message);
		}
		if (surname.equals("")) {
			// Bring the error message using the Faces Context
			errorMessage = "Surname is missing";
			// Add View Faces Message
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, errorMessage);
			// Add the message into context for a specific component
			FacesContext.getCurrentInstance().addMessage("form:errorView", message);
		}
		if (email.equals("")) {
			// Bring the error message using the Faces Context
			errorMessage = "Email is missing";
			// Add View Faces Message
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, errorMessage);
			// Add the message into context for a specific component
			FacesContext.getCurrentInstance().addMessage("form:errorView", message);
		}
		if (password.equals("")) {
			// Bring the error message using the Faces Context
			errorMessage = "Password is missing";
			// Add View Faces Message
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, errorMessage);
			// Add the message into context for a specific component
			FacesContext.getCurrentInstance().addMessage("form:errorView", message);
		}
		if (loggedUser.getRoles().contains(Role.DRIVER)) {
			if (updatePersonalDataRemote.existsDriver(nif, email, loggedUser) == true) {
				// Bring the error message using the Faces Context
				errorMessage = "Driver already exists";
				// Add View Faces Message
				message = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, errorMessage);
				// Add the message into context for a specific component
				FacesContext.getCurrentInstance().addMessage("form:errorView", message);
			}
			if (updatePersonalDataRemote.existsPassengerEmail(nif, name, surname, email, loggedUser) == true) {
				// Bring the error message using the Faces Context
				errorMessage = "Passenger already exists with some email or some nif and different name-surname";
				// Add View Faces Message
				message = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, errorMessage);
				// Add the message into context for a specific component
				FacesContext.getCurrentInstance().addMessage("form:errorView", message);
			}
		} 
		if (loggedUser.getRoles().contains(Role.PASSENGER)) {
			if (updatePersonalDataRemote.existsPassenger(nif, email, loggedUser) == true) {
				// Bring the error message using the Faces Context
				errorMessage = "Passenger already exists";
				// Add View Faces Message
				message = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, errorMessage);
				// Add the message into context for a specific component
				FacesContext.getCurrentInstance().addMessage("form:errorView", message);
			}
			if (updatePersonalDataRemote.existsDriverEmail(nif, name, surname, email, loggedUser) == true) {
				// Bring the error message using the Faces Context
				errorMessage = "Driver already exists with some email or some nif and different name-surname";
				// Add View Faces Message
				message = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, errorMessage);
				// Add the message into context for a specific component
				FacesContext.getCurrentInstance().addMessage("form:errorView", message);
			}
		} 
		
		Pattern patP = Pattern.compile("\\d{9}");
		Matcher matP = patP.matcher(phone);
		if (!(phone.equals("")) && !(matP.matches())) {
			// Bring the error message using the Faces Context
			errorMessage = "Phone format not valid. Ej.: 123456789";
			// Add View Faces Message
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, errorMessage);
			// Add the message into context for a specific component
			FacesContext.getCurrentInstance().addMessage("form:errorView", message);
		}

		Pattern patE = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		Matcher matE = patE.matcher(email);
		if (!(email.equals("")) && !(matE.matches())) {
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
			if (loggedUser.getRoles().contains(Role.DRIVER)) {
				updatePersonalDataRemote.updateDriver(nif, name, surname, phone, email, password);
			}
			if (loggedUser.getRoles().contains(Role.PASSENGER)) {
				updatePersonalDataRemote.updatePassenger(nif, name, surname, phone, email, password);
			}
			
//			HttpSession session = SessionBean.getSession();
//			session.invalidate();
//			this.user = loginRemote.login(this.email, this.password);
//			SessionBean.setLoggedUser(user);
	
			loggedUser.setId(this.nif);
			loggedUser.setUsername(this.name + " " + this.surname);
			SessionBean.setLoggedUser(loggedUser);
			loginMBean.setUser(loggedUser);
			
			this.setNif("");
			this.setName("");
			this.setSurname("");
			this.setPhone("");
			this.setPassword("");
			this.setEmail("");
			
			return "findTripsView.xhtml";
		}
	}
}
