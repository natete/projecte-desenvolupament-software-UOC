package ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import jpa.TripJPA;

@Stateless
public class TripFacadeBean implements TripFacadeRemote {
	
	private static final String BLANK = "";

	// Persistence Unit Context
	@PersistenceContext(unitName = "CarSharing")
	private EntityManager entman;

	/**
	 * @see TripFacadeRemote#findTrips(String, Date, String)
	 */
	@Override
	public List<TripJPA> findTrips(String departureCity, Date departureDate, String arrivalCity) {
		List<TripJPA> result;
		
		CriteriaBuilder cb = entman.getCriteriaBuilder();
		
		CriteriaQuery<TripJPA> q = cb.createQuery(TripJPA.class);
		Root<TripJPA> c = q.from(TripJPA.class);
		List<Predicate> predicates = new ArrayList<>();
		
		if(departureCity != null && !BLANK.equals(departureCity)){
			Predicate pred = cb.like(cb.upper(c.<String>get("departureCity")), departureCity.toUpperCase() + "%");
			predicates.add(pred);
		}
		if(departureDate != null){
			Predicate pred = cb.equal(c.get("departureCity"), departureDate);
			predicates.add(pred);
		}
		if(arrivalCity != null && !BLANK.equals(arrivalCity)){
			Predicate pred = cb.like(cb.upper(c.<String>get("arrivalCity")), arrivalCity.toUpperCase() + "%");
			predicates.add(pred);
		}
		
		q.where(cb.and(predicates.toArray(new Predicate[0])));
		
		final TypedQuery<TripJPA> query = entman.createQuery(q);
		
		return query.getResultList();
	}
}
