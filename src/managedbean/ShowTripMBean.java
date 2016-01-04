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
import jpa.CarJPA;
import jpa.DriverJPA;
import jpa.TripJPA;
import jpa.UserDTO;

@ManagedBean(name = "showTripController")
@ViewScoped
public class ShowTripMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Object HTML_FULL_STAR = "<i class=\"fa fa-star\"></i>";
	private static final Object HTML_EMPTY_STAR = "<i class=\"fa fa-star-o\"></i>";

	@EJB
	private TripFacadeRemote tripFacadeRemote;

	DateFormat dateFormat = new SimpleDateFormat("EEEE, MMM d, yyyy");
	DateFormat timeFormat = new SimpleDateFormat("HH:mm");

	private Integer tripId;
	private String driverId;
	private TripJPA trip;
	private UserDTO loggedUser;
	private String errorMessage;

	public ShowTripMBean() {
		super();
	}

	/**
	 * Method that initializes the managed bean and gets the trip passed as a
	 * parameter
	 * 
	 * @return error if the trip being requested doesn't exist
	 * @throws NamingException
	 */
	public String init() throws NamingException {
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		tripFacadeRemote = (TripFacadeRemote) ctx
				.lookup("java:app/CAT-PDP-GRUP6.jar/TripFacadeBean!ejb.TripFacadeRemote");
		trip = tripFacadeRemote.showTrip(tripId);
		if (trip == null) {
			errorMessage = "The required trip does not exist";
			return "errorView";
		} else {
			driverId = trip.getDriver().getNif();
			loggedUser = SessionBean.getLoggedUser();
			return "";
		}
	}

	public Integer getTripId() {
		return tripId;
	}

	public void setTripId(Integer tripId) {
		this.tripId = tripId;
	}

	public String getDriverId() {
		return driverId;
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

	public String getCar() {
		CarJPA car = trip.getCar();

		return car.getBrand() + " " + car.getModel() + " color " + car.getColor();
	}

	public String getRating() {
		StringBuilder result = new StringBuilder();
		DriverJPA driver = trip.getDriver();

		for (int i = 0; i < 10; i++) {
			if (i < driver.getRating()) {
				result.append(HTML_FULL_STAR);
			} else {
				result.append(HTML_EMPTY_STAR);
			}
		}

		return result.toString();
	}

	public boolean isDriverLogged() {
		return loggedUser != null && loggedUser.isDriver();
	}

	public boolean isPassengerLogged() {
		return loggedUser != null && loggedUser.isPassenger();
	}

	public boolean isLoggedUserInTrip() {
		return isPassengerLogged() && trip.hasPassenger(loggedUser.getId());
	}

	public boolean isTripDriver() {
		return isDriverLogged() && trip.getDriver().getNif().equals(loggedUser.getId());
	}

	public String getErrorMessage() {
		return this.errorMessage;
	}

	public boolean getCanJoinTrip() {
		return isFutureTrip() && isPassengerLogged() && !isLoggedUserInTrip();

		// return true;
	}

	public boolean getCanLeaveTrip() {
		return isFutureTrip() && isLoggedUserInTrip();
	}

	private boolean isFutureTrip() {
		Calendar now = Calendar.getInstance();
		Calendar tripDate = Calendar.getInstance();
		tripDate.setTime(trip.getDepartureDate());
		return now.before(tripDate);
	}
}
