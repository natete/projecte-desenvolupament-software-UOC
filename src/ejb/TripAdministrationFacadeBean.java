package ejb;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import jpa.CarJPA;
import jpa.DriverJPA;
import jpa.PassengerJPA;
import jpa.TripJPA;

/**
 * TripAdministrationFacadeBean
 * @author GRUP6 -jordi-nacho-ximo-joan-
 */
@Stateless
public class TripAdministrationFacadeBean implements TripAdministrationFacadeRemote {

	public static final String BLANK_SPACE = " ";
	public static final String QUERY_FIND_TRIPS_BY_DRIVER = "TripJPA.findTripsByDriver";
	public static final String QUERY_GET_TRIP_BY_ID = "TripJPA.getTripById";
	public static final String QUERY_UPDATE_TRIP = "TripJPA.updateTrip";
	public static final String PARAMETER_DRIVER_NIF = "driverNif";
	public static final String PARAMETER_TRIP_ID = "tripId";
	public static final String PARAMETER_TRIP_DESCRIPTION = "description";
	public static final String PARAMETER_TRIP_DEPARTURE_CITY = "departureCity";
	public static final String PARAMETER_TRIP_FROM_PLACE = "fromPlace";
	public static final String PARAMETER_TRIP_DEPARTURE_DATE = "departureDate";
	public static final String PARAMETER_TRIP_DEPARTURE_TIME = "departureTime";
	public static final String PARAMETER_TRIP_ARRIVAL_CITY = "arrivalCity";
	public static final String PARAMETER_TRIP_TO_PLACE = "toPlace";
	public static final String PARAMETER_TRIP_AVAILABLE_SEATS = "availableSeats";
	public static final String PARAMETER_TRIP_PRICE = "price";
	
	/**
	 * PERSISTENCE CONTEXT
	 */
	@PersistenceContext(unitName = "CarSharing")
	private EntityManager entityManager;

	/**
	 * INTERFACE IMPLEMENTATION
	 */

	/**
	 * findMyTrips
	 */
	@Override
	public Collection<TripJPA> findMyTrips(String driverNif) {

		Collection<TripJPA> myTrips = null;
		try {
		myTrips = (Collection<TripJPA>) entityManager.createNamedQuery(QUERY_FIND_TRIPS_BY_DRIVER)
				.setParameter(PARAMETER_DRIVER_NIF, driverNif).getResultList();
		} catch (PersistenceException e) {
			System.out.println(e);
		}
		return myTrips;
	}

	/**
	 * addTrip
	 * 
	 */
	@Override
	public void addTrip(String description, String departureCity, String fromPlace, Date departureDate,
			Date departureTime, String arrivalCity, String toPlace, int availableSeats, float price, String nif) {
		// TODO Auto-generated method stub

		TripJPA trip = new TripJPA();
		trip.setDescription(description);
		trip.setDepartureCity(departureCity);
		trip.setFromPlace(fromPlace);
		trip.setDepartureDate(departureDate);
		trip.setDepartureTime(departureTime);
		trip.setArrivalCity(arrivalCity);
		trip.setToPlace(toPlace);
		trip.setAvailableSeats(availableSeats);
		trip.setPrice(price);
		
		DriverJPA driverJPA = null;
		try {
			driverJPA = (DriverJPA) entityManager.createNamedQuery("findMyDriver").setParameter("nif", nif)
					.getSingleResult();
		} catch (PersistenceException e) {
				System.out.println(e);
			}
		trip.setDriver(driverJPA);

		Collection<CarJPA> myCars = driverJPA.getCars();
		Iterator<CarJPA> it = myCars.iterator();
		CarJPA myCar = null;
		while (it.hasNext())
			myCar = it.next();
		trip.setCar(myCar);

		try {
			entityManager.persist(trip);

		} catch (PersistenceException e) {
			System.out.println(e);
		}
	}

	/**
	 * findAllPassengers
	 */
	@Override
	public List<PassengerJPA> findAllPassengers(int tripId) {
		// TODO Auto-generated method stub
		
		TripJPA trip = null;
		try {
			trip = (TripJPA) entityManager.createNamedQuery(QUERY_GET_TRIP_BY_ID)
					.setParameter(PARAMETER_TRIP_ID, tripId).getSingleResult();
		} catch (PersistenceException e) {
			System.out.println(e);
		}
		List<PassengerJPA> passengers = trip.getPassengers(); 
		return passengers;
	}

	/**
	 * updateTripInformation
	 */
	@Override
	public void updateTripInformation(int tripId, String description, String departureCity, String fromPlace,
			Date departureDate, Date departureTime, String arrivalCity, String toPlace, int availableSeats,
			float price) {
		// TODO Auto-generated method stub
		
		try {
			entityManager.createNamedQuery(QUERY_UPDATE_TRIP)
			.setParameter(PARAMETER_TRIP_ID, tripId);
		} catch (PersistenceException e) {
			System.out.println(e);
		}
	}
}
