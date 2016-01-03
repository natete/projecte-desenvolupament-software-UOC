package managedbean;

import java.io.Serializable;
import java.util.Properties;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ejb.UserFacadeRemote;
import jpa.UserDTO;
import jpa.UserJPA;

/**
 * Managed Bean UpdatePersonalDataMBean
 * 
 * @author Joaquín Paredes Ribera - jparedesr@uoc.edu
 *
 */
@ManagedBean(name = "updatepersonaldata")
@ViewScoped
public class UpdatePersonalDataMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private UserFacadeRemote userFacadeRemote;

	private UserDTO loggedUser;

	private UserJPA user;
	private String errorMessage;

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
	public UpdatePersonalDataMBean() {
		loggedUser = SessionBean.getLoggedUser();
		try {
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			userFacadeRemote = (UserFacadeRemote) ctx
					.lookup("java:app/CAT-PDP-GRUP6.jar/UserFacadeBean!ejb.UserFacadeRemote");

			user = userFacadeRemote.findUser(loggedUser.getId());

		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getNif() {
		return user.getNif();
	}

	public void setNif(String nif) {
		user.setNif(nif);
	}

	public String getName() {
		return user.getName();
	}

	public void setName(String name) {
		user.setName(name);
	}

	public String getSurname() {
		return user.getSurname();
	}

	public void setSurname(String surname) {
		user.setSurname(surname);
	}

	public String getPhone() {
		return user.getPhone();
	}

	public void setPhone(String phone) {
		user.setPhone(phone);
	}

	public String getPassword() {
		return user.getPassword();
	}

	public void setPassword(String password) {
		user.setPassword(password);
	}

	public String getEmail() {
		return user.getEmail();
	}

	public void setEmail(String email) {
		user.setEmail(email);
	}

	public String getErrorMessage() {
		return this.errorMessage;
	}

	/**
	 * Method that set data user
	 * 
	 * @return String
	 *
	 */
	public String setDataUser() throws Exception {

		String result = "findTripsView.xhtml";

		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		userFacadeRemote = (UserFacadeRemote) ctx
				.lookup("java:app/CAT-PDP-GRUP6.jar/UserFacadeBean!ejb.UserFacadeRemote");
		userFacadeRemote = (UserFacadeRemote) ctx
				.lookup("java:app/CAT-PDP-GRUP6.jar/UserFacadeBean!ejb.UserFacadeRemote");

		if (loggedUser.getId().equals(user.getNif()) && loggedUser.getEmail().equals(user.getEmail())) {
			updatePersonalData();

		} else {
			if (userFacadeRemote.existUser(user)) {
				errorMessage = "This email is being used by another user";
				result = "errorUpdating";
			} else {
				updatePersonalData();
			}
		}

		return result;
	}

	/**
	 * Method that update personal data
	 * 
	 */
	private void updatePersonalData() {
		userFacadeRemote.updatePersonalData(user.getNif(), user.getName(), user.getSurname(), user.getPhone(),
				user.getEmail(), user.getPassword());
		loggedUser.setId(getNif());
		loggedUser.setUsername(getName() + " " + getSurname());
		SessionBean.setLoggedUser(loggedUser);
		loginMBean.setUser(loggedUser);
	}
}