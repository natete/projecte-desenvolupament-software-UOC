
package managedbean;

import java.io.Serializable;
import java.util.Properties;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ejb.UserFacadeRemote;
import jpa.UserJPA;

/**
 * Managed Bean RegisterDriverMBean
 * 
 * @author Joaquín Paredes Ribera - jparedesr@uoc.edu
 *
 */
@ManagedBean(name = "registerdriver")
@ViewScoped
public class RegisterDriverMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String ERROR_VIEW = "errorView";
	private static final String HOME_VIEW = "findTripsView";

	@EJB
	private UserFacadeRemote userFacadeRemote;

	private String nif;
	private String name;
	private String surname;
	private String phone;
	private String password;
	private String email;
	private String errorMessage;

	/**
	 * Constructor method
	 */
	public RegisterDriverMBean() throws Exception {

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

	/**
	 * Method that set data of the driver
	 * 
	 * @return String
	 *
	 */
	public String setDataDriver() {

		String result;

		Properties props = System.getProperties();
		try {
			Context ctx = new InitialContext(props);
			userFacadeRemote = (UserFacadeRemote) ctx
					.lookup("java:app/CAT-PDP-GRUP6.jar/UserFacadeBean!ejb.UserFacadeRemote");

			UserJPA user = userFacadeRemote.findUser(nif);

			if (user == null) {
				if (userFacadeRemote.isEmailUsed(email)) {
					errorMessage = "This email is being used by another user";
					result = ERROR_VIEW;
				} else {
					userFacadeRemote.registerDriver(nif, name, surname, phone, password, email);
					result = HOME_VIEW;
				}
			} else {
				if (userFacadeRemote.existDriver(nif)) {
					errorMessage = "Driver already exists";
					result = ERROR_VIEW;
				} else if (userIsRegistered(user)) {
					userFacadeRemote.registerDriver(nif, name, surname, phone, password, email);
					result = HOME_VIEW;
				} else {
					errorMessage = "A passenger with the given NIF exists and there is a conflict with the provided user info";
					result = ERROR_VIEW;
				}
			}

			return result;
		} catch (NamingException e) {
			return ERROR_VIEW;
		}

	}

	/**
	 * Check if exist the user that match the given conditions
	 * 
	 * @param user
	 *            user to check
	 * 
	 * @return boolean
	 *
	 */
	public boolean userIsRegistered(UserJPA user) {
		boolean result = user.getNif().equals(nif) && user.getEmail().equals(email) && user.getName().equals(name)
				&& user.getSurname().equals(surname) && user.getPassword().equals(password);

		if (user.getPhone() != null) {
			result = result && user.getPhone().equals(phone);
		} else {
			result = result && (phone == null || phone.equals(""));
		}

		return result;
	}
}
