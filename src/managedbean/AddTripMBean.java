package managedbean;

import java.io.Serializable;
import java.util.Date;
import java.util.Properties;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ejb.TripAdministrationFacadeRemote;

@ManagedBean(name = "addtrip")
@SessionScoped
public class AddTripMBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String description;
	private String departureCity;
	private String fromPlace;
	private Date departureDate;
	private Date departureTime;
	private String arrivalCity;
	private String toPlace;
	private Integer availableSeats;
	private float price;
	
	@EJB
	private TripAdministrationFacadeRemote tripAdministrationRemote;
	
	public AddTripMBean() {
		super();
	}
	
	public String addTrip() throws NamingException {
		
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		tripAdministrationRemote = (TripAdministrationFacadeRemote) ctx.lookup("java:app/CAT-PDP-GRUP6.jar/TripAdministrationFacadeBean!ejb.TripAdministrationFacadeRemote");
		
		tripAdministrationRemote.addTrip(departureCity, fromPlace, departureDate, departureTime, arrivalCity, toPlace, availableSeats, price, description);
		
		
		return "index";
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDepartureCity() {
		return departureCity;
	}

	public void setDepartureCity(String departureCity) {
		this.departureCity = departureCity;
	}

	public String getFromPlace() {
		return fromPlace;
	}

	public void setFromPlace(String fromPlace) {
		this.fromPlace = fromPlace;
	}

	public Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	public Date getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}

	public String getArrivalCity() {
		return arrivalCity;
	}

	public void setArrivalCity(String arrivalCity) {
		this.arrivalCity = arrivalCity;
	}

	public String getToPlace() {
		return toPlace;
	}

	public void setToPlace(String toPlace) {
		this.toPlace = toPlace;
	}

	public Integer getAvailableSeats() {
		return availableSeats;
	}

	public void setAvailableSeats(Integer availableSeats) {
		this.availableSeats = availableSeats;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
}
