
package managedbean;

import java.io.Serializable;
import java.util.Properties;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ejb.CommunicationFacadeRemote;
import jpa.PassengerJPA;
import jpa.TripJPA;
import jpa.UserDTO;

/**
 * Managed Bean AskQuestion
 */
@ManagedBean(name = "askquestion")
@ViewScoped
public class AskQuestionMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private CommunicationFacadeRemote askQuestionRemote;

	private int tripId;
	private String passengerId;
	private String subject;
	private String body;
	private TripJPA trip;
	private PassengerJPA passenger;
	private UserDTO loggedUser;
	private String redirectTo;

	/**
	 * Constructor method
	 * 
	 * @throws Exception
	 */
	public AskQuestionMBean() throws Exception {
		super();
	}

	/**
	 * Method to make the init operations
	 * 
	 * @throws NamingException
	 */
	public void init() throws NamingException {
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		askQuestionRemote = (CommunicationFacadeRemote) ctx
				.lookup("java:app/CAT-PDP-GRUP6.jar/CommunicationFacadeBean!ejb.CommunicationFacadeRemote");
		trip = (TripJPA) askQuestionRemote.findTrip(this.getTripId());

		loggedUser = SessionBean.getLoggedUser();

		System.out.println("loggedUser: " + loggedUser);

		passenger = null;
		if (loggedUser != null && loggedUser.isPassenger()) {
			passenger = (PassengerJPA) askQuestionRemote.findPassenger(loggedUser.getId());
		}
	}

	/**
	 * Method to get the TripId
	 * 
	 * @return tripId
	 */
	public int getTripId() {
		return this.tripId;
	}

	/**
	 * Method to set the TripId
	 */
	public void setTripId(int tripId) {
		this.tripId = tripId;
	}

	/**
	 * Method to get the Trip
	 * 
	 * @return trip
	 */
	public TripJPA getTrip() {
		return this.trip;
	}

	/**
	 * Method to set the Trip
	 */
	public void setTrip(TripJPA trip) {
		this.trip = trip;
	}

	/**
	 * Method to get the driver
	 * 
	 * @return driver
	 */
	public String getDriver() {
		return this.getTrip().getDriver().getName() + " " + this.getTrip().getDriver().getSurname();
	}

	/**
	 * Method to get the passenger id
	 * 
	 * @return passenserId
	 */
	public String getPassengerId() {
		return passengerId;
	}

	/**
	 * Method to set the PassengerId
	 */
	public void setPassengerId(String passengerId) {
		this.passengerId = passengerId;
	}

	/**
	 * Method to get the subject
	 *  
	 * @return subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Method to set the subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * Method to get the body
	 * 
	 * @return body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * Method to set the body
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * Method to get the page to redirect
	 * 
	 * @return redirectTo
	 */
	public String getRedirectTo() {
		return redirectTo;
	}

	/**
	 * Method to set the page to redirect
	 */
	public void setRedirectTo(String redirectTo) {
		this.redirectTo = redirectTo;
	}

	
	/**
	 * Method to set Data Trip Comment
	 * @return to tripCommentsView
	 * @throws Exception
	 */
	public String setDataTripComment() throws Exception {
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		askQuestionRemote = (CommunicationFacadeRemote) ctx
				.lookup("java:app/CAT-PDP-GRUP6.jar/CommunicationFacadeBean!ejb.CommunicationFacadeRemote");
		if (passenger != null) {
			askQuestionRemote.askQuestion(trip.getId(), passenger.getNif(), subject, body);
		} else {
			askQuestionRemote.askQuestion(trip.getId(), null, subject, body);
		}
		return "tripCommentsView";
	}
}
