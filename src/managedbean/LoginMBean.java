
package managedbean;

import java.io.Serializable;
import java.util.*;
import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;

import ejb.UserFacadeRemote;

/**
 * Managed Bean LoginMBean
 */
@ManagedBean(name = "login")
@SessionScoped

public class LoginMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private UserFacadeRemote loginRemote;

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
	public LoginMBean() throws Exception {
		//this.setEmail(email);

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

	//validate login
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
			
    	boolean valid = loginRemote.login(email, password);
        if (valid) {
            HttpSession session = SessionMBean.getSession();
            session.setAttribute("email", email);
            return "findTripsView.xhtml";
        } else {
        	// Bring the error message using the Faces Context
			errorMessage = "Incorrect E-mail and Passowrd.\nPlease enter correct E-mail and Password";
			// Add View Faces Message
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, errorMessage);
			// Add the message into context for a specific component
			FacesContext.getCurrentInstance().addMessage("form:errorView", message);
            return "errorView";
        }
    }
 
    //logout event, invalidate session
    public String logout() {
        HttpSession session = SessionMBean.getSession();
        session.invalidate();
        return "login";
    }
	
}
