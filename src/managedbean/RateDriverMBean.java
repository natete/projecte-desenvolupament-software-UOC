
package managedbean;

import java.io.Serializable;
import java.util.*;

import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;

import ejb.CommunicationFacadeRemote;

import jpa.DriverCommentJPA;
import jpa.DriverJPA;
import jpa.PassengerJPA;
import jpa.UserDTO;

/**
 * Managed Bean RateDriver
 */
@ManagedBean(name = "ratedriver")
@SessionScoped
public class RateDriverMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private CommunicationFacadeRemote rateDriverRemote;

	private DriverCommentJPA driverComment;

	private String driverId;
	private String passengerId;
	private String comment;
	private int rating;
	private DriverJPA driver;
	private PassengerJPA passenger;
	private UserDTO loggedUser;
	private String errorMessage;
	private FacesMessage message;
	private String redirectTo;

	/**
	 * Constructor method
	 * @throws Exception
	 */
	public RateDriverMBean() throws Exception {
		super();
	}

	public void init() throws NamingException {
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		rateDriverRemote = (CommunicationFacadeRemote) ctx
				.lookup("java:app/CAT-PDP-GRUP6.jar/CommunicationFacadeBean!ejb.CommunicationFacadeRemote");
		driver = (DriverJPA) rateDriverRemote.findDriver(this.getDriverId());

		loggedUser = SessionBean.getLoggedUser();

		passenger = null;
		if (loggedUser.isPassenger()) {
			passenger = (PassengerJPA) rateDriverRemote.findPassenger(loggedUser.getId());
		}

		driverComment = (DriverCommentJPA) rateDriverRemote.getDriverComment(this.getDriverId(), passenger.getNif());
		if (driverComment != null) {
			this.setComment(driverComment.getComment());
			this.setRating(driverComment.getRating());
		}
	}

	public String getDriverId() {
		return driverId;
	}

	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}

	public String getPassengerId() {
		passengerId = loggedUser.getId();
		return passengerId;
	}

	public void setPassengerId(String passengerId) {
		this.passengerId = passengerId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getDriver() {
		return driver.getName() + " " + driver.getSurname();
	}

	public String getPassenger() {
		return passenger.getName() + " " + passenger.getSurname();
	}

	public String getRedirectTo() {
		System.out.println("redirectTo" + redirectTo);
		return redirectTo;
	}

	public void setRedirectTo(String redirectTo) {
		System.out.println("redirectTo" + redirectTo);
		this.redirectTo = redirectTo;
	}

	public boolean isPassengerLogged() {
		return loggedUser != null && loggedUser.isPassenger();
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String setDataDriverComment() throws Exception {
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		rateDriverRemote = (CommunicationFacadeRemote) ctx
				.lookup("java:app/CAT-PDP-GRUP6.jar/CommunicationFacadeBean!ejb.CommunicationFacadeRemote");
		if (this.comment.equals("")) {
			// Bring the error message using the Faces Context
			errorMessage = "Comment is missing";
			// Add View Faces Message
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, errorMessage);
			// Add the message into context for a specific component
			FacesContext.getCurrentInstance().addMessage("form:errorView", message);
		}

		if (errorMessage != null) {
			return "errorView";
		} else {
			if (driverComment == null) {
				rateDriverRemote.rateDriver(this.getDriverId(), passenger.getNif(), comment, rating);
			} else {
				rateDriverRemote.updateRateDriver(this.getDriverId(), passenger.getNif(), comment, rating);
			}

			return "/pages/public/driverCommentsView";
		}
	}

	public DriverCommentJPA getDataDriverComment(String driverId, String passengerId) throws Exception {
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		rateDriverRemote = (CommunicationFacadeRemote) ctx
				.lookup("java:app/CAT-PDP-GRUP6.jar/CommunicationFacadeBean!ejb.CommunicationFacadeRemote");
		return rateDriverRemote.getDriverComment(driverId, passengerId);

	}

}
