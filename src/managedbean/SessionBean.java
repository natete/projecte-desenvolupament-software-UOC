package managedbean;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jpa.UserDTO;

public class SessionBean {

	public static final String USER_NAME = "username";
	public static final String USER_ID = "userId";
	public static final String DRIVER_ROLE = "driverRole";
	public static final String PASSENGER_ROLE = "passengerRole";

	public static HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}

	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	public static UserDTO getLoggedUser() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		UserDTO user = null;
		if (session.getAttribute(USER_NAME) != null) {
			String username = (String) session.getAttribute(USER_NAME);
			String userId = (String) session.getAttribute(USER_ID);
			boolean isDriver = (boolean) session.getAttribute(DRIVER_ROLE);
			boolean isPassenger = (boolean) session.getAttribute(PASSENGER_ROLE);
			user = new UserDTO(username, userId, isDriver, isPassenger);
		}
		return user;
	}

	public static void setLoggedUser(UserDTO user) {
		HttpSession session = getSession();
		session.setAttribute(USER_NAME, user.getUsername());
		session.setAttribute(USER_ID, user.getId());
		session.setAttribute(DRIVER_ROLE, user.isDriver());
		session.setAttribute(PASSENGER_ROLE, user.isPassenger());
	}
}