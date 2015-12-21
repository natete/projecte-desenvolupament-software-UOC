package managedbean;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import com.sun.javafx.collections.MappingChange.Map;
import ejb.TripAdministrationFacadeRemote;
import jpa.TripJPA;
import jpa.UserDTO;

@ManagedBean(name = "updateTripInformationController")
@ViewScoped
public class UpdateTripInformationMBean implements Serializable {
	
	private static final long serialVersionUID = 1L;	
	@EJB
	private TripAdministrationFacadeRemote updateTripInformationRemote;
	/*
	private String description;
	private String departureCity;
	private String fromPlace;
	private Date departureDate;
	private Date departureTime;
	private String arrivalCity;
	private String toPlace;
	private Integer availableSeats;
	private Float price;
	*/
	
	private String tripDescription;
	private String tripDepartureCity;
	private String tripFromPlace;
	private Date tripDepartureDate;
	private Date tripDepartureTime;
	private String tripArrivalCity;
	private String tripToPlace;
	private Integer tripAvailableSeats;
	private Float tripPrice;
	
	private Integer tripId;
	private FacesMessage message;
	private TripJPA trip;
	DateFormat dateFormat;
	DateFormat timeFormat;
	
	public UpdateTripInformationMBean() {
		
	}
	/*
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
	public void setAvailableSeats(Integer availableSeats) {
		this.availableSeats = availableSeats;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float i) {
		this.price = i;
	}
	*/
	public TripJPA getTrip() {
		return trip;
	}
	public void setTrip(TripJPA trip) {
		this.trip = trip;
	}
	public Integer getTripId() {
		return tripId;
	}
	public void setTripId(Integer tripId) {
		this.tripId = tripId;
	}
	public String getTripDepartureCity() {
		return trip.getDepartureCity();
	}
	public void setTripDepartureCity(String tripDepartureCity) {
		this.tripDepartureCity = tripDepartureCity;
	}
	public String getTripArrivalCity() {
		return trip.getArrivalCity();
	}
	public void setTripArrivalCity(String tripArrivalCity) {
		this.tripArrivalCity = tripArrivalCity;
	}
	public String getTripFromPlace() {
		return trip.getFromPlace();
	}
	public void setTripFromPlace(String tripFromPlace) {
		this.tripFromPlace = tripFromPlace;
	}
	public String getTripToPlace() {
		return trip.getToPlace();
	}
	public void setTripToPlace(String tripToPlace) {
		this.tripToPlace = tripToPlace;
	}
	public Date getTripDepartureDate() {
		/*
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return dateFormat.format(trip.getDepartureDate());
		*/
		return trip.getDepartureDate();
	}
	public void setTripDepartureDate(Date tripDepartureDate) {
		this.tripDepartureDate = tripDepartureDate;
	}
	public Date getTripDepartureTime() {
		/*
		timeFormat = new SimpleDateFormat("HH:mm");
		return timeFormat.format(trip.getDepartureTime());
		*/
		return trip.getDepartureTime();
	}
	public void setTripDepartureTime(Date tripDepartureTime) {
		this.tripDepartureTime = tripDepartureTime;
	}
	public int getTripAvailableSeats() {
		return trip.getAvailableSeats();
	}
	public void setTripAvailableSeats(int tripAvailableSeats) {
		this.tripAvailableSeats = tripAvailableSeats;
	}
	public float getTripPrice() {
		return trip.getPrice();
	}
	public void setTripPrice(float tripPrice) {
		this.tripPrice = tripPrice;
	}
	public String getTripDescription() {
		return trip.getDescription();
	}
	public void setTripDescription(String tripDescription) {
		this.tripDescription = tripDescription;
	}
	
	public void updateTrip() throws Exception {
		String errorMessage = null;
		Properties properties = System.getProperties();
		Context context = new InitialContext(properties);
		updateTripInformationRemote = (TripAdministrationFacadeRemote) context
				.lookup("java:app/CAT-PDP-GRUP6.jar/TripAdministrationFacadeBean!ejb"
						+ ".TripAdministrationFacadeRemote");
		trip =updateTripInformationRemote.showTrip(tripId);
	}
	
	public String refreshTrip() throws Exception {
		/*
		String errorMessage = null;
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
		// add 1900 to year because starts counting from 1900;
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
			/*	
			ActionEvent event = null;
			String action = (String)event.getComponent().getAttributes().get("action");
			Integer tripId = 113;
			String departureCity = "soller";
			String arrivalCity = "ariany";
			String description = "ok";
			String fromPlace = "ajunt...";
			String toPlace = "ajunt...";
			Date departureDate = null;
			Date departureTime = null;
			Float price = null;
			*/
			updateTripInformationRemote.updateTripInformation(tripId, tripDescription, 
				tripDepartureCity, tripFromPlace, tripDepartureDate, tripDepartureTime, 
				tripArrivalCity, tripToPlace, tripAvailableSeats, tripPrice);
			/*
			this.setDepartureCity("");
			this.setFromPlace("");
			this.setDepartureCity(null);
			this.setDepartureCity(null);
			this.setArrivalCity("");
			this.setFromPlace("");
			this.setAvailableSeats(0);
			this.setPrice(0);
			*/
			return "/pages/public/findTripsView";
		//}
	}
	
	
	
}