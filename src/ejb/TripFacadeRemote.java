package ejb;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import jpa.TripJPA;

/**
 * Interface to provide the methods to manage the trips
 * 
 * @author Ignacio González Bullón <nachoglezbul@uoc.edu>
 *
 */
@Remote
public interface TripFacadeRemote {

	/**
	 * Find the trips that match the given conditions
	 * 
	 * @param departureCity
	 * @param departureDate
	 * @param arrivalCity
	 * @return a list of trips or an empty list
	 */
	List<TripJPA> findTrips(String departureCity, Date departureDate, String arrivalCity);

	TripJPA showTrip(Integer id);
}
