package ejb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import jpa.DriverCommentJPA;
import jpa.DriverJPA;
import jpa.MessageJPA;
import jpa.PassengerJPA;
import jpa.TripJPA;

/**
 * EJB Session Bean Class
 */
@Stateless
public class CommunicationFacadeBean implements CommunicationFacadeRemote {

	private static final String QUERY_FIND_QUESTIONS = "MessageJPA.getQuestions";
	private static final String QUERY_GET_DRIVER_COMMENTS = "DriverCommentJPA.getDriverComments";
	private static final String QUERY_GET_DRIVER_COMMENT_BY_PASSENGER = "DriverCommentJPA.getDriverCommentByPassenger";
	private static final String QUERY_GET_TRIP_BY_ID = "TripJPA.getTripById";
	private static final String QUERY_GET_MESSAGE_BY_ID = "MessageJPA.getMessageById";

	private static final String PARAMETER_TRIP_ID = "tripId";
	private static final String PARAMETER_DRIVER_ID = "driverId";
	private static final String PARAMETER_PASSENGER_ID = "passengerId";
	private static final String PARAMETER_QUESTION_ID = "questionId";

	// Persistence Unit Context
	@PersistenceContext(unitName = "CarSharing")
	private EntityManager entman;

	/**
	 * Method that returns Collection of comments of one trip
	 * @param tripId, the Id of the trip
	 * @return collection of comments (questions and answers) about the trip
	 */
	@SuppressWarnings("unchecked")
	public Collection<MessageJPA> showTripComments(int tripId) throws PersistenceException {

		Query query = entman.createNamedQuery(QUERY_FIND_QUESTIONS);
		query.setParameter(PARAMETER_TRIP_ID, tripId);

		return query.getResultList();
	}

	/**
	 * Method that returns Collection of comments of one driver
	 * @param driverId, the Id of the driver
	 * @return collection of comments and ratings about the driver
	 */
	@SuppressWarnings("unchecked")
	public Collection<DriverCommentJPA> showDriverComments(String driverId) throws PersistenceException {
		Query query = entman.createNamedQuery(QUERY_GET_DRIVER_COMMENTS);
		query.setParameter(PARAMETER_DRIVER_ID, driverId).getResultList();

		return query.getResultList();
	}

	/**
	 * Method that adds a question
	 * @param tripId, the id of the trip
	 * @param passengerId, the Id of the passenger. It may be null if it's an unknown user
	 * @param subject, the subject of the question
	 * @param body, the body of the question
	 */
	public void askQuestion(int tripId, String passengerId, String subject, String body) throws PersistenceException {

		MessageJPA message = new MessageJPA();
		TripJPA t = findTrip(tripId);
		PassengerJPA p = null;
		if (passengerId != null) {
			p = findPassenger(passengerId);
		}
		message.setTrip(t);
		message.setPassenger(p);
		message.setSubject(subject);
		message.setBody(body);
		message.setAnswers(new ArrayList<MessageJPA>());
		try {
			entman.persist(message);

		} catch (PersistenceException e) {
			System.out.println(e);
		}
	}

	/**
	 * Method that replies a question
	 * @param tripId, the id of the trip
	 * @param questionId, the id of the question
	 * @param driverId, the id of the driver
	 * @param subject, the subject of the question
	 * @param body, the body of the answer
	 */
	public void replyQuestion(int tripId, int questionId, String driverId, String subject, String body)
			throws PersistenceException {

		MessageJPA question = findMessage(questionId);
		MessageJPA answer = new MessageJPA();
		TripJPA t = findTrip(tripId);
		answer.setTrip(t);
		DriverJPA d = findDriver(driverId);
		answer.setDriver(d);
		answer.setSubject(subject);
		answer.setBody(body);
		answer.setQuestion(findMessage(questionId));
		question.setAnswers(Arrays.asList(answer));

		try {
			entman.persist(answer);
			entman.persist(question);

		} catch (PersistenceException e) {
			System.out.println(e);
		}
	}

	/**
	 * Method that rate a driver
	 * @param driverId, the id of the driver
	 * @param passengerId, the id of the passenger
	 * @param comment, opinion about the driver
	 * @param rating, rating about the driver (0-10)
	 */
	public void rateDriver(String driverId, String passengerId, String comment, int rating) {

		DriverCommentJPA driverComment = getDriverComment(driverId, passengerId);

		if (driverComment == null) {
			driverComment = new DriverCommentJPA(comment, rating);
			DriverJPA d = this.findDriver(driverId);
			PassengerJPA p = this.findPassenger(passengerId);
			driverComment.setDriver(d);
			driverComment.setPassenger(p);
			entman.persist(driverComment);
		} else {
			driverComment.setComment(comment);
			driverComment.setRating(rating);
			entman.merge(driverComment);
		}
	}

	/**
	 * Method to get a driver comment
	 */
	public DriverCommentJPA getDriverComment(String driverId, String passengerId) throws PersistenceException {

		try {
			Query query = entman.createNamedQuery(QUERY_GET_DRIVER_COMMENT_BY_PASSENGER);
			query.setParameter(PARAMETER_DRIVER_ID, driverId);
			query.setParameter(PARAMETER_PASSENGER_ID, passengerId);

			return (DriverCommentJPA) query.getSingleResult();
		} catch (PersistenceException e) {
			System.out.println(e);
			return null;
		}
	}

	/**
	 * Method that find a trip
	 */
	public TripJPA findTrip(int tripId) throws PersistenceException {

		Query query = entman.createNamedQuery(QUERY_GET_TRIP_BY_ID);
		query.setParameter(PARAMETER_TRIP_ID, tripId);

		try {
			return (TripJPA) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * Method that find a driver
	 */
	public DriverJPA findDriver(String driverId) throws PersistenceException {

		DriverJPA driver = (DriverJPA) entman.createQuery("FROM DriverJPA b WHERE b.nif = ?1").setParameter(1, driverId)
				.getSingleResult();
		return driver;
	}

	/**
	 * Method that find a passenger
	 */
	public PassengerJPA findPassenger(String passengerId) throws PersistenceException {
		
		PassengerJPA passenger = (PassengerJPA) entman.createQuery("FROM PassengerJPA b WHERE b.nif = ?1")
				.setParameter(1, passengerId).getSingleResult();
		return passenger;
	}

	/**
	 * Method that find a message
	 */
	public MessageJPA findMessage(int questionId) throws PersistenceException {
		Query query = entman.createNamedQuery(QUERY_GET_MESSAGE_BY_ID);
		query.setParameter(PARAMETER_QUESTION_ID, questionId);

		try {
			return (MessageJPA) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
