package ejb;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import jpa.TripJPA;

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
	 * @param departureCity the departure city of the trip
	 * @param departureDate the departure date of the trip
	 * @param arrivalCity the arrival city of the trip
	 * @return a list of trips or an empty list
	 */
	List<TripJPA> findTrips(String departureCity, Date departureDate, String arrivalCity);

	/**
	 * Find the trip with the given id 
	 * @param id the id of the trip to be found
	 * @return {{@link TripJPA} the trip
	 */
	TripJPA showTrip(Integer id);

	/**
	 * Registers a user in a trip
	 * @param userId the id of the user to be registered
	 * @param tripId the id of the trip where the user has to be registered
	 */
	void registerInTrip(String userId, Integer tripId);

	/**
	 * Removes a user from a trip
	 * @param userId the id of the user to be removed
	 * @param tripId the id of the trip where the user has to be removed
	 */
	void removeFromTrip(String userId, Integer tripId);
}
