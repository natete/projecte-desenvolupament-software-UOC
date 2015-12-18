package managedbean;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

@ManagedBean(name = "removeFromTripController")
@ViewScoped
public class RemoveFromTripMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private TripFacadeRemote tripFacadeRemote;

	DateFormat dateFormat = new SimpleDateFormat("EEEE, MMM d, yyyy");
	DateFormat timeFormat = new SimpleDateFormat("HH:mm");

	private Integer tripId;
	private TripJPA trip;
	private UserDTO loggedUser;

	private String errorMessage;

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
		}
		loggedUser = SessionBean.getLoggedUser();
		return result;
	}

	public String removeFromTrip() {
		String result = "showTrip";
		try {
			tripFacadeRemote.removeFromTrip(loggedUser.getId(), tripId);
		} catch (IllegalArgumentException e) {
			errorMessage = e.getMessage();
			result = "errorView";
		}
		return result;
	}

	public String cancelRemoving() {
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
}
