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
import jpa.TripJPA;
import jpa.UserDTO;

/**
 * FindMyTripsMBean
 * @author GRUP6 jordi-nacho-ximo-joan
 */
@ManagedBean(name = "findMyTripsController")
@ViewScoped
public class FindMyTripsMBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private TripAdministrationFacadeRemote tripAdmFacadeRemote;
	protected Collection<TripJPA> tripsListView;
	private Collection<TripJPA> tripsList;
	private UserDTO logedUser;
	private String driverId;
	private String driverName;
	private Integer tripId;
	private int screen = 0;
	protected int numTrips = 0;
	
	
	/**
	 * FindMyTripsMBean Default Constructor.
	 */
	public FindMyTripsMBean() {
		super();
	}
	
	/**
	 * Returns parameter logedUser via SessionBean.
	 * @return logedUser.
	 */
	public UserDTO getLogedUser() {
		logedUser = SessionBean.getLoggedUser();
		return logedUser;
	}
	
	/**
	 * Returns parameter tripId.
	 * @return tripId.
	 */
	public Integer getTripId() {
		return tripId;
	}
	
	/**
	 * Sets parameter tripId.
	 * @param tripId.
	 */
	public void setTripId(Integer tripId) {
		this.tripId = tripId;
	}
	
	/**
	 * Sets parameter logeUser via SessionBean.
	 * @param logedUser.
	 */
	public void setLogedUser(UserDTO logedUser) {
		this.logedUser = logedUser;
	}
	
	/**
	 * Returns parameter driverId.
	 * @return driverId.
	 */
	public String getDriverId() {
		driverId = getLogedUser().getId();
		return driverId;
	}
	
	/**
	 * Sets parameter driverId.
	 * @param driverId.
	 */
	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}
	
	/**
	 * Returns parameter driverName.
	 * @return driverName.
	 */
	public String getDriverName() {
		
		return driverName;
	}
	
	/**
	 * Sets parameter driverName.
	 * @param driverName.
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	
	
	/**
	 * Returns a list of trips owned by a 
	 * logged driver. The list is formatted as
	 * Collection&lt;PassengerJPA&gt;. The 
	 * method lists screen up to 10 passengers.
	 * @see tripList() to know how this method 
	 * gets the trips from the database.
	 * @return Collection&lt;TripJPA&gt;.
	 * @throws Exception
	 */
	public Collection<TripJPA> getTripListView() throws Exception {
		int n = 0;
		tripsListView = new ArrayList<TripJPA>();
		this.tripList();
		for (Iterator<TripJPA> iter2 = tripsList.iterator(); iter2.hasNext();) {
			TripJPA trip = (TripJPA) iter2.next();
			if (n >= screen * 10 && n < (screen * 10 + 10)) {
				this.tripsListView.add(trip);
			}
			n += 1;
		}
		this.numTrips = n;
		return tripsListView;
	}
	
	/**
	 * Gets all trips owned by a logged user
	 * from carsharing.trip table. This method 
	 * is used by getTripListView() to show on 
	 * screen the passengers.
	 * @show getTripListView().
	 * @throws Exception.
	 */
	private void tripList() throws Exception {
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		screen = 0;
		tripAdmFacadeRemote = (TripAdministrationFacadeRemote) ctx .lookup("java:app/CAT-PDP-GRUP6.jar/TripAdministrationFacadeBean!ejb.TripAdministrationFacadeRemote");
		tripsList = (Collection<TripJPA>)tripAdmFacadeRemote.findMyTrips(getDriverId());
	}
	
	/**
	 * Method used by getTripListView() to page 
	 * all passenger registered on a trip.
	 */
	public void nextScreen() {
		if (((screen + 1) * 10 < tripsList.size())) {
			screen += 1;
		}
	}
	
	/**
	 * Method used by getTripListView() to page 
	 * all passenger registered on a trip.
	 */
	public void previousScreen() {
		if ((screen > 0)) {
			screen -= 1;
		}
	}
}
