
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
 * 
 * @author Joaquín Paredes Ribera - jparedesr@uoc.edu
 *
 */
@ManagedBean(name = "login")
@SessionScoped
public class LoginMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private UserFacadeRemote loginRemote;

	private String password;
	private String email;
	private UserDTO user;
	private String errorMessage;

	private FacesMessage message;

	/**
	 * Constructor method
	 */
	public LoginMBean() {

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

	public String getId() {
		return this.user.getId();
	}

	public void setId(String id) {
		this.user.setId(id);
	}

	public void setUser(UserDTO user) {
		this.user = user;

	}

	public String getRoles() {
		StringBuilder result = new StringBuilder();
		for (UserDTO.Role role : this.user.getRoles()) {
			result.append(role.getValue());
			if (this.user.getRoles().size() > 1 && (result.indexOf("/") == -1)) {
				result.append(" / ");
			}
		}
		return result.toString();
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * Method that validae login
	 * 
	 * @return String
	 *
	 */
	public String validateData() throws NamingException {
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
			SessionBean.setLoggedUser(user);

			return "findTripsView.xhtml";
		} else {
			errorMessage = "Incorrect E-mail and Passowrd. Please enter correct E-mail and Password";
			// Add View Faces Message
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, errorMessage);
			// Add the message into context for a specific component
			FacesContext.getCurrentInstance().addMessage("form:errorView", message);
			return "errorView";
		}
	}

	// logout event, invalidate session
	public String logout() {
		HttpSession session = SessionBean.getSession();
		session.invalidate();
		this.user = null;
		return "logout";
	}

	public boolean isLogged() {
		return this.user != null;
	}

	public boolean isDriver() {
		return this.getRoles().contains("Driver");
	}

	public boolean isPassenger() {
		return this.getRoles().contains("Passenger");
	}

}
