
package managedbean;

import java.io.Serializable;
import java.util.*;

import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import jpa.DriverCommentJPA;
import jpa.DriverJPA;
import jpa.TripJPA;
import ejb.CommunicationFacadeRemote;

/**
 * Managed Bean ShowDriverCommentsMBean
 */
@ManagedBean(name = "drivercomments")
@SessionScoped
public class ShowDriverCommentsMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private CommunicationFacadeRemote driverCommentsRemote;

	// stores the nif driver
	private String driverId;
	// stores the nif passenger
	private String passengerId;
	// stores all the instances of DriverCommentJPA
	private Collection<DriverCommentJPA> driverCommentsList;
	// stores the screen number where the user is
	private int screen = 0;
	// stores ten or fewer DriverCommentJPA instances that the user can see on a screen
	protected Collection<DriverCommentJPA> driverCommentsView;
	// stores a instance of DriverJPA
	protected DriverJPA driver;
	protected int numberDriverComments = 0;
	public int tripId;
	protected TripJPA trip;
	

	/**
	 * Constructor method
	 * 
	 * @throws Exception
	 */
	public ShowDriverCommentsMBean() throws Exception {
		super();
	}
	
	public void init() throws NamingException {
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		driverCommentsRemote = (CommunicationFacadeRemote) ctx.lookup("java:app/CAT-PDP-GRUP6.jar/CommunicationFacadeBean!ejb.CommunicationFacadeRemote");
		driver = (DriverJPA) driverCommentsRemote.findDriver(this.getDriverId());
	}


	/**
	 * Method that returns an instance Collection of 10 or less DriverCommentJPA according
	 * screen where the user is.
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

	public void previousScreen() {
		if ((screen > 0)) {
			screen -= 1;
		}
	}

	public String getDriverId() {
		return this.driverId;
	}

	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}
	
	public String getPassengerId() {
		return this.passengerId;
	}

	public void setPassengerId(String passengerId) {
		this.passengerId = passengerId;
	}
	
	public int getTripId() {
		return this.tripId;
	}

	public void setTripId(int tripId) {
		this.tripId = tripId;
	}

	public String getDriver() {
		return driver.getName() + " " + driver.getSurname();
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

	/**
	 * Method that gets a list of instances all DriverCommentJPA
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void driverCommentsList() throws Exception {
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		screen = 0;
		driverCommentsRemote = (CommunicationFacadeRemote) ctx.lookup("java:app/CAT-PDP-GRUP6.jar/CommunicationFacadeBean!ejb.CommunicationFacadeRemote");
		driverCommentsList = (Collection<DriverCommentJPA>) driverCommentsRemote.showDriverComments(this.getDriverId());
	}
}
