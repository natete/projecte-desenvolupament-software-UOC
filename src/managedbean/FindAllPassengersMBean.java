package managedbean;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Locale;
import java.util.Properties;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ejb.TripAdministrationFacadeRemote;
import jpa.PassengerJPA;
import jpa.TripJPA;

/**
 * FindAllPassengersMBean
 * @author GRUP6 jordi-nacho-ximo-joan
 */
@ManagedBean(name = "findAllPassengersController")
@ViewScoped
public class FindAllPassengersMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private TripAdministrationFacadeRemote tripAdmFacadeRemote;

	private int tripId;
	private Collection<PassengerJPA> passengers;
	private TripJPA trip;
	private Locale browserLocale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
	private DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL, browserLocale);
	private DateFormat timeFormat = new SimpleDateFormat("HH:mm");

	/**
	 * AddTripMBean Default Constructor.
	 */
	public FindAllPassengersMBean() {
		super();
	}

	public String getTrip() {
		StringBuilder result = new StringBuilder(" ");
		result.append(trip.getDepartureCity());
		result.append(" - ");
		result.append(trip.getArrivalCity());
		result.append(" ");
		result.append(dateFormat.format(trip.getDepartureDate()));
		result.append(" - ");
		result.append(timeFormat.format(trip.getDepartureTime()));
		return result.toString();
	}

	/**
	 * Returns parameter tripId.
	 * @return tripId.
	 */
	public int getTripId() {
		return tripId;
	}

	/**
	 * Sets parameter tripId.
	 * @param tripId.
	 */
	public void setTripId(int tripId) {
		this.tripId = tripId;
	}

	public Collection<PassengerJPA> getPassengers() {
		return passengers;
	}

	/**
	 * Gets all passengers registered on a trip
	 * from carsharing.trip table. This method 
	 * is used by getAllPassengers() to show on 
	 * screen the passengers.
	 * @show getAllPassengers().
	 * @throws Exception.
	 */
	public void init() throws NamingException {
		Properties properties = System.getProperties();
		Context context = new InitialContext(properties);
		tripAdmFacadeRemote = (TripAdministrationFacadeRemote) context.lookup(
				"java:app/CAT-PDP-GRUP6.jar/TripAdministrationFacadeBean!ejb" + ".TripAdministrationFacadeRemote");
		trip = tripAdmFacadeRemote.getTrip(tripId);
		passengers = (Collection<PassengerJPA>) tripAdmFacadeRemote.findAllPassengers(tripId);
	}
}