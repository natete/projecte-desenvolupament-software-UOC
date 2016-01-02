package ejb;

import java.util.Collection;

import javax.ejb.Remote;

import jpa.DriverCommentJPA;
import jpa.DriverJPA;
import jpa.MessageJPA;
import jpa.PassengerJPA;
import jpa.TripJPA;

/**
 * Session EJB Remote Interfaces
 */
@Remote
public interface CommunicationFacadeRemote {
	/**
	 * Remotely invoked method.
	 */

	/**
	 * Method that returns Collection of comments of one trip
	 * @param tripId, the Id of the trip
	 * @return collection of comments (questions and answers) about the trip
	 */
	Collection<MessageJPA> showTripComments(int tripId);

	/**
	 * Method that returns Collection of comments of one driver
	 * @param driverId, the Id of the driver
	 * @return collection of comments and ratings about the driver
	 */
	Collection<DriverCommentJPA> showDriverComments(String driverId);

	/**
	 * Method that adds a question
	 * @param tripId, the id of the trip
	 * @param passengerId, the Id of the passenger. It may be null if it's an unknown user
	 * @param subject, the subject of the question
	 * @param body, the body of the question
	 */
	void askQuestion(int tripId, String passengerId, String subject, String body);

	/**
	 * Method that replies a question
	 * @param tripId, the id of the trip
	 * @param questionId, the id of the question
	 * @param driverId, the id of the driver
	 * @param subject, the subject of the question
	 * @param body, the body of the answer
	 */
	void replyQuestion(int tripId, int questionId, String driverId, String subject, String body);

	/**
	 * Method that rate a driver
	 * @param driverId, the id of the driver
	 * @param passengerId, the id of the passenger
	 * @param comment, opinion about the driver
	 * @param rating, rating about the driver (0-10)
	 */
	void rateDriver(String driverId, String passengerId, String comment, int rating);

	DriverCommentJPA getDriverComment(String driverId, String PassengerId);

	TripJPA findTrip(int tripId);

	DriverJPA findDriver(String driverId);

	PassengerJPA findPassenger(String passengerId);

	MessageJPA findMessage(int questionId);
}
