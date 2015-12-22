package managedbean;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
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
import jpa.CarJPA;
import jpa.TripJPA;
import jpa.UserDTO;

@ManagedBean(name = "updateTripInformationController")
@ViewScoped
public class UpdateTripInformationMBean implements Serializable {
	
	private static final long serialVersionUID = 1L;	
	@EJB
	private TripAdministrationFacadeRemote updateTripInformationRemote;	
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
	private Collection<CarJPA> cars;
	private String carSelected;
	public UpdateTripInformationMBean() {
		
	}
	
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
		return trip.getDepartureDate();
	}
	public void setTripDepartureDate(Date tripDepartureDate) {
		this.tripDepartureDate = tripDepartureDate;
	}
	public Date getTripDepartureTime() {
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
	public String getCarSelected() {
		return carSelected;
	}
	public void setCarSelected(String carSelected) {
		this.carSelected = carSelected;
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
	
	public ArrayList<String> getCars() throws Exception {
		UserDTO userDTO = SessionBean.getLoggedUser();
		String driverId = userDTO.getId();
		Properties properties = System.getProperties();
		Context context = new InitialContext(properties);
		updateTripInformationRemote = (TripAdministrationFacadeRemote) context.lookup("java:app/CAT-PDP-GRUP6.jar/TripAdministrationFacadeBean!ejb.TripAdministrationFacadeRemote");
		cars = null;
		cars = updateTripInformationRemote.getMyCars(driverId);
		ArrayList<String> brands = new ArrayList<String>();
		Iterator<CarJPA> it = cars.iterator();
		while (it.hasNext()) {
			CarJPA car = it.next();
			brands.add(car.getBrand()+" "+car.getModel()+" "+car.getColor());
		}
		return brands;
	}
	
	public String refreshTrip() throws Exception {
		
			updateTripInformationRemote.updateTripInformation(tripId, tripDescription, 
				tripDepartureCity, tripFromPlace, tripDepartureDate, tripDepartureTime, 
				tripArrivalCity, tripToPlace, tripAvailableSeats, tripPrice, carSelected);
			
			return "/pages/public/findTripsView";
	}
	
	
	
}