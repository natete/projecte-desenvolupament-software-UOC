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

import ejb.TripAdministrationFacadeRemote;
import jpa.TripJPA;
import jpa.UserDTO;

@ManagedBean(name = "deleteTripController")
@ViewScoped
public class DeleteTripMBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final DateFormat dateFormat = new SimpleDateFormat("EEEE, MMM d, yyyy");
	private static final DateFormat timeFormat = new SimpleDateFormat("HH:mm");

	@EJB
	private TripAdministrationFacadeRemote tripAdmFacadeRemote;

	private Integer tripId;
	private TripJPA trip;
	private UserDTO driver;
	private String pickupPoint;
	private String dropoffPoint;
	private String date;
	private String errorMessage;

	public DeleteTripMBean() throws NamingException {
		super();
	}

	public Integer getTripId() {
		return tripId;
	}

	public void setTripId(Integer tripId) {
		this.tripId = tripId;
	}

	public TripJPA getTrip() {
		return trip;
	}

	public void setTrip(TripJPA trip) {
		this.trip = trip;
	}

	public UserDTO getDriver() {
		return driver;
	}

	public void setDriver(UserDTO driver) {
		this.driver = driver;
	}

	public String getPickupPoint() {
		return pickupPoint;
	}

	public void setPickupPoint(String pickupPoint) {
		this.pickupPoint = pickupPoint;
	}

	public String getDropoffPoint() {
		return dropoffPoint;
	}

	public void setDropoffPoint(String dropoffPoint) {
		this.dropoffPoint = dropoffPoint;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void init() throws NamingException {
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		tripAdmFacadeRemote = (TripAdministrationFacadeRemote) ctx.lookup(
				"java:app/CAT-PDP-GRUP6.jar/TripAdministrationFacadeBean!ejb" + ".TripAdministrationFacadeRemote");
		trip = tripAdmFacadeRemote.getTrip(tripId);
		if (trip != null) {
			setPickupPoint(trip.getDepartureCity() + ", " + trip.getFromPlace());
			setDropoffPoint(trip.getArrivalCity() + ", " + trip.getToPlace());
			setDate(dateFormat.format(trip.getDepartureDate()) + ", " + timeFormat.format(trip.getDepartureTime()));
		}

	}

	public String deleteTrip() {
		String result;
		try {
			tripAdmFacadeRemote.deleteTrip(tripId);
			result = "findMyTrips";
		} catch (IllegalArgumentException e) {
			errorMessage = e.getMessage();
			result = "errorView";
		}
		return result;
	}
}
