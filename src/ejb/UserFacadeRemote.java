package ejb;

import java.util.Collection;

import javax.ejb.Remote;


/**
 * Session EJB Remote Interfaces
 */
@Remote
public interface UserFacadeRemote {
	  /**
	   * Remotely invoked method.
	   */
	  public void addCar(String nif, String carRegistrationId, String brand, String model, String color);	
	  public Collection<?> listAllCars(String nif);
	  public void deleteCar(String carRegistrationId);
	  public boolean existsCar(String carRegistrationId);
}
