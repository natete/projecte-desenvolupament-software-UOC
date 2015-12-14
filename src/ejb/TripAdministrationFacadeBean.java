package ejb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
	
	/**
	 * PERSISTENCE CONTEXT
	 */
	@PersistenceContext(unitName="CarSharing") 
	private EntityManager entityManager;
	
	/**
	 * INTERFACE IMPLEMENTATION
	 */
	
	/**
	 * findMyTrips
	 */
	@Override
	public Collection<TripJPA> findMyTrips(String driver) {
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<TripJPA> criteriaQuery = criteriaBuilder.createQuery(TripJPA.class);
		Root<TripJPA> tripJPA = criteriaQuery.from(TripJPA.class);
		Collection<Predicate> predicates = new ArrayList<>();
		
		if(driver != null && !BLANK_SPACE.equals(driver)) {
			Predicate predicate = criteriaBuilder.like(criteriaBuilder.upper(tripJPA.get("driver")), driver.toUpperCase() + "%");
			predicates.add(predicate);
		}
		
		criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
		
		final TypedQuery<TripJPA> query = entityManager.createQuery(criteriaQuery);
		
		return query.getResultList();
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
		
		DriverJPA driverJPA = (DriverJPA) entityManager.createNamedQuery ("findMyDriver").setParameter("nif", nif).getSingleResult();
		trip.setDriver(driverJPA);
		
		Collection<CarJPA> myCars = driverJPA.getCars();
		Iterator<CarJPA> it = myCars.iterator();
		CarJPA myCar = null;
		while (it.hasNext()) myCar = it.next();
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
	public Collection<PassengerJPA> findAllPassengers(int tripId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * updateTripInformation
	 */
	@Override
	public void updateTripInformation(int tripId, String description, String departureCity, String fromPlace,
			Date departureDate, Date departureTime, String arrivalCity, String toPlace, int availableSeats,
			float price) {
		// TODO Auto-generated method stub	
	}
	
}

