package ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import jpa.TripJPA;

@Stateless
public class TripFacadeBean implements TripFacadeRemote {

	private static final String BLANK = "";

	private static final String GET_TRIP_BY_ID = "TripJPA.getTripById";

	private static final String PARAMETER_TRIP_ID = "tripId";

	// Persistence Unit Context
	@PersistenceContext(unitName = "CarSharing")
	private EntityManager entman;

	/**
	 * @see TripFacadeRemote#findTrips(String, Date, String)
	 */
	@Override
	public List<TripJPA> findTrips(String departureCity, Date departureDate, String arrivalCity) {

		CriteriaBuilder cb = entman.getCriteriaBuilder();

		CriteriaQuery<TripJPA> q = cb.createQuery(TripJPA.class);
		Root<TripJPA> c = q.from(TripJPA.class);
		List<Predicate> predicates = new ArrayList<>();

		if (departureCity != null && !BLANK.equals(departureCity)) {
			Predicate pred = cb.like(cb.upper(c.get("departureCity")), departureCity.toUpperCase() + "%");
			predicates.add(pred);
		}
		if (departureDate != null) {
			Predicate pred = cb.equal(c.get("departureDate"), departureDate);
			predicates.add(pred);
		}
		if (arrivalCity != null && !BLANK.equals(arrivalCity)) {
			Predicate pred = cb.like(cb.upper(c.get("arrivalCity")), arrivalCity.toUpperCase() + "%");
			predicates.add(pred);
		}

		q.where(cb.and(predicates.toArray(new Predicate[0])));

		final TypedQuery<TripJPA> query = entman.createQuery(q);

		return query.getResultList();
	}

	@Override
	public TripJPA showTrip(Integer id) {

		Query query = entman.createNamedQuery(GET_TRIP_BY_ID);
		query.setParameter(PARAMETER_TRIP_ID, id);
		return (TripJPA) query.getSingleResult();
	}
}
