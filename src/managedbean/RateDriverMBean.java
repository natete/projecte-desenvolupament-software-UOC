package managedbean;

import java.io.Serializable;
import java.util.*;

import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ejb.CommunicationFacadeRemote;

import jpa.DriverCommentJPA;
import jpa.DriverJPA;
import jpa.PassengerJPA;
import jpa.TripJPA;
import jpa.UserDTO;

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
	
	private int tripId;
	private String passengerId;
	private String comment;
	private int rating;
	private TripJPA trip; 
	private DriverJPA driver;
	private PassengerJPA passenger;
	private UserDTO loggedUser;
	private String redirectTo;
	
		
	/**
	 * Constructor method
	 * @throws Exception
	 */
	public RateDriverMBean() throws Exception
	{
		super();
	}
	
	/**
	 * Method to make the init operations
	 * @throws NamingException
	 */
	public void init() throws NamingException {
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		rateDriverRemote = (CommunicationFacadeRemote) ctx.lookup("java:app/CAT-PDP-GRUP6.jar/CommunicationFacadeBean!ejb.CommunicationFacadeRemote");
		trip = (TripJPA) rateDriverRemote.findTrip(this.getTripId());
		driver = trip.getDriver();
						
		loggedUser = SessionBean.getLoggedUser();
		
		passenger = null;
		if(loggedUser.isPassenger()) {
			passenger = (PassengerJPA) rateDriverRemote.findPassenger(loggedUser.getId());
		}
		
		driverComment = (DriverCommentJPA) rateDriverRemote.getDriverComment(driver.getNif(),passenger.getNif());
		if(driverComment != null) {
			this.setComment(driverComment.getComment());
			this.setRating(driverComment.getRating());
		}else{
			this.setComment("");
			this.setRating(0);
		}
	}
	
	/**
     * Method to get the TripId
     * @return tripId
     */
	public int getTripId() {
		return tripId;
	}

	/**
     * Method to set the TripId
     */
	public void setTripId(int tripId) {
		this.tripId = tripId;
	}

	/**
     * Method to get the PassengerId
     * @return passengerId
     */	
	public String getPassengerId() {
		passengerId = loggedUser.getId();
		return passengerId;
	}

	/**
     * Method to set the PassengerId
     */
	public void setPassengerId(String passengerId) {
		this.passengerId = passengerId;
	}

	/**
	 * Method to get the comment about the driver
	 * @return comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Method to set the comment about the driver
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * Method to get the rating about the driver
	 * @return comment
	 */
	public int getRating() {
		return rating;
	}

	/**
	 * Method to set the rating about the driver
	 */
	public void setRating(int rating) {
		this.rating = rating;
	}
	
	/**
     * Method to get Driver name + Driver surname
     * @return driver name + driver surname
     */
	public String getDriver() {
		return driver.getName() + " " + driver.getSurname();
	}
	
	/**
     * Method to get Passenger name + Passenger surname
     * @return passenger name + passenger surname
     */
	public String getPassenger()  {
		return passenger.getName() + " " + passenger.getSurname();
	}
	
	/**
     * Method to get the page to redirect
     * @return redirectTo
     */
	public String getRedirectTo() {
		return redirectTo;
	}

	/**
     * Method to set the page to redirect
     */
	public void setRedirectTo(String redirectTo) {
		System.out.println("redirectTo" + redirectTo);
		this.redirectTo = redirectTo;
	}

	/**
	 * Method than returns if Passenger is logged
	 * @return boolean
	 */
	public boolean isPassengerLogged() {
		return loggedUser != null && loggedUser.isPassenger();
	}

	/**
	 * Method to set Data Driver Comment
	 * @return to driverCommentView
	 * @throws Exception
	 */
	
	public String setDataDriverComment() throws Exception
	{	
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		rateDriverRemote = (CommunicationFacadeRemote) ctx.lookup("java:app/CAT-PDP-GRUP6.jar/CommunicationFacadeBean!ejb.CommunicationFacadeRemote");
						
		if (driverComment == null) {
			rateDriverRemote.rateDriver(driver.getNif(),passenger.getNif(), comment, rating);
		} else {
			rateDriverRemote.updateRateDriver(driver.getNif(),passenger.getNif(), comment, rating);
		}
		return "/pages/public/driverCommentsView"; 
	}
	
	/**
	 * Method to get a passenger comment about a driver
	 * @param driverId
	 * @param passengerId
	 * @return Driver comment
	 * @throws Exception
	 */
	public DriverCommentJPA getDataDriverComment(String driverId, String passengerId) throws Exception
	{	
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		rateDriverRemote = (CommunicationFacadeRemote) ctx.lookup("java:app/CAT-PDP-GRUP6.jar/CommunicationFacadeBean!ejb.CommunicationFacadeRemote");
		return rateDriverRemote.getDriverComment(driverId, passengerId);
						
	}
		
}
