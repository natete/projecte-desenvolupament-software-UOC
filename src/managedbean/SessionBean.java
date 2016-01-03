package managedbean;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jpa.UserDTO;

/**
 * Manages the logged user
 * 
 * @author Ignacio González Bullón - nachoglezbul@uoc.edu
 *
 */
public class SessionBean {

	public static final String USER_NAME = "username";
	public static final String USER_ID = "userId";
	public static final String DRIVER_ROLE = "driverRole";
	public static final String PASSENGER_ROLE = "passengerRole";
	public static final String EMAIL = "email";

	public static HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}

	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	/**
	 * Retrieves the logged user in the current instance of the Session
	 * 
	 * @return user the logged {@link UserDTO}
	 */
	public static UserDTO getLoggedUser() {
		HttpSession session = getSession();
		UserDTO user = null;
		if (session.getAttribute(USER_NAME) != null) {
			String username = (String) session.getAttribute(USER_NAME);
			String userId = (String) session.getAttribute(USER_ID);
			String email = (String) session.getAttribute(EMAIL);
			boolean isDriver = (boolean) session.getAttribute(DRIVER_ROLE);
			boolean isPassenger = (boolean) session.getAttribute(PASSENGER_ROLE);
			user = new UserDTO(username, userId, email, isDriver, isPassenger);
		}
		return user;
	}

	/**
	 * Sets the given user as logged in the current session
	 * 
	 * @param user
	 *            the {@link UserDTO} to be registered as logged
	 */
	public static void setLoggedUser(UserDTO user) {
		HttpSession session = getSession();
		session.setAttribute(USER_NAME, user.getUsername());
		session.setAttribute(USER_ID, user.getId());
		session.setAttribute(EMAIL, user.getEmail());
		session.setAttribute(DRIVER_ROLE, user.isDriver());
		session.setAttribute(PASSENGER_ROLE, user.isPassenger());
	}
}