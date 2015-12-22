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
import ejb.TripAdministrationFacadeRemote;
import jpa.PassengerJPA;

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
	private Collection<PassengerJPA> passengersListView;
	private Collection<PassengerJPA> passengersList;
	private int screen = 0;
	protected int numPassengers = 0;
	
	
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
		tripAdmFacadeRemote = (TripAdministrationFacadeRemote) ctx .lookup("java:app/CAT-PDP-GRUP6.jar/TripAdministrationFacadeBean!ejb.TripAdministrationFacadeRemote");
		passengersList = (Collection<PassengerJPA>)tripAdmFacadeRemote.findAllPassengers(getTripId());
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
}
