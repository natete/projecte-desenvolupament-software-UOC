package managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
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
	private static final String EMPTY_LIST_MESSAGE = "<i class='fa fa-times'></i>"
		+ "No passengers for this trip.";
	@EJB
	private TripAdministrationFacadeRemote tripAdmFacadeRemote;
	private int tripId;
	private Collection<PassengerJPA> passengersListView;
	private Collection<PassengerJPA> passengersList;
	private int screen = 0;
	protected int numPassengers = 0;
	private String tripDepartureCity;
	private String tripArrivalCity;
	private Date tripDepartureDate;
	private Date tripDepartureTime;
	private TripJPA trip;
	private String searchMessage = "";
	
	
	/**
	 * AddTripMBean Default Constructor.
	 */
	public FindAllPassengersMBean() {
		super();
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
	
	public String getTripDepartureCity() {
		return tripDepartureCity;
	}

	public void setTripDepartureCity(String tripDepartureCity) {
		this.tripDepartureCity = tripDepartureCity;
	}

	public String getTripArrivalCity() {
		return tripArrivalCity;
	}

	public void setTripArrivalCity(String tripArrivalCity) {
		this.tripArrivalCity = tripArrivalCity;
	}

	public Date getTripDepartureDate() {
		return tripDepartureDate;
	}

	public void setTripDepartureDate(Date tripDepartureDate) {
		this.tripDepartureDate = tripDepartureDate;
	}

	public Date getTripDepartureTime() {
		return tripDepartureTime;
	}

	public void setTripDepartureTime(Date tripDepartureTime) {
		this.tripDepartureTime = tripDepartureTime;
	}
	
	public Collection<PassengerJPA> getPassengersList() {
		return passengersList;
	}
	
	public void setPassengersList(Collection<PassengerJPA> passengersList) {
		this.passengersList = passengersList;
	}

	public TripJPA getTrip() {
		return trip;
	}

	public void setTrip(TripJPA trip) {
		this.trip = trip;
	}
	
	public String getSearchMessage() {
		return searchMessage;
	}

	public void setSearchMessage(String searchMessage) {
		this.searchMessage = searchMessage;
	}
	

	/**
	 * Returns a list of passengers registered in a trip
	 * owned by a logged driver. The list is formatted as
	 * Collection&lt;PassengerJPA&gt;. The method lists 
	 * screen up to 10 passengers.
	 * @see passengersList() to know how this method gets
	 * the passengers from the database.
	 * @return Collection&lt;PassengerJPA&gt;.
	 * @throws Exception.
	 */
	public Collection<PassengerJPA> getAllPassengers() throws Exception {
		
		int n = 0;
		passengersListView = new ArrayList<PassengerJPA>();
		this.passengersList();
		for (Iterator<PassengerJPA> iter2 = passengersList.iterator(); iter2.hasNext();) {
			PassengerJPA passenger = (PassengerJPA) iter2.next();
			if (n >= screen * 10 && n < (screen * 10 + 10)) {
				this.passengersListView.add(passenger);
			}
			n += 1;
		}
		this.numPassengers = n;
		return passengersListView;
	}
	
	/**
	 * Gets all passengers registered on a trip
	 * from carsharing.trip table. This method 
	 * is used by getAllPassengers() to show on 
	 * screen the passengers.
	 * @show getAllPassengers().
	 * @throws Exception.
	 */
	private void passengersList() throws Exception {
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		screen = 0;
		tripAdmFacadeRemote = (TripAdministrationFacadeRemote) ctx 
				.lookup("java:app/CAT-PDP-GRUP6.jar/TripAdministrationFacadeBean!ejb."
						+ "TripAdministrationFacadeRemote");
		passengersList = (Collection<PassengerJPA>)tripAdmFacadeRemote.findAllPassengers(getTripId());
		if (passengersList.isEmpty() || passengersList == null) {
			searchMessage = EMPTY_LIST_MESSAGE;
		}
	}
	
	/**
	 * Method used by getAllPassenger() to page 
	 * all passenger registered on a trip.
	 */
	public void nextScreen() {
		if (((screen + 1) * 10 < passengersList.size())) {
			screen += 1;
		}
	}
	
	/**
	 * Method used by getAllPassenger() to page 
	 * all passenger registered on a trip.
	 */
	public void previousScreen() {
		if ((screen > 0)) {
			screen -= 1;
		}
	}
	
	public void getTripData() throws NamingException {
		Properties properties = System.getProperties();
		Context context = new InitialContext(properties);
		tripAdmFacadeRemote = (TripAdministrationFacadeRemote) context
			.lookup("java:app/CAT-PDP-GRUP6.jar/TripAdministrationFacadeBean!ejb"
					+ ".TripAdministrationFacadeRemote");
		trip = tripAdmFacadeRemote.getTrip(tripId);
		tripDepartureCity = trip.getDepartureCity();
		tripArrivalCity = trip.getArrivalCity();
		tripDepartureDate = trip.getDepartureDate();
		tripDepartureTime = trip.getDepartureTime();
	}
}