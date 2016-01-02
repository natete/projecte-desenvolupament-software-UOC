
package managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ejb.CommunicationFacadeRemote;
import jpa.DriverCommentJPA;
import jpa.DriverJPA;
import jpa.TripJPA;
import jpa.UserDTO;

/**
 * Managed Bean ShowDriverCommentsMBean
 */
@ManagedBean(name = "drivercomments")
@SessionScoped
public class ShowDriverCommentsMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Object HTML_FULL_STAR = "<i class=\"fa fa-star\"></i>";
	private static final Object HTML_EMPTY_STAR = "<i class=\"fa fa-star-o\"></i>";

	@EJB
	private CommunicationFacadeRemote driverCommentsRemote;

	
	private String passengerId;
	private Collection<DriverCommentJPA> driverCommentsList;
	private int screen = 0;
	protected Collection<DriverCommentJPA> driverCommentsView;
	private DriverJPA driver;
	private int numberDriverComments = 0;
	private int tripId;
	private TripJPA trip;
	private UserDTO loggedUser;
	private String noCommentsMsg = "<i class=\"fa fa-times\"></i> There are no comments about this driver";

	/**
	 * Constructor method
	 * 
	 * @throws Exception
	 */
	public ShowDriverCommentsMBean() throws Exception {
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
		driverCommentsRemote = (CommunicationFacadeRemote) ctx
				.lookup("java:app/CAT-PDP-GRUP6.jar/CommunicationFacadeBean!ejb.CommunicationFacadeRemote");
		trip = (TripJPA) driverCommentsRemote.findTrip(this.getTripId());
		driver = trip.getDriver();
		loggedUser = SessionBean.getLoggedUser();
	}

	/**
	 * Method that returns an instance Collection of 10 or less DriverCommentJPA
	 * according screen where the user is.
	 * 
	 * @return Collection DriverCommentJPA
	 */
	public Collection<DriverCommentJPA> getDriverCommentsListView() throws Exception {
		int n = 0;
		driverCommentsView = new ArrayList<DriverCommentJPA>();
		this.driverCommentsList();
		for (Iterator<DriverCommentJPA> iter2 = driverCommentsList.iterator(); iter2.hasNext();) {
			DriverCommentJPA driverComment = (DriverCommentJPA) iter2.next();
			if (n >= screen * 10 && n < (screen * 10 + 10)) {
				this.driverCommentsView.add(driverComment);
			}
			n += 1;
		}
		System.out.println("TripId " + this.getTripId());
		this.numberDriverComments = n;
		return driverCommentsView;
	}

	/**
	 * Returns the total number of instances of DriverCommentJPA
	 * 
	 * @return Car number
	 */
	public int getNumberDriverComments() {
		return this.numberDriverComments;
	}

	/**
	 * allows forward or backward in user screens
	 */
	public void nextScreen() {
		if (((screen + 1) * 10 < driverCommentsList.size())) {
			screen += 1;
		}
	}

	/**
	 * allows backward in user screens
	 */
	public void previousScreen() {
		if ((screen > 0)) {
			screen -= 1;
		}
	}

	/**
	 * Method to get the PassengerId
	 * 
	 * @return passengerId
	 */
	public String getPassengerId() {
		return this.passengerId;
	}

	/**
	 * Method to set the PassengerId
	 */
	public void setPassengerId(String passengerId) {
		this.passengerId = passengerId;
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
	 * Method to get Driver name + Driver surname
	 * 
	 * @return driver name + driver surname
	 */
	public String getDriver() {
		return driver.getFullName();
	}

	/**
	 * Method to get the Message There are no comments
	 * 
	 * @return noCommentsMsg
	 */
	public String getNoCommentsMsg() {
		return noCommentsMsg;
	}

	/**
	 * Method that returns a Collection of DriverCommentJPA
	 * 
	 * @return Collection DriverCommentJPA
	 */
	public Collection<DriverCommentJPA> getDriverCommentsList() {
		return this.driverCommentsList;
	}

	/**
	 * Method than returns if Passenger is logged
	 * 
	 * @return boolean
	 */
	public boolean isPassengerLogged() {
		return loggedUser != null && loggedUser.isPassenger();
	}

	/**
	 * Method than returns if Passenger is enrolled in the trip
	 * 
	 * @return boolean
	 */
	public boolean isLoggedUserInTrip() {
		return isPassengerLogged() && trip.hasPassenger(loggedUser.getId());
	}

	/**
	 * Method used for Facelet to call driverCommentsView Facelet
	 * 
	 * @return Facelet name
	 * @throws Exception
	 */
	public String showDriverComments() throws Exception {
		driverCommentsList();
		return "driverCommentsView";
	}

	/**
	 * Method used for Facelet to call rateDriver Facelet
	 * 
	 * @return Facelet name
	 */
	public String rateDriver() {
		return "rateDriverView";
	}

	public String getRating(int rating) {
		StringBuilder result = new StringBuilder();

		for (int i = 0; i < 10; i++) {
			if (i < rating) {
				result.append(HTML_FULL_STAR);
			} else {
				result.append(HTML_EMPTY_STAR);
			}
		}

		return result.toString();
	}

	/**
	 * Method that gets a list of instances all DriverCommentJPA
	 * 
	 * @throws Exception
	 */
	private void driverCommentsList() throws Exception {
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		screen = 0;
		driverCommentsRemote = (CommunicationFacadeRemote) ctx
				.lookup("java:app/CAT-PDP-GRUP6.jar/CommunicationFacadeBean!ejb.CommunicationFacadeRemote");
		driverCommentsList = (Collection<DriverCommentJPA>) driverCommentsRemote.showDriverComments(driver.getNif());
	}
}
