package ejb;

import java.util.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.Remote;
import jpa.TripJPA;
import jpa.CarJPA;
import jpa.DriverJPA;
import jpa.PassengerJPA;

/**
 * TripAdministrationFacadeRemote
 * @author GRUP6 -jordi-nacho-ximo-joan-
 */
@Remote
public interface TripAdministrationFacadeRemote {
	public Collection<TripJPA> findMyTrips(String driver);
	void addTrip(String description, String departureCity, String fromPlace, Date departureDate, Date departureTime, String arrivalCity, String toPlace, int availableSeats, float price, String nif, String selectedCar);
	public Collection<PassengerJPA> findAllPassengers(int tripId);
	public void updateTripInformation(Integer tripId, String description, String departureCity, String fromPlace, Date departureDate, Date departureTime, String arrivalCity, String toPlace, Integer availableSeats, Float price);
	public TripJPA showTrip(Integer tripId);
	public Collection<CarJPA> getMyCars(String driverId);
}