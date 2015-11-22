package ejb;

import java.sql.Time;
import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import jpa.TripJPA;

@Stateless
public class TripAdministrationFacadeBean implements TripAdministrationFacadeRemote {

	@PersistenceContext(unitName = "CarSharing")
	private EntityManager entman;

	@Override
	public void addTrip(String departureCity, String fromPlace, Date departureDate, Date departureTime,
			String arrivalCity, String toPlace, Integer availableSeats, float price, String description) {

		TripJPA trip = new TripJPA(description, departureCity, fromPlace, departureDate, departureTime, arrivalCity,
				toPlace, availableSeats, price);
		
		try {
			entman.persist(trip);
		} catch (PersistenceException e) {
			System.out.println(e);
		}

	}

}
