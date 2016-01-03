package managedbean;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ejb.TripAdministrationFacadeRemote;
import jpa.CarJPA;
import jpa.TripJPA;
import jpa.UserDTO;

/**
 * UpdateTripInformationMBean
 * 
 * @author GRUP6 jordi-nacho-ximo-joan
 */
@ManagedBean(name = "updateTripInformationController")
@ViewScoped
public class UpdateTripInformationMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private TripAdministrationFacadeRemote updateTripInformationRemote;

	private TripJPA trip;
	private Integer tripId;
	private Collection<CarJPA> cars;
	private UserDTO loggedUser;
	private boolean isEditing;
	private String carSelected;

	public UpdateTripInformationMBean() {
		super();
	}

	public void init() throws NamingException {
		Properties properties = System.getProperties();
		Context context = new InitialContext(properties);
		loggedUser = SessionBean.getLoggedUser();
		updateTripInformationRemote = (TripAdministrationFacadeRemote) context.lookup(
				"java:app/CAT-PDP-GRUP6.jar/TripAdministrationFacadeBean!ejb" + ".TripAdministrationFacadeRemote");
		if (tripId != null) {
			isEditing = true;
			trip = updateTripInformationRemote.getTrip(tripId);
			cars = updateTripInformationRemote.getMyCars(loggedUser.getId());
		} else {
			isEditing = false;
		}
	}

	/**
	 * Returns parameter trip.
	 * 
	 * @return trip.
	 */
	public TripJPA getTrip() {
		return trip;
	}

	/**
	 * Sets parameter trip.
	 * 
	 * @param trip.
	 */
	public void setTrip(TripJPA trip) {
		this.trip = trip;
	}

	/**
	 * Returns parameter tripId.
	 * 
	 * @return tripId.
	 */
	public Integer getTripId() {
		return tripId;
	}

	/**
	 * Sets parameter tripId.
	 * 
	 * @param tripId.
	 */
	public void setTripId(Integer tripId) {
		this.tripId = tripId;
	}

	/**
	 * Returns parameter tripDepartureCity.
	 * 
	 * @return tripDepartureCity.
	 */
	public String getDepartureCity() {
		return trip.getDepartureCity();
	}

	/**
	 * Sets parameter tripDeparturecity.
	 * 
	 * @param tripDepartureCity.
	 */
	public void setDepartureCity(String departureCity) {
		trip.setDepartureCity(departureCity);
	}

	/**
	 * Returns parameter tripArrivalCity.
	 * 
	 * @return tripArrivalCity.
	 */
	public String getArrivalCity() {
		return trip.getArrivalCity();
	}

	/**
	 * Sets parameter tripArrivalCity.
	 * 
	 * @param tripArrivalCity.
	 */
	public void setArrivalCity(String arrivalCity) {
		this.trip.setArrivalCity(arrivalCity);
	}

	/**
	 * Returns parameter tripFromPlace.
	 * 
	 * @return tripFromPlace.
	 */
	public String getFromPlace() {
		return trip.getFromPlace();
	}

	/**
	 * Sets parameter tripFromPlace.
	 * 
	 * @param tripFromPlace.
	 */
	public void setFromPlace(String fromPlace) {
		this.trip.setFromPlace(fromPlace);
	}

	/**
	 * Returns parameter tripToPlace.
	 * 
	 * @return tripToPlace.
	 */
	public String getToPlace() {
		return trip.getToPlace();
	}

	/**
	 * Sets parameter tripToPlace.
	 * 
	 * @param tripToPlace.
	 */
	public void setToPlace(String toPlace) {
		this.trip.setToPlace(toPlace);
	}

	/**
	 * Returns parameter tripDepartureDate.
	 * 
	 * @return tripDepartureDate.
	 */
	public Date getDepartureDate() {
		return trip.getDepartureDate();
	}

	/**
	 * Sets parameter tripDepartureDate.
	 * 
	 * @param tripDepartureDate.
	 */
	public void setDepartureDate(Date departureDate) {
		this.trip.setDepartureDate(departureDate);
	}

	/**
	 * Returns parameter tripDepartureTime.
	 * 
	 * @return tripDepartureTime.
	 */
	public Date getDepartureTime() {
		return trip.getDepartureTime();
	}

	/**
	 * Sets parameter tripDeparturetime.
	 * 
	 * @param tripDepartureTime.
	 */
	public void setDepartureTime(Date departureTime) {
		this.trip.setDepartureTime(departureTime);
	}

	/**
	 * Returns parameter tripAvailableSeats.
	 * 
	 * @param tripAvailableSeats.
	 */
	public int getAvailableSeats() {
		return trip.getAvailableSeats();
	}

	/**
	 * Sets tripAvailableSeats.
	 * 
	 * @param tripAvailableSeats.
	 */
	public void setAvailableSeats(int availableSeats) {
		this.trip.setAvailableSeats(availableSeats);
	}

	/**
	 * Returns parameter tripPrice.
	 * 
	 * @return tripPrice.
	 */
	public float getPrice() {
		return trip.getPrice();
	}

	/**
	 * Sets parameter tripPrice.
	 * 
	 * @param tripPrice.
	 */
	public void setPrice(float price) {
		this.trip.setPrice(price);
	}

	/**
	 * Returns parameter tripDescription.
	 * 
	 * @return tripDescription.
	 */
	public String getDescription() {
		return trip.getDescription();
	}

	/**
	 * Sets parameter tripDescription.
	 * 
	 * @param tripDescription.
	 */
	public void setDescription(String description) {
		this.trip.setDescription(description);
	}

	/**
	 * Returns parameter carSelected.
	 * 
	 * @return carSelected.
	 */
	public String getCarSelected() {
		return this.carSelected;
	}

	/**
	 * Sets parameter carSelected.
	 * 
	 * @param carSelected.
	 */
	public void setCarSelected(String carSelected) {
		this.carSelected = carSelected;
	}

	/**
	 * Gets a list of cars owned by the logged driver. Cars are shown on a
	 * selectMenu for the driver to select one of driver's cars.
	 * 
	 * @return ArrayList&lt;String&gt;.
	 * @throws Exception
	 */
	public Collection<CarJPA> getCars() {
		return this.cars;
	}

	/**
	 * Method that calls the remote interface to persist car's updates.
	 * 
	 * @return initial page.
	 * @throws Exception
	 */
	public String refreshTrip() throws Exception {

		updateTripInformationRemote.updateTripInformation(tripId, getDescription(), getDepartureCity(), getFromPlace(),
				getDepartureDate(), getDepartureTime(), getArrivalCity(), getToPlace(), getAvailableSeats(), getPrice(),
				carSelected);

		return "/pages/public/findTripsView";
	}

	public boolean getIsEditing() {
		return isEditing;
	}
}
