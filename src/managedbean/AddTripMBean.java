package managedbean;

import java.io.Serializable;
import java.util.Date;
import java.util.Properties;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.validation.constraints.Future;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import ejb.TripAdministrationFacadeRemote;
import jpa.UserDTO;

/**
 * AddTripMBean
 * @author GRUP6 -jordi-nacho-ximo-joan-
 */
@ManagedBean(name="addtrip")
@SessionScoped
public class AddTripMBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private TripAdministrationFacadeRemote addTripRemote;
	
	private String description;
	@NotEmpty(message = "Departure City cannot be empty!")
	private String departureCity;
	@NotEmpty(message = "Departure Place cannot be empty!")
	private String fromPlace;
	//@NotEmpty(message = "Departure Date cannot be empty!")
	@Future(message = "You must type a correct Date!")
	private Date departureDate;
	//@NotEmpty(message = "Departure Time cannot be empty!")
	//@Future(message = "you must type a correct Time!")
	//@Pattern(regexp = "[0-2][0-9]:[0-5][0-9]", message = "Hour between 00:00 and 23:59")
	private Date departureTime;
	@NotEmpty(message = "Arrival City cannot be empty!")
	private String arrivalCity;
	@NotEmpty(message = "Arrival Place cannot be empty!")
	private String toPlace;
	//@NotEmpty(message = "Available Seats cannot be empty!")
	//@Digits(integer = 1 , fraction = 0, message = "You must provide a valid range!")
	//@Range(min=1, max=4, message="There are "+"#{addTrip.availableSeats}"+" available seats!")
	private int availableSeats;
	//@NotEmpty(message = "Price cannot be empty!")
	//@Digits(integer = 2, fraction = 0, message = "You must provide a valid age!")
	//@Range(min=0, max=20, message="Price between 0 and 20 €")
	private float price;
	
	
	public AddTripMBean() throws Exception {
		
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
	
	public int getAvailableSeats() {
		return availableSeats;
	}
	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}
	
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	
	
	public String addTrip() throws Exception {
		
			Properties properties = System.getProperties();
			Context context = new InitialContext(properties);
			addTripRemote = (TripAdministrationFacadeRemote) context.lookup("java:app/CAT-PDP-GRUP6.jar/TripAdministrationFacadeBean!ejb.TripAdministrationFacadeRemote");
			addTripRemote.addTrip(description, departureCity, fromPlace, departureDate, departureTime, arrivalCity, toPlace, availableSeats, price);
			
			this.setDepartureCity("");
			this.setFromPlace("");
			this.setDepartureDate(null);
			this.setDepartureTime(null);
			this.setArrivalCity("");
			this.setToPlace("");
			this.setAvailableSeats(0);
			this.setPrice(0);
		
		return "findTripsView.xhtml";
	}
}
