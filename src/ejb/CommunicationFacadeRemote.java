package ejb;

import java.util.Collection;

import javax.ejb.Remote;
import javax.persistence.PersistenceException;

import jpa.DriverCommentJPA;
import jpa.DriverJPA;
import jpa.TripJPA;


/**
 * Session EJB Remote Interfaces
 */
@Remote
public interface CommunicationFacadeRemote {
	  /**
	   * Remotely invoked method.
	   */
	 	  
	  public Collection<?>showTripComments(int tripId);
	  public Collection<?>showDriverComments(String driver);
	  public void askQuestion(int tripId, String passenger, String subject, String body);
	  public void replyQuestion(int tripId, int questionId, String driver, String subject, String body);
	  public void rateDriver(String driver, String passenger, String comment, int rating);
	  public DriverCommentJPA getDriverComment(String driver, String Passenger);
	  public void updateRateDriver(String driverId, String passengerId, String comment, int rating);
	  public TripJPA findTrip(int tripId); 
	  public DriverJPA findDriver(String driverId); 
}
