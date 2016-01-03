package ejb;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.transaction.Transactional;

import jpa.CarJPA;
import jpa.DriverJPA;
import jpa.PassengerJPA;
import jpa.TripJPA;
import jpa.TripsDTO;

/**
 * TripAdministrationFacadeBean
 * 
 * @author GRUP6 jordi-nacho-ximo-joan
 */
@Stateless
public class TripAdministrationFacadeBean implements TripAdministrationFacadeRemote {

	private static final String QUERY_FIND_TRIPS_BY_DRIVER = "TripJPA.findTripsByDriver";
	private static final String QUERY_GET_TRIP_BY_ID = "TripJPA.getTripById";
	private static final String QUERY_FIND_DRIVER = "DriverJPA.getByNif";
	private static final String QUERY_GET_CARS_BY_DRIVER = "CarJPA.findCarsByDriverId";
	private static final String QUERY_COUNT_TRIPS_BY_DRIVER = "TripJPA.countTripJPA";
	private static final String QUERY_FIND_CAR_BY_ID = "CarJPA.findCarById";
	private static final String PARAMETER_DRIVER_NIF = "nif";
	private static final String PARAMETER_TRIP_ID = "tripId";
	private static final String PARAMETER_CAR_ID = "carRegistrationId";
	private static final int PAGE_SIZE = 10;
	private static final int MINIMUM_DAYS_REQUIRED = 2;

	@PersistenceContext(unitName = "CarSharing")
	private EntityManager entman;

	/**
	 * Returns a collection of trips from carsharing.trip owned by a logged
	 * driver.
	 * 
	 * @notation This method is no longer in use.
	 * @return Collection&lt;TripJPA&gt;.
	 * @see findMyTrips(String driver, int page).
	 */
	@Override
	@SuppressWarnings("unchecked")
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
	 * This method makes queries to trips table by paging methods.
	 * 
	 * @return TripsDTO trips.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public TripsDTO findMyTrips(String driver, int page) {

		Query query = entman.createNamedQuery(QUERY_FIND_TRIPS_BY_DRIVER).setParameter(PARAMETER_DRIVER_NIF, driver)
				.setFirstResult(page * PAGE_SIZE).setMaxResults(PAGE_SIZE);
		TripsDTO trips = new TripsDTO();
		trips.setTrips(query.getResultList());

		trips.setTotal(countMyTrips(driver));
		return trips;
	}

	private Long countMyTrips(String driverNif) {
		Long totalResults = (Long) entman.createNamedQuery(QUERY_COUNT_TRIPS_BY_DRIVER)
				.setParameter(PARAMETER_DRIVER_NIF, driverNif).getSingleResult();
		return totalResults;
	}

	/**
	 * Add a trip to carsharing.trps table. The method gets the parameters from
	 * the managed bean, then gets the driver id to add it to the trip entity,
	 * and finally persists the trip via entity manager.
	 */
	@Override
	public void addTrip(String description, String departureCity, String fromPlace, Date departureDate,
			Date departureTime, String arrivalCity, String toPlace, int availableSeats, float price, String nif,
			String selectedCar) {

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

		DriverJPA driver = getDriver(nif);

		if (driver == null) {
			throw new IllegalArgumentException("The selected driver is not registered");
		}

		trip.setDriver(driver);

		CarJPA car = findCar(selectedCar);

		if (car == null) {
			throw new IllegalArgumentException("The selected car is not registered");
		}

		try {
			entman.persist(trip);
		} catch (PersistenceException e) {
			System.out.println(e);
		}
	}

	private DriverJPA getDriver(String driverId) {
		DriverJPA driver;
		Query query = entman.createNamedQuery(QUERY_FIND_DRIVER);
		query.setParameter(PARAMETER_DRIVER_NIF, driverId);
		try {
			driver = (DriverJPA) query.getSingleResult();
		} catch (NoResultException e) {
			driver = null;
		}
		return driver;
	}

	/**
	 * Sends a query to carsharing.trip upon tripId parameter to get the
	 * specified trip. Then calls getPassengers() method from TripJPA to get all
	 * passengers of that specified trip.
	 * 
	 * @return List&lt;PassengerJPA&gt;.
	 */
	@Override
	public List<PassengerJPA> findAllPassengers(int tripId) {

		TripJPA trip = null;
		trip = (TripJPA) entman.createNamedQuery(QUERY_GET_TRIP_BY_ID).setParameter(PARAMETER_TRIP_ID, tripId)
				.getSingleResult();
		List<PassengerJPA> passengers = trip.getPassengers();
		return passengers;
	}

	/**
	 * Updates a given trip upon its id and the parameters gotten from the
	 * managed bean. The method first has to split the carSelected string to get
	 * the brand, model and color parameters. Then calls a query to get the
	 * given car. Finally, executes the update.
	 */
	@Override
	public void updateTripInformation(Integer tripId, String description, String departureCity, String fromPlace,
			Date departureDate, Date departureTime, String arrivalCity, String toPlace, Integer availableSeats,
			Float price, String selectedCar) {

		CarJPA car = findCar(selectedCar);

		if (car == null) {
			throw new IllegalArgumentException("The selected car is not registered");
		} else {
			TripJPA trip = getTrip(tripId);
			trip.setDescription(description);
			trip.setDepartureCity(departureCity);
			trip.setFromPlace(fromPlace);
			trip.setDepartureDate(departureDate);
			trip.setDepartureTime(departureTime);
			trip.setArrivalCity(arrivalCity);
			trip.setToPlace(toPlace);
			trip.setAvailableSeats(availableSeats);
			trip.setPrice(price);
			trip.setCar(car);

			entman.persist(trip);
		}
	}

	private CarJPA findCar(String carId) {
		CarJPA car;
		Query query = entman.createNamedQuery(QUERY_FIND_CAR_BY_ID);
		query.setParameter(PARAMETER_CAR_ID, carId);
		try {
			car = (CarJPA) query.getSingleResult();
		} catch (NoResultException e) {
			car = null;
		}
		return car;
	}

	public TripJPA getTrip(Integer id) {
		TripJPA trip;
		Query query = entman.createNamedQuery(QUERY_GET_TRIP_BY_ID);
		query.setParameter(PARAMETER_TRIP_ID, id);
		try {
			trip = (TripJPA) query.getSingleResult();
		} catch (NoResultException e) {
			trip = null;
		}
		return trip;
	}

	@SuppressWarnings("unchecked")
	public Collection<CarJPA> getMyCars(String driverId) {
		Collection<CarJPA> myCars = null;
		try {
			myCars = entman.createNamedQuery(QUERY_GET_CARS_BY_DRIVER).setParameter(PARAMETER_DRIVER_NIF, driverId)
					.getResultList();
		} catch (PersistenceException e) {
			System.out.println(e);
		}
		return myCars;
	}

	@Transactional
	public void deleteTrip(int tripId) {
		TripJPA trip = entman.find(TripJPA.class, tripId);
		Calendar depDate = Calendar.getInstance();
		depDate.setTime(trip.getDepartureDate());
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, MINIMUM_DAYS_REQUIRED);
		if (cal.after(depDate)) {
			throw new IllegalArgumentException("Too late to delete that Trip.<br />We need at least "
					+ "a three-day time to let passengers know about it.");
		}
		entman.remove(trip);
	}
}
