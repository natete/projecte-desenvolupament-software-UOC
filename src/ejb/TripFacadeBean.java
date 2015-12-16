package ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import jpa.PassengerJPA;
import jpa.TripJPA;
import jpa.TripsDTO;

/**
 * Implementation of {@link TripFacadeRemote} that performs trip related actions
 * @author Ignacio González Bullón - nachoglezbul@uoc.edu
 *
 */
@Stateless
public class TripFacadeBean implements TripFacadeRemote {

	private static final String BLANK = "";

	private static final String GET_TRIP_BY_ID = "TripJPA.getTripById";

	private static final String PARAMETER_TRIP_ID = "tripId";

	private static final String GET_PASSENGER_BY_ID = "PassengerJPA.getPassengerById";

	private static final String PARAMETER_PASSENGER_ID = "passengerId";

	private static final int PAGE_SIZE = 10;

	@PersistenceContext(unitName = "CarSharing")
	private EntityManager entman;

	/**
	 * @see TripFacadeRemote#findTrips(String, Date, String)
	 */
	@Override
	public TripsDTO findTrips(String departureCity, Date departureDate, String arrivalCity, int page) {

		CriteriaBuilder cb = entman.getCriteriaBuilder();

		CriteriaQuery<TripJPA> criteriaQuery = cb.createQuery(TripJPA.class);
		Root<TripJPA> root = criteriaQuery.from(TripJPA.class);
		List<Predicate> predicates = new ArrayList<>();

		if (departureCity != null && !BLANK.equals(departureCity)) {
			Predicate pred = cb.like(cb.upper(root.<String> get("departureCity")), departureCity.toUpperCase() + "%");
			predicates.add(pred);
		}
		if (departureDate != null) {
			Predicate pred = cb.equal(root.get("departureDate"), departureDate);
			predicates.add(pred);
		}
		if (arrivalCity != null && !BLANK.equals(arrivalCity)) {
			Predicate pred = cb.like(cb.upper(root.<String> get("arrivalCity")), arrivalCity.toUpperCase() + "%");
			predicates.add(pred);
		}

		criteriaQuery.where(cb.and(predicates.toArray(new Predicate[0])));

		final TypedQuery<TripJPA> query = entman.createQuery(criteriaQuery);

		query.setFirstResult(page * PAGE_SIZE);
		query.setMaxResults(PAGE_SIZE);

		TripsDTO trips = new TripsDTO();

		trips.setTrips(query.getResultList());

		CriteriaQuery<Long> countCriteria = cb.createQuery(Long.class);
		Root<TripJPA> countRoot = countCriteria.from(TripJPA.class);
		countCriteria.select(cb.count(countRoot));
		countCriteria.where(cb.and(predicates.toArray(new Predicate[0])));

		trips.setTotal(entman.createQuery(countCriteria).getSingleResult());

		return trips;
	}

	/**
	 * @see TripFacadeRemote#showTrip(Integer)
	 */
	@Override
	public TripJPA showTrip(Integer id) {
		TripJPA result;
		Query query = entman.createNamedQuery(GET_TRIP_BY_ID);
		query.setParameter(PARAMETER_TRIP_ID, id);
		try {
			result = (TripJPA) query.getSingleResult();
		} catch (NoResultException e) {
			result = null;
		}
		return result;
	}

	/**
	 * @see TripFacadeRemote#registerInTrip(String, Integer)
	 */
	@Override
	public void registerInTrip(String userId, Integer tripId) throws IllegalArgumentException {
		TripJPA trip = showTrip(tripId);
		PassengerJPA passenger;
		Query query = entman.createNamedQuery(GET_PASSENGER_BY_ID);
		query.setParameter(PARAMETER_PASSENGER_ID, userId);
		try {
			passenger = (PassengerJPA) query.getSingleResult();
		} catch (NoResultException e) {
			passenger = null;
		}
		if (passenger == null || trip == null) {
			throw new IllegalArgumentException("Error obtaining trip or passenger from database");
		} else if (trip.getAvailableSeats() == 0) {
			throw new IllegalArgumentException(
					"Sorry, the selected trip has not available seats. Please, try it later or find another trip");
		} else {
			trip.addPassenger(passenger);
			entman.persist(trip);
		}
	}

	/**
	 * @see TripFacadeRemote#removeFromTrip(String, Integer)
	 */
	@Override
	public void removeFromTrip(String userId, Integer tripId) throws IllegalArgumentException {
		TripJPA trip = showTrip(tripId);
		PassengerJPA passenger;
		Query query = entman.createNamedQuery(GET_PASSENGER_BY_ID);
		query.setParameter(PARAMETER_PASSENGER_ID, userId);
		try {
			passenger = (PassengerJPA) query.getSingleResult();
		} catch (NoResultException e) {
			passenger = null;
		}
		if (passenger == null || trip == null) {
			throw new IllegalArgumentException("Error obtaining trip or passenger from database");
		} else {
			trip.removePassenger(passenger);
			entman.persist(trip);
		}
	}
}
