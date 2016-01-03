package ejb;

import java.util.Date;

import javax.ejb.Remote;

import jpa.TripJPA;
import jpa.TripsDTO;

/**
 * Interface to provide the methods to manage the trips
 * 
 * @author Ignacio González Bullón - nachoglezbul@uoc.edu
 *
 */
@Remote
public interface TripFacadeRemote {

	/**
	 * Find the trips that match the given conditions
	 * 
	 * @param departureCity
	 *            the departure city of the trip
	 * @param departureDate
	 *            the departure date of the trip
	 * @param arrivalCity
	 *            the arrival city of the trip
	 * @param page
	 *            number of required page for pagination
	 * @return a list of trips or an empty list
	 */
	TripsDTO findTrips(String departureCity, Date departureDate, String arrivalCity, int page);

	/**
	 * Find the trip with the given id
	 * 
	 * @param id
	 *            the id of the trip to be found
	 * @return {{@link TripJPA} the trip
	 */
	TripJPA showTrip(Integer id);

	/**
	 * Registers a user in a trip
	 * 
	 * @param userId
	 *            the id of the user to be registered
	 * @param tripId
	 *            the id of the trip where the user has to be registered
	 */
	void registerInTrip(String userId, Integer tripId);

	/**
	 * Removes a user from a trip
	 * 
	 * @param userId
	 *            the id of the user to be removed
	 * @param tripId
	 *            the id of the trip where the user has to be removed
	 */
	void removeFromTrip(String userId, Integer tripId);

	/**
	 * Find the trips that match the given conditions
	 * 
	 * @param departureCity
	 *            the departure city of the trip
	 * @param arrivalCity
	 *            the arrival city of the trip
	 * @param initialDate
	 *            the initial departure date of the trip to look for
	 * @param finalDate
	 *            the final departure date of the trip to look for
	 * @param minPrice
	 *            the minimum price
	 * @param maxPrice
	 *            the maximum price
	 * @param hasSeats
	 *            restrict to trips with available seats
	 * @param page
	 *            number of required page for pagination
	 * @return @param page the page of the search
	 */
	TripsDTO advancedSearch(String departureCity, String arrivalCity, Date initialDate, Date finalDate, float minPrice,
			float maxPrice, boolean hasSeats, int i);
}
