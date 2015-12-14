package managedbean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
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
	//@NotEmpty(message = "Departure City cannot be empty!")
	private String departureCity;
	//@NotEmpty(message = "Departure Place cannot be empty!")
	private String fromPlace;
	//@NotEmpty(message = "Departure Date cannot be empty!")
	//@Future(message = "You must type a correct Date!")
	private Date departureDate;
	//@NotEmpty(message = "Departure Time cannot be empty!")
	//@Future(message = "you must type a correct Time!")
	//@Pattern(regexp = "[0-2][0-9]:[0-5][0-9]", message = "Hour between 00:00 and 23:59")
	private Date departureTime;
	//@NotEmpty(message = "Arrival City cannot be empty!")
	private String arrivalCity;
	//@NotEmpty(message = "Arrival Place cannot be empty!")
	private String toPlace;
	//@NotEmpty(message = "Available Seats cannot be empty!")
	//@Digits(integer = 1 , fraction = 0, message = "You must provide a valid range!")
	//@Range(min=1, max=4, message="There are "+"#{addTrip.availableSeats}"+" available seats!")
	private int availableSeats;
	//@NotEmpty(message = "Price cannot be empty!")
	//@Digits(integer = 2, fraction = 0, message = "You must provide a valid age!")
	//@Range(min=0, max=20, message="Price between 0 and 20 €")
	private float price;
	
	private FacesMessage message;
	
	
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
		String errorMessage = null;
		
		Properties properties = System.getProperties();
		Context context = new InitialContext(properties);
		addTripRemote = (TripAdministrationFacadeRemote) context.lookup("java:app/CAT-PDP-GRUP6.jar/TripAdministrationFacadeBean!ejb.TripAdministrationFacadeRemote");
		
		Pattern pattern = Pattern.compile("^[a-zA-Z]+( *[a-zA-Z])*$");
		
		Matcher matcher = pattern.matcher(this.departureCity);
		if (!matcher.matches()) {
			errorMessage = "You must enter a Departure City, without extra characters";
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, errorMessage);
			FacesContext.getCurrentInstance().addMessage("form:errorView", message);
		}
		
		matcher = pattern.matcher(this.fromPlace);
		if (!matcher.matches()) {
			errorMessage = "You must enter a Departure Place, without extra characters";
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, errorMessage);
			FacesContext.getCurrentInstance().addMessage("form:errorView", message);
		}
		
		matcher = pattern.matcher(this.arrivalCity);
		if (!matcher.matches()) {
			errorMessage = "You must enter an Arrival City, without extra characters";
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, errorMessage);
			FacesContext.getCurrentInstance().addMessage("form:errorView", message);
		}
		
		matcher = pattern.matcher(this.toPlace);
		if (!matcher.matches()) {
			errorMessage = "You must enter an Arrival Place, without extra characters";
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, errorMessage);
			FacesContext.getCurrentInstance().addMessage("form:errorView", message);
		}
				
		Date date = new Date();
		Integer thisDay = date.getDate();
		Integer thisMonth = date.getMonth();
		// add 1 to month because starts counting from 0;
		thisMonth ++;
		Integer thisYear = date.getYear();
		// add 1900 to year beacause starts counting from 1900;
		thisYear +=1900;
		
        Integer depDay = this.departureDate.getDate();
        Integer depMonth = this.departureDate.getMonth();
        depMonth ++;
        Integer depYear = this.departureDate.getYear();
        depYear += 1900;
        
        if (thisYear.shortValue() == depYear.shortValue()) {
        	if (thisMonth.shortValue() < depMonth.shortValue());
        	if (thisMonth.shortValue() == depMonth.shortValue()) {
        		if (thisDay.shortValue() < depDay.shortValue());
        		if (thisDay.shortValue() == depDay.shortValue()) {
        			errorMessage = "You must enter a Date that matches as from Tomorrow (DAY)";
	        		message = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, errorMessage);
	        		FacesContext.getCurrentInstance().addMessage("form:errorView", message);
        		}
        		if (thisDay.shortValue() > depDay.shortValue()) {
        			errorMessage = "You must enter a Date that matches as from Tomorrow (DAY)";
	        		message = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, errorMessage);
	        		FacesContext.getCurrentInstance().addMessage("form:errorView", message);
        		}
        	}
        	if (thisMonth.shortValue() > depMonth.shortValue()) {
        		errorMessage = "You must enter a Date that matches as from Tomorrow (MONTH)";
    			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, errorMessage);
    			FacesContext.getCurrentInstance().addMessage("form:errorView", message);
        	}
        }
        if (thisYear.shortValue() > depYear.shortValue()) {
        	errorMessage = "You must enter a Date that matches as from Tomorrow (YEAR)";
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, errorMessage);
			FacesContext.getCurrentInstance().addMessage("form:errorView", message);
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
    	String depTime = sdf.format(this.departureTime.getTime());
        pattern = Pattern.compile("([01]?[0-9]|2[0-3]):[0-5][0-9]");
        matcher = pattern.matcher(depTime);
        if (!matcher.matches()) {
        	errorMessage = "You must enter a Time as like 09:15";
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, errorMessage);
			FacesContext.getCurrentInstance().addMessage("form:errorView", message);
        }
        
        date = this.departureDate;
        Integer depHour = date.getHours();
		if (depHour.intValue() < 0 && depHour.intValue() > 23) {
			errorMessage = "Hour must be between 00 and 23";
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, errorMessage);
			FacesContext.getCurrentInstance().addMessage("form:errorView", message);
		}
		Integer depMins = date.getMinutes();
		if (depMins.intValue() < 0 && depMins.intValue() > 59) {
			errorMessage = "Minuts must be between 00 and 59";
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, errorMessage);
			FacesContext.getCurrentInstance().addMessage("form:errorView", message);
		}
        
        if (errorMessage != null) {
			return "errorView";
			
		} else {
			
			UserDTO userDTO = SessionBean.getLoggedUser();
			String nif = userDTO.getId();
			
			addTripRemote.addTrip(description, departureCity, fromPlace, departureDate, departureTime, arrivalCity, toPlace, availableSeats, price, nif);
			
			this.setDepartureCity("");
			this.setFromPlace("");
			this.setDepartureDate(null);
			this.setDepartureTime(null);
			this.setArrivalCity("");
			this.setToPlace("");
			this.setAvailableSeats(0);
			this.setPrice(0);
			
			return "/pages/public/findTripsView";
		}
	}
}
