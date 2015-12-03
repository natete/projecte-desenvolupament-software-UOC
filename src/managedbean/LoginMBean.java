
package managedbean;

import java.io.Serializable;
import java.util.Properties;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

import ejb.UserFacadeRemote;
import jpa.UserDTO;

/**
 * Managed Bean LoginMBean
 */
@ManagedBean(name = "login")
@SessionScoped
public class LoginMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String USER_NAME = "username";

	private static final String USER_ID = "userId";

	private static final String DRIVER_ROLE = "driverRole";

	private static final String PASSENGER_ROLE = "passengerRole";

	@EJB
	private UserFacadeRemote loginRemote;

	private String password;
	private String email;
	private UserDTO user;

	private FacesMessage message;

	/**
	 * Constructor method
	 */
	public LoginMBean() {
		// this.setEmail(email);
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

	public String getUsername() {
		return this.user.getUsername();
	}

	public String getRoles() {
		StringBuilder result = new StringBuilder();
		for (UserDTO.Role role : this.user.getRoles()) {
			result.append(role.getValue());
		}
		return result.toString();
	}

	// validate login
	public String validateData() throws NamingException {
		String errorMessage = null;
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		loginRemote = (UserFacadeRemote) ctx.lookup("java:app/CAT-PDP-GRUP6.jar/UserFacadeBean!ejb.UserFacadeRemote");
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

		this.user = loginRemote.login(this.email, this.password);

		if (this.user != null) {
			HttpSession session = SessionMBean.getSession();
			session.setAttribute(USER_NAME, this.user.getUsername());
			session.setAttribute(USER_ID, this.user.getId());
			session.setAttribute(DRIVER_ROLE, this.user.isDriver());
			session.setAttribute(PASSENGER_ROLE, this.user.isPassenger());

			return "findTripsView.xhtml";
		} else {
			errorMessage = "Incorrect E-mail and Passowrd.\nPlease enter correct E-mail and Password";
			// Add View Faces Message
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, errorMessage);
			// Add the message into context for a specific component
			FacesContext.getCurrentInstance().addMessage("form:errorView", message);
			return "errorView";
		}
		//
		// boolean valid = loginRemote.login(email, password);
		// if (valid) {
		// HttpSession session = SessionMBean.getSession();
		// session.setAttribute("email", email);
		// return "findTripsView.xhtml";
		// } else {
		// // Bring the error message using the Faces Context
		// errorMessage = "Incorrect E-mail and Passowrd.\nPlease enter correct
		// E-mail and Password";
		// // Add View Faces Message
		// message = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage,
		// errorMessage);
		// // Add the message into context for a specific component
		// FacesContext.getCurrentInstance().addMessage("form:errorView",
		// message);
		// return "errorView";
		// }
	}

	// logout event, invalidate session
	public String logout() {
		HttpSession session = SessionMBean.getSession();
		session.invalidate();
		this.user = null;
		return "login";
	}

	public boolean isLogged() {
		return this.user != null;
	}

}
