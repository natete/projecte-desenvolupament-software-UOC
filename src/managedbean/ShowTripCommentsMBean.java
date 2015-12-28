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

	// stores all the instances of MessageJPA
	private Collection<MessageJPA> tripCommentsList;
	// stores the screen number where the user is
	private int screen = 0;
	// stores ten or fewer MessageJPA instances that the user can see on a
	// screen
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

	public void init() throws NamingException {
		loggedUser = SessionBean.getLoggedUser();
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		System.out.println("tripId " + tripId);
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

	private void populateTripCommentsList() {
		int n = 0;
		tripCommentsView.clear();
		for (Iterator<MessageJPA> iter2 = tripCommentsList.iterator(); iter2.hasNext();) {
			MessageJPA driverComment = (MessageJPA) iter2.next();
			if (n >= screen * PAGE_SIZE && n < (screen * PAGE_SIZE + PAGE_SIZE)) {
				this.tripCommentsView.add(driverComment);
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

	public TripJPA getTrip() {
		return this.trip;
	}

	public void setTrip(TripJPA trip) {
		this.trip = trip;
	}

	public int getTripId() {
		return this.tripId;
	}

	public void setTripId(int tripId) {
		this.tripId = tripId;
	}

	public String getDriver() {
		return trip.getDriver().getFullName();
	}

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

	public boolean getIsFirstScreen() {
		return screen == 0;
	}

	public boolean getIsLastScreen() {
		return ((int) (tripCommentsList.size() / PAGE_SIZE)) == screen;
	}
}
