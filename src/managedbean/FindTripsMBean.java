package managedbean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ejb.TripFacadeRemote;
import jpa.TripJPA;

@ManagedBean(name = "findTripsController")
@ViewScoped
public class FindTripsMBean implements Serializable {

	private static final long serialVersionUID = -8313865001184025539L;

	@EJB
	private TripFacadeRemote tripFacadeRemote;

	private String departureCity;
	private Date departureDate;
	private String arrivalCity;
	private List<TripJPA> trips;

	private final String emptyListMessage = "<i class='fa fa-times'></i> Sorry we have no trips matching your search criteria right now...";

	private String searchMessage = "";

	public FindTripsMBean() {
		super();

	}

	public void findTrips() throws NamingException {
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		tripFacadeRemote = (TripFacadeRemote) ctx
				.lookup("java:app/CAT-PDP-GRUP6.jar/TripFacadeBean!ejb.TripFacadeRemote");
		trips = tripFacadeRemote.findTrips(departureCity, departureDate, arrivalCity);
		if (trips == null || trips.isEmpty()) {
			searchMessage = emptyListMessage;
		}
	}

	public String getDepartureCity() {
		return departureCity;
	}

	public void setDepartureCity(String departureCity) {
		this.departureCity = departureCity;
	}

	public Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	public String getArrivalCity() {
		return arrivalCity;
	}

	public void setArrivalCity(String arrivalCity) {
		this.arrivalCity = arrivalCity;
	}

	public List<TripJPA> getTrips() {
		return trips;
	}

	public void setTrips(List<TripJPA> trips) {
		this.trips = trips;
	}

	public String getSearchMessage() {
		return searchMessage;
	}
}
