package managedbean;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import jpa.CarJPA;
import jpa.DriverJPA;
import jpa.TripJPA;
import jpa.UserDTO;

@ManagedBean(name = "trips")
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
	private TripJPA trip;
	private int screen = 0;
	protected int numTrips = 0;
	private DateFormat dateFormat = new SimpleDateFormat("EEEE, MMM d, yyyy");
	private DateFormat timeFormat = new SimpleDateFormat("HH:mm");
	
	public FindMyTripsMBean() {
		
	}
		
	public UserDTO getLogedUser() {
		logedUser = SessionBean.getLoggedUser();
		return logedUser;
	}
	public void setLogedUser(UserDTO logedUser) {
		this.logedUser = logedUser;
	}
	public String getDriverId() {
		driverId = getLogedUser().getId();
		return driverId;
	}
	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}
	public String getDriverName() {
		
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	
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
	
	private void tripList() throws Exception {
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		screen = 0;
		tripAdmFacadeRemote = (TripAdministrationFacadeRemote) ctx .lookup("java:app/CAT-PDP-GRUP6.jar/TripAdministrationFacadeBean!ejb.TripAdministrationFacadeRemote");
		tripsList = (Collection<TripJPA>)tripAdmFacadeRemote.findMyTrips(getDriverId());
	}
	
	public void nextScreen() {
		if (((screen + 1) * 10 < tripsList.size())) {
			screen += 1;
		}
	}

	public void previousScreen() {
		if ((screen > 0)) {
			screen -= 1;
		}
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

	public String getCar() {
		CarJPA car = trip.getCar();
		return car.getBrand() + " " + car.getModel() + " color " + car.getColor();
	}	
}
