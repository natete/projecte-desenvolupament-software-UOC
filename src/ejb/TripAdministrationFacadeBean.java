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

	public static final String BLANK_SPACE = " ";
	public static final String QUERY_FIND_TRIPS_BY_DRIVER = "TripJPA.findTripsByDriver";
	public static final String QUERY_GET_TRIP_BY_ID = "TripJPA.getTripById";
	public static final String QUERY_UPDATE_TRIP = "TripJPA.updateTrip";
	public static final String QUERY_GET_DRIVER_BY_NIF = "findMyDriver";
	public static final String QUERY_GET_CARS_BY_DRIVER = "CarJPA.findCarsByDriver";
	public static final String QUERY_COUNT_TRIPS_BY_DRIVER = "TripJPA.countTripJPA";
	public static final String QUERY_DELETE_TRIP_BY_TRIPID = "TripJPA.deleteTripJPA";
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
	public static final String PARAMETER_TRIP_CAR = "myCar";
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
		// TODO Auto-generated method stub

		Query query = entman.createNamedQuery(QUERY_FIND_TRIPS_BY_DRIVER)
				.setParameter("driverNif", driver)
				.setFirstResult(page * PAGE_SIZE).setMaxResults(PAGE_SIZE);
		TripsDTO trips = new TripsDTO();
		trips.setTrips(query.getResultList());
		Integer total = trips.getTrips().size();

		trips.setTotal(total.longValue());
		return trips;
		/*
		 * DriverJPA myDriver = (DriverJPA)
		 * entman.createNamedQuery(QUERY_GET_DRIVER_BY_NIF) .setParameter("nif",
		 * driver) .getSingleResult(); CriteriaBuilder cb =
		 * entman.getCriteriaBuilder(); CriteriaQuery<TripJPA> criteriaQuery =
		 * cb.createQuery(TripJPA.class); Root<TripJPA> root =
		 * criteriaQuery.from(TripJPA.class); List<Predicate> predicates = new
		 * ArrayList<>(); Predicate pred = cb.equal(root.get("driver"),
		 * myDriver); predicates.add(pred);
		 * criteriaQuery.where(cb.and(predicates.toArray(new Predicate[0])));
		 * final TypedQuery<TripJPA> query = entman.createQuery(criteriaQuery);
		 * query.setFirstResult(page * PAGE_SIZE);
		 * query.setMaxResults(PAGE_SIZE); TripsDTO trips = new TripsDTO();
		 * trips.setTrips(query.getResultList()); CriteriaQuery<Long>
		 * countCriteria = cb.createQuery(Long.class); Root<TripJPA> countRoot =
		 * countCriteria.from(TripJPA.class);
		 * countCriteria.select(cb.count(countRoot));
		 * countCriteria.where(cb.and(predicates.toArray(new Predicate[0])));
		 * trips.setTotal(entman.createQuery(countCriteria).getSingleResult());
		 * return trips;
		 */
	}

	public static int getLastPage(int totalElements, int pageSize) {
		int base = totalElements / pageSize;
		int mod = totalElements % pageSize;
		return base + (mod > 0 ? 1 : 0);
	}

	public static int getStartItemByPage(int currentPage, int pageSize) {
		return Math.max((pageSize * (currentPage - 1)) + 1, 1);
	}

	/**
	 * Add a trip to carsharing.trps table. The method gets the parameters from
	 * the managed bean, then gets the driver id to add it to the trip entity,
	 * and finally persists the trip via entity manager.
	 */
	@Override
	public void addTrip(String description, String departureCity, String fromPlace,
			Date departureDate, Date departureTime, String arrivalCity, String toPlace,
			int availableSeats, float price, String nif, String selectedCar) {
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
			driverJPA = (DriverJPA) entman.createNamedQuery("findMyDriver")
					.setParameter("nif", nif).getSingleResult();
		} catch (PersistenceException e) {
			System.out.println(e);
		}
		trip.setDriver(driverJPA);

		String[] tokens = selectedCar.split(" ");
		String brand = tokens[0];
		String model = tokens[1];
		String colour = tokens[2];
		CarJPA myCar = (CarJPA) entman.createNamedQuery("CarJPA.findCarByBrandModelColour")
				.setParameter("brand", brand)
				.setParameter("model", model).setParameter("colour", colour).getSingleResult();
		trip.setCar(myCar);
		try {
			entman.persist(trip);

		} catch (PersistenceException e) {
			System.out.println(e);
		}
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
		// TODO Auto-generated method stub

		TripJPA trip = null;
		trip = (TripJPA) entman.createNamedQuery(QUERY_GET_TRIP_BY_ID)
			.setParameter(PARAMETER_TRIP_ID, tripId).getSingleResult();
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
	public void updateTripInformation(Integer tripId, String description, String departureCity,
		String fromPlace, Date departureDate, Date departureTime, String arrivalCity,
		String toPlace, Integer availableSeats, Float price, String selectedCar) {
		// TODO Auto-generated method stub

		String[] tokens = selectedCar.split(" ");
		String brand = tokens[0];
		String model = tokens[1];
		String colour = tokens[2];
		CarJPA myCar = (CarJPA) entman.createNamedQuery("CarJPA.findCarByBrandModelColour")
				.setParameter("brand", brand).setParameter("model", model)
				.setParameter("colour", colour).getSingleResult();
		try {
			entman.createNamedQuery(QUERY_UPDATE_TRIP).setParameter(PARAMETER_TRIP_DESCRIPTION,
				description)
				.setParameter(PARAMETER_TRIP_DEPARTURE_CITY, departureCity)
				.setParameter(PARAMETER_TRIP_FROM_PLACE, fromPlace)
				.setParameter(PARAMETER_TRIP_DEPARTURE_DATE, departureDate)
				.setParameter(PARAMETER_TRIP_DEPARTURE_TIME, departureTime)
				.setParameter(PARAMETER_TRIP_ARRIVAL_CITY, arrivalCity)
				.setParameter(PARAMETER_TRIP_TO_PLACE, toPlace)
				.setParameter(PARAMETER_TRIP_AVAILABLE_SEATS, availableSeats)
				.setParameter(PARAMETER_TRIP_PRICE, price).setParameter(PARAMETER_TRIP_ID, tripId)
				.setParameter(PARAMETER_TRIP_CAR, myCar).executeUpdate();
		} catch (PersistenceException e) {
			System.out.println(e);
		}
	}

	public TripJPA showTrip(Integer id) {
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
		DriverJPA driverJPA = (DriverJPA) entman.createNamedQuery(QUERY_GET_DRIVER_BY_NIF)
				.setParameter(PARAMETER_DRIVER_NIF1, driverId).getSingleResult();
		try {
			myCars = entman.createNamedQuery(QUERY_GET_CARS_BY_DRIVER)
				.setParameter(PARAMETER_CARS_DRIVER, driverJPA)
				.getResultList();
		} catch (PersistenceException e) {
			System.out.println(e);
		}
		return myCars;
	}

	public Long countMyTrips(String driverNif) {
		Integer i = 0;
		Long totalResults = i.longValue();
		totalResults = (Long) entman.createNamedQuery(QUERY_COUNT_TRIPS_BY_DRIVER)
			.setParameter(PARAMETER_DRIVER_NIF, driverNif).getSingleResult();
		return totalResults;
	}

	@Override
	public String deleteTrip(int tripId) {
		String errorMessage = null;
		TripJPA trip = (TripJPA) entman.createNamedQuery(QUERY_GET_TRIP_BY_ID)
			.setParameter(PARAMETER_TRIP_ID, tripId).getSingleResult();
		Calendar depDate = Calendar.getInstance();
		depDate.setTime(trip.getDepartureDate());
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, MINIMUM_DAYS_REQUIRED);
		if (cal.after(depDate)) {
			errorMessage = "Too late to delete that Trip.<br />We need at least "
				+ "a three-day time to let passengers know about it.";
			return errorMessage;
		}
		/*
		List<PassengerJPA> passengers = findAllPassengers(tripId);
		if (!passengers.isEmpty() || passengers != null) {
			Iterator<PassengerJPA> it = passengers.iterator();
			while (it.hasNext()) {
				PassengerJPA p = it.next();
				trip.removePassenger(p);
				entman.persist(p);
			}
		}
		*/
		entman.createNamedQuery(QUERY_DELETE_TRIP_BY_TRIPID)
			.setParameter(PARAMETER_TRIP_ID, tripId).executeUpdate();
		return errorMessage;
	}
}
