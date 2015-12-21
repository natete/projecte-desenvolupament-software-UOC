package ejb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
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
	public static final String QUERY_GET_DRIVER_BY_NIF = "findMyDriver";
	public static final String QUERY_GET_CARS_BY_DRIVER = "CarJPA.findCarsByDriverId";
	public static final String PARAMETER_CARS_DRIVER = "driver";
	public static final String PARAMETER_DRIVER_NIF = "driverNif";
	public static final String PARAMETER_DRIVER_NIF1 = "nif";
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
	@PersistenceContext(unitName = "CarSharing")
	private EntityManager entman;
	private String carSelected;

	/**
	 * findMyTrips
	 */
	@Override
	public Collection<TripJPA> findMyTrips(String driverNif) {

		Collection<TripJPA> myTrips = null;
		try {
		myTrips = (Collection<TripJPA>) entman.createNamedQuery(QUERY_FIND_TRIPS_BY_DRIVER)
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
			Date departureTime, String arrivalCity, String toPlace, int availableSeats, float price, String nif, String selectedCar) {
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
			driverJPA = (DriverJPA) entman.createNamedQuery("findMyDriver").setParameter("nif", nif)
					.getSingleResult();
		} catch (PersistenceException e) {
				System.out.println(e);
			}
		trip.setDriver(driverJPA);
		
		String[] tokens = selectedCar.split(" ");
		String brand = tokens[0];
		String model = tokens[1];
		String colour = tokens[2];
		CarJPA myCar = (CarJPA) entman.createNamedQuery("CarJPA.findCarByBrandModelColour")
				.setParameter("brand", brand).setParameter("model", model)
				.setParameter("colour", colour).getSingleResult();
		trip.setCar(myCar);
		try {
			entman.persist(trip);

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
			trip = (TripJPA) entman.createNamedQuery(QUERY_GET_TRIP_BY_ID)
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
	public void updateTripInformation(Integer tripId, String description, String departureCity, String fromPlace,
			Date departureDate, Date departureTime, String arrivalCity, String toPlace, Integer availableSeats,
			Float price) {
		// TODO Auto-generated method stub		

		try {
			entman.createNamedQuery(QUERY_UPDATE_TRIP)
			.setParameter(PARAMETER_TRIP_DESCRIPTION, description)
			.setParameter(PARAMETER_TRIP_DEPARTURE_CITY, departureCity)
			.setParameter(PARAMETER_TRIP_FROM_PLACE, fromPlace)
			.setParameter(PARAMETER_TRIP_DEPARTURE_DATE, departureDate)
			.setParameter(PARAMETER_TRIP_DEPARTURE_TIME, departureTime)
			.setParameter(PARAMETER_TRIP_ARRIVAL_CITY, arrivalCity)
			.setParameter(PARAMETER_TRIP_TO_PLACE, toPlace)
			.setParameter(PARAMETER_TRIP_AVAILABLE_SEATS, availableSeats)
			.setParameter(PARAMETER_TRIP_PRICE, price)
			.setParameter(PARAMETER_TRIP_ID, tripId)
			.executeUpdate();
		} catch (PersistenceException e) {
			System.out.println(e);
		}
	}
	
	public TripJPA showTrip(Integer tripId) {
		Query query = entman.createNamedQuery(QUERY_GET_TRIP_BY_ID);
		query.setParameter(PARAMETER_TRIP_ID, tripId);
		return (TripJPA) query.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	public Collection<CarJPA> getMyCars(String driverId) {
		Collection<CarJPA> myCars = null;
		DriverJPA driverJPA = (DriverJPA) entman.createNamedQuery(QUERY_GET_DRIVER_BY_NIF)
				.setParameter(PARAMETER_DRIVER_NIF1, driverId).getSingleResult();
		try {
			myCars = entman.createNamedQuery(QUERY_GET_CARS_BY_DRIVER)
					.setParameter(PARAMETER_CARS_DRIVER, driverJPA).getResultList();
		} catch (PersistenceException e) {
			System.out.println(e);
		}
		return myCars;
	}
}