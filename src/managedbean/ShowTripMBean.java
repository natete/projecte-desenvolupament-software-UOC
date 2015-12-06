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
import jpa.DriverJPA;
import jpa.TripJPA;

@ManagedBean(name = "showTripController")
@ViewScoped
public class ShowTripMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private TripFacadeRemote tripFacadeRemote;

	DateFormat dateFormat = new SimpleDateFormat("EEEE, MMM d, yyyy");
	DateFormat timeFormat = new SimpleDateFormat("HH:mm");

	private Integer tripId;
	private TripJPA trip;

	public ShowTripMBean() {
		super();
	}

	public void init() throws NamingException {
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		tripFacadeRemote = (TripFacadeRemote) ctx
				.lookup("java:app/CAT-PDP-GRUP6.jar/TripFacadeBean!ejb.TripFacadeRemote");
		trip = tripFacadeRemote.showTrip(tripId);
	}

	public Integer getTripId() {
		return tripId;
	}

	public void setTripId(Integer tripId) {
		this.tripId = tripId;
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

	public String getDriver() {
		DriverJPA driver = trip.getDriver();

		return driver.getName() + " " + driver.getSurname();
	}

	public float getPrice() {
		return trip.getPrice();
	}

	public int getAvailableSeats() {
		return trip.getAvailableSeats();
	}

	public String getDescription() {
		return trip.getDescription();
	}
}
