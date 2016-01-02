package managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ejb.CommunicationFacadeRemote;
import jpa.MessageJPA;
import jpa.TripJPA;
import jpa.UserDTO;

/**
 * Managed Bean ShowTripCommentsMBean
 */
@ManagedBean(name = "tripcomments")
@ViewScoped
public class ShowTripCommentsMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final int PAGE_SIZE = 5;

	@EJB
	private CommunicationFacadeRemote tripCommentsRemote;
	private Collection<MessageJPA> tripCommentsList;
	private int screen = 0;
	private Collection<MessageJPA> tripCommentsView;
	private int tripId;
	private TripJPA trip;
	private UserDTO loggedUser;
	private boolean isDriverLogged;

	/**
	 * Constructor method
	 * 
	 * @throws Exception
	 */
	public ShowTripCommentsMBean() throws Exception {
		super();
		tripCommentsView = new ArrayList<>();
		screen = 0;
	}

	/**
	 * Method to make the init operations
	 * 
	 * @throws NamingException
	 */
	public void init() throws NamingException {
		loggedUser = SessionBean.getLoggedUser();
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		tripCommentsRemote = (CommunicationFacadeRemote) ctx
				.lookup("java:app/CAT-PDP-GRUP6.jar/CommunicationFacadeBean!ejb.CommunicationFacadeRemote");
		if (tripCommentsList == null) {
			tripCommentsList = (Collection<MessageJPA>) tripCommentsRemote.showTripComments(this.getTripId());
		}
		if (trip == null) {
			trip = (TripJPA) tripCommentsRemote.findTrip(this.getTripId());
			isDriverLogged = loggedUser != null && loggedUser.getId().equals(trip.getDriver().getNif());
		}
		populateTripCommentsList();
	}

	/**
	 * Method that returns an instance Collection of PAGE_SIZE or less
	 * MessageJPA according screen where the user is.
	 * 
	 * @return Collection MessageJPA
	 */
	public Collection<MessageJPA> getTripcommentsListView() throws Exception {
		return tripCommentsView;
	}

	/**
	 * Method to populate tripCommentsView with trip comments
	 */
	private void populateTripCommentsList() {
		int n = 0;
		tripCommentsView.clear();
		for (Iterator<MessageJPA> iter2 = tripCommentsList.iterator(); iter2.hasNext();) {
			MessageJPA tripComment = (MessageJPA) iter2.next();
			if (n >= screen * PAGE_SIZE && n < (screen * PAGE_SIZE + PAGE_SIZE)) {
				this.tripCommentsView.add(tripComment);
			}
			n += 1;
		}
	}

	/**
	 * allows forward or backward in user screens
	 */
	public void nextScreen() {
		if (((screen + 1) * PAGE_SIZE < tripCommentsList.size())) {
			screen += 1;
		}
	}

	public void previousScreen() {
		if ((screen > 0)) {
			screen -= 1;
		}
	}

	/**
	 * Method to get the trip
	 * @return trip
	 */
	public TripJPA getTrip() {
		return this.trip;
	}

	/**
	 * Method to set the trip
	 * @param trip
	 */
	public void setTrip(TripJPA trip) {
		this.trip = trip;
	}

	/**
	 * Method to get the TripId
	 * @return tripId
	 */
	public int getTripId() {
		return this.tripId;
	}

	/**
	 * Method to set the tripId
	 * @param tripId
	 */
	public void setTripId(int tripId) {
		this.tripId = tripId;
	}

	/**
	 * Method to get the driver
	 * @return driver
	 */
	public String getDriver() {
		return trip.getDriver().getFullName();
	}

	/**
	 * Method to check if the user logged has driver's role
	 */
	public boolean isDriverLogged() {
		return isDriverLogged;
	}

	/**
	 * Method used for Facelet to call askQuestion Facelet
	 * 
	 * @return Facelet name
	 */
	public String askQuestion() {
		return "askQuestionView";
	}

	/**
	 * Method to get if it's the first screen
	 */
	public boolean getIsFirstScreen() {
		return screen == 0;
	}

	/**
	 * Method to get if it's the last screen
	 */
	public boolean getIsLastScreen() {
		return ((int) (tripCommentsList.size() / PAGE_SIZE)) == screen;
	}
}
