
package managedbean;

import java.io.Serializable;
import java.util.*;

import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;

import ejb.CommunicationFacadeRemote;

import jpa.DriverCommentJPA;

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
	
	private FacesMessage message;
	
		
	/**
	 * Constructor method
	 * @throws Exception
	 */
	public AskQuestionMBean() throws Exception
	{
		this.setTripId(1);
	    this.setPassengerId("22222222X");
	    this.setSubject("");
	    this.setBody(""); 
	}
	
		
	public int getTripId() {
		return tripId;
	}


	public void setTripId(int tripId) {
		this.tripId = tripId;
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


	public String setDataTripComment() throws Exception
	{	
		String errorMessage=null;
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
			askQuestionRemote.askQuestion(tripId, passengerId, subject, body);
			this.setSubject("");
			this.setBody("");
			return "tripCommentsView"; 
		}
	}
}
