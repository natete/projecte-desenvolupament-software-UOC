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

	Collection<MessageJPA> showTripComments(int tripId);

	Collection<DriverCommentJPA> showDriverComments(String driver);

	void askQuestion(int tripId, String passenger, String subject, String body);

	void replyQuestion(int tripId, int questionId, String driver, String subject, String body);

	void rateDriver(String driver, String passenger, String comment, int rating);

	DriverCommentJPA getDriverComment(String driver, String Passenger);

	TripJPA findTrip(int tripId);

	DriverJPA findDriver(String driverId);

	PassengerJPA findPassenger(String passengerId);

	MessageJPA findMessage(int questionId);
}
