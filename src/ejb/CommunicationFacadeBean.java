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
		Collection<MessageJPA> questions = entman.createQuery("FROM MessageJPA b WHERE b.trip.id = :tripId AND b.replyQuestion = null").setParameter("tripId", tripId).getResultList();
		
		Collection<MessageJPA> tripComments = new ArrayList<MessageJPA>();
		
		for (Iterator<MessageJPA> iter1 = questions.iterator(); iter1.hasNext();) {
			MessageJPA q = (MessageJPA) iter1.next();
			tripComments.add(q);
			for (Iterator<MessageJPA> iter2 = q.getAnswers().iterator(); iter2.hasNext();) {
				MessageJPA a = (MessageJPA) iter2.next();
				tripComments.add(a);
			}
		}
//			try
//			{
//				MessageJPA answer = (MessageJPA) entman.createQuery("FROM MessageJPA b WHERE b.replyQuestion.questionId = :questionId").setParameter("questionId", q.getQuestionId()).getSingleResult();
//				if (answer != null) {
//					tripComments.add(answer);
//				}
//			}catch (PersistenceException e) {
//				System.out.println(e);
//			} 
//		}
	    return tripComments;
	}
   
	/**
	 * Method that returns Collection of comments of one driver
	 */
	public java.util.Collection<?> showDriverComments(String driverId) throws PersistenceException {
		@SuppressWarnings("unchecked")
		Collection<DriverCommentJPA> driverComments = entman.createQuery("FROM DriverCommentJPA b  WHERE b.driver.nif = :driverId").setParameter("driverId", driverId).getResultList();
		
	    return driverComments;
	}
	
	/**
	 * Method that adds a question
	 */
	public void askQuestion(int tripId, String passengerId, String subject, String body) throws PersistenceException {

		MessageJPA message = new MessageJPA();
		TripJPA t = findTrip(tripId);
		PassengerJPA p = null;
		if(passengerId != null){
			p = findPassenger(passengerId);
		}
		message.setTrip(t);
		message.setPassenger(p);
		message.setSubject(subject);
		message.setBody(body);
		message.answers = new ArrayList<MessageJPA>();
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

		MessageJPA answer = new MessageJPA();
		TripJPA t = findTrip(tripId);
		answer.setTrip(t);
		DriverJPA d = findDriver(driverId);
		answer.setDriver(d);
		answer.setSubject(subject);
		answer.setBody(body);
		MessageJPA question = findMessage(questionId);
		answer.setQuestion(question);
		question.answers = (Arrays.asList(answer));
		
		try
		{
			entman.persist(answer);
			entman.persist(question);
			
		}catch (PersistenceException e) {
			System.out.println(e);
		} 
	}	
	
		  
	/**
	 * Method that rate a driver
	 */
	public void rateDriver(String driverId, String passengerId, String comment, int rating)throws PersistenceException {
		DriverCommentJPA driverComment = new DriverCommentJPA();
		DriverJPA d = this.findDriver(driverId);
		PassengerJPA p = this.findPassenger(passengerId);
		driverComment.setDriver(d);
		driverComment.setPassenger(p);
		driverComment.setComment(comment);
		driverComment.setRating(rating);
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
		DriverCommentJPA driverComment = (DriverCommentJPA) entman.createQuery("FROM DriverCommentJPA b WHERE b.driver.nif = :driverId AND b.passenger.nif = :passengerId").setParameter("driverId", driverId).setParameter("passengerId",passengerId).getSingleResult();
		return driverComment;
		}catch (PersistenceException e) {
			System.out.println(e);
			return null;
		} 
	   
	}
	
	/**
	 * Method that rate a driver
	 */
	public void updateRateDriver(String driverId, String passengerId, String comment, int rating) throws PersistenceException {
		try
		{
		entman.createQuery("UPDATE DriverCommentJPA b SET b.comment = :comment, b.rating = :rating WHERE b.driver.nif = :driverId AND b.passenger.nif = :passengerId").setParameter("driverId", driverId).setParameter("passengerId",passengerId).setParameter("comment",comment).setParameter("rating",rating).executeUpdate();
		}catch (PersistenceException e) {
			System.out.println(e);
		} 	
	}	
	
	/**
	 * Method that find a trip
	 */
	public TripJPA findTrip(int tripId) throws PersistenceException {
		@SuppressWarnings("unchecked")
		TripJPA trip = (TripJPA) entman.createQuery("FROM TripJPA b WHERE b.id = ?1").setParameter(1, tripId).getSingleResult();
		return trip;
	}	
	
	/**
	 * Method that find a driver
	 */
	public DriverJPA findDriver(String driverId) throws PersistenceException {
		@SuppressWarnings("unchecked")
		DriverJPA driver = (DriverJPA) entman.createQuery("FROM DriverJPA b WHERE b.nif = ?1").setParameter(1, driverId).getSingleResult();
		return driver;
	}	
	
	/**
	 * Method that find a passenger
	 */
	public PassengerJPA findPassenger(String passengerId) throws PersistenceException {
		@SuppressWarnings("unchecked")
		PassengerJPA passenger = (PassengerJPA) entman.createQuery("FROM PassengerJPA b WHERE b.nif = ?1").setParameter(1, passengerId).getSingleResult();
		return passenger;
	}	

	/**
	 * Method that find a message
	 */
	public MessageJPA findMessage(int questionId) throws PersistenceException {
		@SuppressWarnings("unchecked")
		MessageJPA message = (MessageJPA) entman.createQuery("FROM MessageJPA b WHERE b.questionId = ?1").setParameter(1, questionId).getSingleResult();
		return message;
	}
}	
