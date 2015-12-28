
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
 * Managed Bean RateDriver
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
	private String errorMessage;
	private String redirectTo;

	/**
	 * Constructor method
	 * 
	 * @throws Exception
	 */
	public AskQuestionMBean() throws Exception {
		super();
	}

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

	public int getTripId() {
		return this.tripId;
	}

	public void setTripId(int tripId) {
		this.tripId = tripId;
	}

	public TripJPA getTrip() {
		return this.trip;
	}

	public void setTrip(TripJPA trip) {
		this.trip = trip;
	}

	public String getDriver() {
		return this.getTrip().getDriver().getName() + " " + this.getTrip().getDriver().getSurname();
	}

	public String getPassengerId() {
		return passengerId;
	}

	public void setPassengerId(String passengerId) {
		this.passengerId = passengerId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getRedirectTo() {
		return redirectTo;
	}

	public void setRedirectTo(String redirectTo) {
		this.redirectTo = redirectTo;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

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
		// }
	}
}
