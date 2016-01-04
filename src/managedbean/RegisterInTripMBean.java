package managedbean;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ejb.TripFacadeRemote;
import jpa.TripJPA;
import jpa.UserDTO;

@ManagedBean(name = "registerInTripController")
@ViewScoped
public class RegisterInTripMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private TripFacadeRemote tripFacadeRemote;

	DateFormat dateFormat = new SimpleDateFormat("EEEE, MMM d, yyyy");
	DateFormat timeFormat = new SimpleDateFormat("HH:mm");

	private Integer tripId;
	private TripJPA trip;
	private UserDTO loggedUser;
	private boolean isRegistered;

	private String errorMessage;

	/**
	 * Method that initializes the managed bean and gets the trip passed as a
	 * parameter
	 * 
	 * @return error if the trip being requested doesn't exist
	 * @throws NamingException
	 */
	public String init() throws NamingException {
		String result = "";
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		tripFacadeRemote = (TripFacadeRemote) ctx
				.lookup("java:app/CAT-PDP-GRUP6.jar/TripFacadeBean!ejb.TripFacadeRemote");
		trip = tripFacadeRemote.showTrip(tripId);
		if (trip == null) {
			this.errorMessage = "The required trip does not exist";
			result = "errorView";
		} else if (!isFutureTrip(trip)) {
			this.errorMessage = "You can not register in a past trip";
			result = "errorView";
		}
		loggedUser = SessionBean.getLoggedUser();
		isRegistered = trip.hasPassenger(loggedUser.getId());
		return result;
	}

	private boolean isFutureTrip(TripJPA trip2) {
		Calendar now = Calendar.getInstance();
		Calendar tripDate = Calendar.getInstance();
		tripDate.setTime(trip.getDepartureDate());
		return now.before(tripDate);
	}

	/**
	 * Registers the logged passenger in the trip
	 * 
	 * @return redirects to the showTrip view if success to error view otherwise
	 */
	public String registerInTrip() {
		String result = "showTrip";
		try {
			tripFacadeRemote.registerInTrip(loggedUser.getId(), tripId);
		} catch (IllegalArgumentException e) {
			errorMessage = e.getMessage();
			result = "errorView";
		}
		return result;
	}

	public String cancelRegistration() {
		return "showTrip";
	}

	public Integer getTripId() {
		return tripId;
	}

	public void setTripId(Integer tripId) {
		this.tripId = tripId;
	}

	public String getErrorMessage() {
		return this.errorMessage;
	}

	public String getPickupPoint() {
		return trip.getDepartureCity() + ", " + trip.getFromPlace();
	}

	public String getDropoffPoint() {
		return trip.getArrivalCity() + ", " + trip.getToPlace();
	}

	public String getDate() {
		return dateFormat.format(trip.getDepartureDate()) + " - " + timeFormat.format(trip.getDepartureTime());
	}

	public String getLoggedUsername() {
		return loggedUser.getUsername();
	}

	public boolean getIsRegistered() {
		return isRegistered;
	}

}
