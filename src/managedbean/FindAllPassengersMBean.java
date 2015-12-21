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
import jpa.TripJPA;

@ManagedBean(name = "showPassengersTripController")
@ViewScoped
public class FindAllPassengersMBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String EMPTY_LIST_MESSAGE = "<i class='fa fa-times'></i> There are no passengers for this trip right now...";
	
	@EJB
	private TripAdministrationFacadeRemote tripAdmFacadeRemote;
	
	private int tripId;
	private String searchMessage = "";
	private Collection<PassengerJPA> passengersListView;
	private Collection<PassengerJPA> passengersList;
	private PassengerJPA passenger;
	private int screen = 0;
	protected int numPassengers = 0;
		
	public FindAllPassengersMBean() {
		
	}
		
	public int getTripId() {
		return tripId;
	}
	public void setTripId(int tripId) {
		this.tripId = tripId;
	}
		
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
	
	private void passengersList() throws Exception {
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		screen = 0;
		tripAdmFacadeRemote = (TripAdministrationFacadeRemote) ctx .lookup("java:app/CAT-PDP-GRUP6.jar/TripAdministrationFacadeBean!ejb.TripAdministrationFacadeRemote");
		passengersList = (Collection<PassengerJPA>)tripAdmFacadeRemote.findAllPassengers(getTripId());
	}
}