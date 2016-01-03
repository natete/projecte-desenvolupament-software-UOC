package managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.Context;
import javax.naming.InitialContext;

import ejb.TripAdministrationFacadeBean;
import ejb.TripAdministrationFacadeRemote;
import jpa.CarJPA;
import jpa.TripJPA;
import jpa.UserDTO;

/**
 * UpdateTripInformationMBean
 * @author GRUP6 jordi-nacho-ximo-joan
 */
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
	private TripJPA trip;
	private Collection<CarJPA> cars;
	private String carSelected;
	
	
	/**
	 * AddTripMBean Default Constructor.
	 */
	public UpdateTripInformationMBean() {
		
	}
	
	
	/**
	 * Returns parameter trip.
	 * @return trip.
	 */
	public TripJPA getTrip() {
		return trip;
	}
	
	/**
	 * Sets parameter trip.
	 * @param trip.
	 */
	public void setTrip(TripJPA trip) {
		this.trip = trip;
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
	 * Returns parameter tripDepartureCity.
	 * @return tripDepartureCity.
	 */
	public String getTripDepartureCity() {
		return trip.getDepartureCity();
	}
	
	/**
	 * Sets parameter tripDeparturecity.
	 * @param tripDepartureCity.
	 */
	public void setTripDepartureCity(String tripDepartureCity) {
		this.tripDepartureCity = tripDepartureCity;
	}
	
	/**
	 * Returns parameter tripArrivalCity.
	 * @return tripArrivalCity.
	 */
	public String getTripArrivalCity() {
		return trip.getArrivalCity();
	}
	
	/**
	 * Sets parameter tripArrivalCity.
	 * @param tripArrivalCity.
	 */
	public void setTripArrivalCity(String tripArrivalCity) {
		this.tripArrivalCity = tripArrivalCity;
	}
	
	/**
	 * Returns parameter tripFromPlace.
	 * @return tripFromPlace.
	 */
	public String getTripFromPlace() {
		return trip.getFromPlace();
	}
	
	/**
	 * Sets parameter tripFromPlace.
	 * @param tripFromPlace.
	 */
	public void setTripFromPlace(String tripFromPlace) {
		this.tripFromPlace = tripFromPlace;
	}
	
	/**
	 * Returns parameter tripToPlace.
	 * @return tripToPlace.
	 */
	public String getTripToPlace() {
		return trip.getToPlace();
	}
	
	/**
	 * 
	 * Sets parameter tripToPlace.
	 * @param tripToPlace.
	 */
	public void setTripToPlace(String tripToPlace) {
		this.tripToPlace = tripToPlace;
	}
	
	/**
	 * Returns parameter tripDepartureDate.
	 * @return tripDepartureDate.
	 */
	public Date getTripDepartureDate() {
		return trip.getDepartureDate();
	}
	
	/**
	 * Sets parameter tripDepartureDate.
	 * @param tripDepartureDate.
	 */
	public void setTripDepartureDate(Date tripDepartureDate) {
		this.tripDepartureDate = tripDepartureDate;
	}
	
	/**
	 * Returns parameter tripDepartureTime.
	 * @return tripDepartureTime.
	 */
	public Date getTripDepartureTime() {
		return trip.getDepartureTime();
	}
	
	/**
	 * Sets parameter tripDeparturetime.
	 * @param tripDepartureTime.
	 */
	public void setTripDepartureTime(Date tripDepartureTime) {
		this.tripDepartureTime = tripDepartureTime;
	}
	
	/**
	 * Returns parameter tripAvailableSeats.
	 * @param tripAvailableSeats.
	 */
	public int getTripAvailableSeats() {
		return trip.getAvailableSeats();
	}
	
	/**
	 * Sets tripAvailableSeats.
	 * @param tripAvailableSeats.
	 */
	public void setTripAvailableSeats(int tripAvailableSeats) {
		this.tripAvailableSeats = tripAvailableSeats;
	}
	
	/**
	 * Returns parameter tripPrice.
	 * @return tripPrice.
	 */
	public float getTripPrice() {
		return trip.getPrice();
	}
	
	/**
	 * Sets parameter tripPrice.
	 * @param tripPrice.
	 */
	public void setTripPrice(float tripPrice) {
		this.tripPrice = tripPrice;
	}
	
	/**
	 * Returns parameter tripDescription.
	 * @return tripDescription.
	 */
	public String getTripDescription() {
		return trip.getDescription();
	}
	
	/**
	 * Sets parameter tripDescription.
	 * @param tripDescription.
	 */
	public void setTripDescription(String tripDescription) {
		this.tripDescription = tripDescription;
	}
	
	/**
	 * Returns parameter carSelected.
	 * @return carSelected.
	 */
	public String getCarSelected() {
		return carSelected;
	}
	
	/**
	 * Sets parameter carSelected.
	 * @param carSelected.
	 */
	public void setCarSelected(String carSelected) {
		this.carSelected = carSelected;
	}
	
	
	/**
	 * Gets a trip from trip table upon the 
	 * tripId given parameter. The result 
	 * is put into trip variable.
	 * @throws Exception
	 */
	public void updateTrip() throws Exception {
		Properties properties = System.getProperties();
		Context context = new InitialContext(properties);
		updateTripInformationRemote = (TripAdministrationFacadeRemote) context
				.lookup("java:app/CAT-PDP-GRUP6.jar/TripAdministrationFacadeBean!ejb"
						+ ".TripAdministrationFacadeRemote");
		if (tripId != null) {
		trip = updateTripInformationRemote.showTrip(tripId);
		tripDepartureCity = trip.getDepartureCity();
		tripArrivalCity = trip.getArrivalCity();
		tripDepartureDate = trip.getDepartureDate();
		}
	}
	
	/**
	 * Gets a list of cars owned by the 
	 * logged driver. Cars are shown on 
	 * a selectMenu for the driver to 
	 * select one of driver's cars.
	 * @return ArrayList&lt;String&gt;.
	 * @throws Exception
	 */
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
	
	/**
	 * Method that calls the remote interface 
	 * to persist car's updates.
	 * @return initial page.
	 * @throws Exception
	 */
	public String refreshTrip() throws Exception {
		
			updateTripInformationRemote.updateTripInformation(tripId, tripDescription, 
				tripDepartureCity, tripFromPlace, tripDepartureDate, tripDepartureTime, 
				tripArrivalCity, tripToPlace, tripAvailableSeats, tripPrice, carSelected);
			
			return "/pages/public/findTripsView";
	}
}
