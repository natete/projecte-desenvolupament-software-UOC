package ejb;

import java.util.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import jpa.MessageJPA;
import jpa.DriverCommentJPA;
import jpa.TripJPA;
import jpa.DriverJPA;
import jpa.PassengerJPA;
import ejb.CommunicationFacadeRemote;

/**
 * EJB Session Bean Class 
 */
@Stateless
public class CommunicationFacadeBean implements CommunicationFacadeRemote {
	
	//Persistence Unit Context
	@PersistenceContext(unitName="CarSharing") 
	private EntityManager entman;
	
	/**
	 * Method that returns Collection of comments of one trip
	 */
	public java.util.Collection<?> showTripComments(int tripId) throws PersistenceException {
		@SuppressWarnings("unchecked")
		Collection<MessageJPA> tripComments = entman.createQuery("FROM MessageJPA b WHERE b.tripId = :tripId").setParameter("tripId", tripId).getResultList();
		
	    return tripComments;
	}
   
	/**
	 * Method that returns Collection of comments of one driver
	 */
	public java.util.Collection<?> showDriverComments(String driverId) throws PersistenceException {
		@SuppressWarnings("unchecked")
		Collection<DriverCommentJPA> driverComments = entman.createQuery("FROM DriverCommentJPA b WHERE b.driverId = :driverId").setParameter("driverId", driverId).getResultList();
		
	    return driverComments;
	}
	
	/**
	 * Method that adds a question
	 */
	public void askQuestion(int tripId, int questionId, String passengerId, String subject, String body) throws PersistenceException {

		MessageJPA message = new MessageJPA();
		TripJPA t = findTrip(tripId);
		message.setTrip(t);
		message.setQuestionId(questionId);
		PassengerJPA p = findPassenger(passengerId);
		message.setPassenger(p);
		message.setSubject(subject);
		message.setBody(body);
		try
		{
			entman.persist(message);
			
		}catch (PersistenceException e) {
			System.out.println(e);
		} 
	}	
	  
	/**
	 * Method that replies a question
	 */
	public void replyQuestion(int tripId, int questionId, String driverId, String subject, String body) throws PersistenceException {

		MessageJPA message = new MessageJPA();
		TripJPA t = findTrip(tripId);
		message.setTrip(t);
		message.setQuestionId(questionId);
		DriverJPA d = findDriver(driverId);
		message.setDriver(d);
		message.setSubject(subject);
		message.setBody(body);
		try
		{
			entman.persist(message);
			
		}catch (PersistenceException e) {
			System.out.println(e);
		} 
	}	
	
		  
	/**
	 * Method that rate a driver
	 */
	public void rateDriver(String driverId, String passengerId, String comment, int ratting)throws PersistenceException {
		DriverCommentJPA driverComment = new DriverCommentJPA();
		driverComment.setDriverId(driverId);
		driverComment.setPassengerId(passengerId);
		driverComment.setComment(comment);
		driverComment.setRatting(ratting);
		try
		{
			entman.persist(driverComment);
			
		}catch (PersistenceException e) {
			System.out.println(e);
		} 
		
	}	
	
	/**
	 * Method to get a driver comment
	 */
	public DriverCommentJPA getDriverComment(String driverId, String passengerId) throws PersistenceException {
	
		try
		{
		DriverCommentJPA driverComment = (DriverCommentJPA) entman.createQuery("FROM DriverCommentJPA b WHERE b.driverId = :driverId AND b.passengerId = :passengerId").setParameter("driverId", driverId).setParameter("passengerId",passengerId).getSingleResult();
		return driverComment;
		}catch (PersistenceException e) {
			System.out.println(e);
			return null;
		} 
	   
	}
	
	/**
	 * Method that rate a driver
	 */
	public void updateRateDriver(String driverId, String passengerId, String comment, int ratting) throws PersistenceException {
		try
		{
		entman.createQuery("UPDATE DriverCommentJPA b SET b.comment = :comment, b.ratting = :ratting WHERE b.driverId = :driverId AND b.passengerId = :passengerId").setParameter("driverId", driverId).setParameter("passengerId",passengerId).setParameter("comment",comment).setParameter("ratting",ratting).executeUpdate();
		}catch (PersistenceException e) {
			System.out.println(e);
		} 	
	}	
	
	/**
	 * Method that find a trip
	 */
	public TripJPA findTrip(int tripId) throws PersistenceException {
		@SuppressWarnings("unchecked")
		TripJPA trip = (TripJPA) entman.createQuery("FROM TripJPA b WHERE b.tripId = ?1").setParameter(1, tripId).getSingleResult();
		return trip;
	}	
	
	/**
	 * Method that find a passenger
	 */
	public DriverJPA findDriver(String driverId) throws PersistenceException {
		@SuppressWarnings("unchecked")
		DriverJPA driver = (DriverJPA) entman.createQuery("FROM DriverJPA b WHERE b.driverId = ?1").setParameter(1, driverId).getSingleResult();
		return driver;
	}	
	
	/**
	 * Method that find a passenger
	 */
	public PassengerJPA findPassenger(String passengerId) throws PersistenceException {
		@SuppressWarnings("unchecked")
		PassengerJPA passenger = (PassengerJPA) entman.createQuery("FROM PassengerJPA b WHERE b.passengerId = ?1").setParameter(1, passengerId).getSingleResult();
		return passenger;
	}	
}
