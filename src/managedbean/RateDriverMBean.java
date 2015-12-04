
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
@ManagedBean(name = "ratedriver")
@SessionScoped
public class RateDriverMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
		
	@EJB
	private CommunicationFacadeRemote rateDriverRemote;
	
	private DriverCommentJPA driverComment;
	
	private String driverId;
	private String passengerId;
	private String comment;
	private int rating;
	
	private FacesMessage message;
	
		
	/**
	 * Constructor method
	 * @throws Exception
	 */
	public RateDriverMBean() throws Exception
	{
		this.setPassengerId("22222222X");
	    this.setDriverId("00000000X");
	    driverComment = getDataDriverComment(driverId,passengerId);
	    if (driverComment==null) {
	    	setComment("");
	    	setRating(0); 
	    }else{
	    	this.comment = driverComment.getComment();
	    	this.rating = driverComment.getRating();
	    }		
	}
	
		
	public String getDriverId() {
		return driverId;
	}


	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}



	public String getPassengerId() {
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



	public String setDataDriverComment() throws Exception
	{	
		String errorMessage=null;
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		rateDriverRemote = (CommunicationFacadeRemote) ctx.lookup("java:app/CAT-PDP-GRUP6.jar/CommunicationFacadeBean!ejb.CommunicationFacadeRemote");
		if (this.comment.equals("")){
		    // Bring the error message using the Faces Context
			errorMessage = "Comment is missing";
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
			if (driverComment == null) {
				rateDriverRemote.rateDriver(driverId, passengerId, comment, rating);
			} else {
				rateDriverRemote.updateRateDriver(driverId, passengerId, comment, rating);
			}
			
						
			return "driverCommentsView"; 
		}
	}
	
	public DriverCommentJPA getDataDriverComment(String driverId, String passengerId) throws Exception
	{	
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		rateDriverRemote = (CommunicationFacadeRemote) ctx.lookup("java:app/CAT-PDP-GRUP6.jar/CommunicationFacadeBean!ejb.CommunicationFacadeRemote");
		return rateDriverRemote.getDriverComment(driverId, passengerId);
						
	}
		
}
