
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

import jpa.PassengerJPA;
import jpa.TripJPA;
import jpa.UserDTO;

/**
 * Managed Bean RateDriver
 */
@ManagedBean(name = "askquestion")
@SessionScoped
public class AskQuestionMBean implements Serializable{
	
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
	private FacesMessage message;
	private String redirectTo;
	
	
		
	/**
	 * Constructor method
	 * @throws Exception
	 */
	public AskQuestionMBean() throws Exception
	{
		super();
	}
	
	public void init() throws NamingException {
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		askQuestionRemote = (CommunicationFacadeRemote) ctx.lookup("java:app/CAT-PDP-GRUP6.jar/CommunicationFacadeBean!ejb.CommunicationFacadeRemote");
		trip = (TripJPA) askQuestionRemote.findTrip(this.getTripId());
					
		loggedUser = SessionBean.getLoggedUser();
		
		System.out.println("loggedUser: " + loggedUser);
		
		passenger = null;
		if(loggedUser != null && loggedUser.isPassenger()) {
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
	
	public String setDataTripComment() throws Exception
	{	
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		askQuestionRemote = (CommunicationFacadeRemote) ctx.lookup("java:app/CAT-PDP-GRUP6.jar/CommunicationFacadeBean!ejb.CommunicationFacadeRemote");
		if (this.subject.equals("")){
		    // Bring the error message using the Faces Context
			errorMessage = "Subject is missing";
			// Add View Faces Message
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
			            errorMessage, errorMessage);
			// Add the message into context for a specific component
			FacesContext.getCurrentInstance().addMessage("form:errorView", message);
		}
		if (this.body.equals("")){
		    // Bring the error message using the Faces Context
			errorMessage = "Body is missing";
			// Add View Faces Message
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
			            errorMessage, errorMessage);
			// Add the message into context for a specific component
			FacesContext.getCurrentInstance().addMessage("form:errorView", message);
		}
				
		if(errorMessage!=null){
			return "errorView";
		}
		else {	
			if (passenger != null) {
				askQuestionRemote.askQuestion(trip.getId(), passenger.getNif(), subject, body);
			}else{
				askQuestionRemote.askQuestion(trip.getId(), null, subject, body);
			}
			this.setSubject("");
			this.setBody("");
			return "/pages/public/tripCommentsView"; 
		}
	}
}
