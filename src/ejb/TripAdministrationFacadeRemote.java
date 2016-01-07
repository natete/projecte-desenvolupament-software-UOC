package ejb;

import java.util.Collection;
import java.util.Date;

import javax.ejb.Remote;

import jpa.CarJPA;
import jpa.PassengerJPA;
import jpa.TripJPA;
import jpa.TripsDTO;

/**
 * TripAdministrationFacadeRemote
 * 
 * @author GRUP6 jordi-nacho-ximo-joan
 */
@Remote
public interface TripAdministrationFacadeRemote {

	/**
	 * Gets a collection of trips owned by the logged driver.
	 * 
	 * @param driver, the id of a driver.
	 * @return {@link Collection} of {@link TripJPA} owned by the
	 */
	public Collection<TripJPA> findMyTrips(String driver);

	public TripsDTO findMyTrips(String driver, int page);

	/**
	 * Adds a trip to the system upon the given parameters.
	 * 
	 * @param description, the description of the trip.
	 * @param departureCity, the departure city of the trip.
	 * @param fromPlace, the pickup place of the trip.
	 * @param departureDate, the departure date of the trip.
	 * @param departureTime, the departure time of the trip.
	 * @param arrivalCity, the arrival city of the trip.
	 * @param toPlace, the drop off place of the trip.
	 * @param availableSeats, the available seats of the trip.
	 * @param price, the price of the trip.
	 * @param nif, the id of owner's trip.
	 * @param selectedCar, the car selected of owner's trip.
	 */
	public void addTrip(String description, String departureCity, String fromPlace, Date departureDate,
			Date departureTime, String arrivalCity, String toPlace, Integer availableSeats, Float price, String nif,
			String selectedCar);

	/**
	 * Gets a list of passengers of a trip, given the trip id. The list of
	 * passenger is formatted as a collection.
	 * 
	 * @param tripId, the trip id.
	 * @return {@link Collection} of {@link PassengerJPA}
	 */
	public Collection<PassengerJPA> findAllPassengers(int tripId);

	/**
	 * Updates a trip upon the trip id and the given parameters.
	 * 
	 * @param tripId, trip id of the trip.
	 * @param description, the description of the trip.
	 * @param departureCity, the departure city of the trip.
	 * @param fromPlace, the pickup place of the trip.
	 * @param departureDate, the departure date of the trip.
	 * @param departureTime, the departure time of the trip.
	 * @param arrivalCity, the arrival city of the trip.
	 * @param toPlace, the drop off place of the trip.
	 * @param availableSeats, the available seats of the trip.
	 * @param price, the price of the trip.
	 * @param carSelected, the car selected of owner's trip.
	 */
	public void updateTripInformation(Integer tripId, String description, String departureCity, String fromPlace,
			Date departureDate, Date departureTime, String arrivalCity, String toPlace, Integer availableSeats,
			Float price, String carSelected);

	public TripJPA getTrip(Integer tripId);

	public Collection<CarJPA> getMyCars(String driverId);

	public boolean deleteTrip(int tripId);
}
