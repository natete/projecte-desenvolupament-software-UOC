
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

import jpa.MessageJPA;
import jpa.DriverJPA;
import jpa.TripJPA;
import jpa.UserDTO;

/**
 * Managed Bean ReplyQuestion
 */
@ManagedBean(name = "replyquestion")
@SessionScoped
public class ReplyQuestionMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
		
	@EJB
	private CommunicationFacadeRemote replyQuestionRemote;
			
	private int tripId;
	private int questionId;
	private String passengerId;
	private String subject;
	private String body;
	private TripJPA trip;
	private MessageJPA question;
	private DriverJPA driver;
	private UserDTO loggedUser;
	private String errorMessage;
	private FacesMessage message;
	
	
		
	/**
	 * Constructor method
	 * @throws Exception
	 */
	public ReplyQuestionMBean() throws Exception
	{
		super();
	}
	
	public void init() throws NamingException {
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		replyQuestionRemote = (CommunicationFacadeRemote) ctx.lookup("java:app/CAT-PDP-GRUP6.jar/CommunicationFacadeBean!ejb.CommunicationFacadeRemote");
		trip = (TripJPA) replyQuestionRemote.findTrip(tripId);
					
		loggedUser = SessionBean.getLoggedUser();
		
		System.out.println("loggedUser: " + loggedUser);
		
		driver = null;
		if(loggedUser != null && loggedUser.isDriver()) {
			driver = (DriverJPA) replyQuestionRemote.findDriver(loggedUser.getId());
		}
		
		question = (MessageJPA) replyQuestionRemote.findMessage(questionId);
	}
	
	public int getTripId() {
		return this.tripId;
	}

	public void setTripId(int tripId) {
		this.tripId = tripId;
	}
	
	public int getQuestionId() {
		return this.questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
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

	public String getErrorMessage() {
		return errorMessage;
	}
	
	public String setDataTripComment() throws Exception
	{	
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		replyQuestionRemote = (CommunicationFacadeRemote) ctx.lookup("java:app/CAT-PDP-GRUP6.jar/CommunicationFacadeBean!ejb.CommunicationFacadeRemote");
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
			replyQuestionRemote.replyQuestion(this.trip.getId(), question.getQuestionId(), driver.getNif(), subject, body);
			this.setSubject("");
			this.setBody("");
			return "tripCommentsView"; 
		}
	}
}
