package ejb;

import java.sql.Time;
import java.util.Date;

import javax.ejb.Remote;

@Remote
public interface TripAdministrationFacadeRemote {

	public void addTrip(String departureCity, String fromPlace, Date departureDate, Date departureTime,
			String arrivalCity, String toPlace, Integer availableSeats, float price, String description);
}
