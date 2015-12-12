package ejb;

import java.util.Date;
import java.sql.Time;
import java.util.Collection;
import javax.ejb.Remote;
import jpa.TripJPA;
import jpa.PassengerJPA;

/**
 * TripAdministrationFacadeRemote
 * @author GRUP6 -jordi-nacho-ximo-joan-
 */
@Remote
public interface TripAdministrationFacadeRemote {
	/**
	 * INTERFACE METHODS
	 */
	public Collection<TripJPA> findMyTrips(String driver);
	public void addTrip(String description, String departureCity, String fromPlace, Date departureDate, Date departureTime, String arrivalCity, String toPlace, int availableSeats, float price);
	public Collection<PassengerJPA> findAllPassengers(int tripId);
	public void updateTripInformation(int tripId, String description, String departureCity, String fromPlace, Date departureDate, Date departureTime, String arrivalCity, String toPlace, int availableSeats, float price);
}
