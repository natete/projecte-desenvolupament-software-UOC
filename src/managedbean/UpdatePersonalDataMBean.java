
package managedbean;

import java.io.Serializable;
import java.util.Properties;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.naming.Context;
import javax.naming.InitialContext;

import ejb.UserFacadeRemote;
import jpa.UserDTO;

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
	 * @param loginMBean
	 *            the loginMBean to set
	 */
	public void setLoginMBean(LoginMBean loginMBean) {
		this.loginMBean = loginMBean;
	}

	/**
	 * Constructor method
	 */
	public UpdatePersonalDataMBean() throws Exception {

	}

	public String getNif() {
		return loggedUser.getId();
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getName() {
		if (loggedUser.isDriver()) {
			return updatePersonalDataRemote.findDriver(nif).getName();
		} else {
			return updatePersonalDataRemote.findPassenger(nif).getName();
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		if (loggedUser.isDriver()) {
			return updatePersonalDataRemote.findDriver(nif).getSurname();
		} else {
			return updatePersonalDataRemote.findPassenger(nif).getSurname();
		}
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPhone() {
		if (loggedUser.isDriver()) {
			return updatePersonalDataRemote.findDriver(nif).getPhone();
		} else {
			return updatePersonalDataRemote.findPassenger(nif).getPhone();
		}
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		if (loggedUser.isDriver()) {
			return updatePersonalDataRemote.findDriver(nif).getPassword();
		} else {
			return updatePersonalDataRemote.findPassenger(nif).getPassword();
		}
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		if (loggedUser.isDriver()) {
			return updatePersonalDataRemote.findDriver(nif).getEmail();
		} else {
			return updatePersonalDataRemote.findPassenger(nif).getEmail();
		}
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String setDataUser() throws Exception {

		String result = null;

		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		updatePersonalDataRemote = (UserFacadeRemote) ctx
				.lookup("java:app/CAT-PDP-GRUP6.jar/UserFacadeBean!ejb.UserFacadeRemote");
		loginRemote = (UserFacadeRemote) ctx.lookup("java:app/CAT-PDP-GRUP6.jar/UserFacadeBean!ejb.UserFacadeRemote");

		if (loggedUser.isDriver() && !(loggedUser.isPassenger())) {
			if (updatePersonalDataRemote.existsDriver(nif, email, loggedUser) == true) {
				errorMessage = "Driver already exists";
				result = "errorView";
			} else if (updatePersonalDataRemote.existsPassengerEmail(nif, name, surname, email, null) == true) {
				errorMessage = "Passenger already exists with some email or some nif and different name-surname";
				result = "errorView";
			} else {
				updatePersonalDataRemote.updateDriver(nif, name, surname, phone, email, password);
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

				result = "findTripsView.xhtml";

			}
		}
		if (loggedUser.isPassenger() && !(loggedUser.isDriver())) {
			if (updatePersonalDataRemote.existsPassenger(nif, email, loggedUser) == true) {
				errorMessage = "Passenger already exists";
				result = "errorView";
			} else if (updatePersonalDataRemote.existsDriverEmail(nif, name, surname, email, null) == true) {
				errorMessage = "Driver already exists with some email or some nif and different name-surname";
				result = "errorView";
			} else {
				updatePersonalDataRemote.updatePassenger(nif, name, surname, phone, email, password);
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

				result = "findTripsView.xhtml";

			}
		}

		if (loggedUser.isDriver() && loggedUser.isPassenger()) {
			if (updatePersonalDataRemote.existsDriver(nif, email, loggedUser) == true) {
				errorMessage = "Driver already exists";
				result = "errorView";
			} else if (updatePersonalDataRemote.existsPassengerEmail(nif, name, surname, email, loggedUser) == true) {
				errorMessage = "Passenger already exists with some email or some nif and different name-surname";
				result = "errorView";
			} else if (updatePersonalDataRemote.existsPassenger(nif, email, loggedUser) == true) {
				errorMessage = "Passenger already exists";
				result = "errorView";
			} else if (updatePersonalDataRemote.existsDriverEmail(nif, name, surname, email, loggedUser) == true) {
				errorMessage = "Driver already exists with some email or some nif and different name-surname";
				result = "errorView";
			} else {
				updatePersonalDataRemote.updateDriver(nif, name, surname, phone, email, password);
				updatePersonalDataRemote.updatePassenger(nif, name, surname, phone, email, password);
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

				result = "findTripsView.xhtml";

			}
		}
		return result;

	}
}
