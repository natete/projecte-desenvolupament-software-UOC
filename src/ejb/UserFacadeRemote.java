package ejb;

import java.util.Collection;

import javax.ejb.Remote;
import javax.persistence.PersistenceException;

import jpa.DriverJPA;
import jpa.PassengerJPA;
import jpa.UserDTO;

/**
 * Session EJB Remote Interfaces
 */
@Remote
public interface UserFacadeRemote {
	/**
	 * Remotely invoked method.
	 * @param userLogged 
	 */
	public void addCar(String carRegistrationId, String brand, String model, String color, UserDTO userLogged);

	public Collection<?> listAllCars(String nif);

	public boolean existsTripsByCar(String carRegistrationId);
		
	public void deleteCar(String carRegistrationId);

	public void registerDriver(String nif, String name, String surname, String phone, String password, String email);

	public void registerPassenger(String nif, String name, String surname, String phone, String password, String email);

	public UserDTO login(String email, String password);

	public boolean existsCar(String carRegistrationId);

	public boolean existsDriver(String nif, String email, UserDTO userLogged);

	public DriverJPA findDriver(String nif);
		
	public boolean existsPassenger(String nif, String email, UserDTO userLogged);

	public PassengerJPA findPassenger(String nif);
	
	public void updateDriver(String nif, String name, String surname, String phone, String email, String password);

	public void updatePassenger(String nif, String name, String surname, String phone, String email, String password);
	
	public boolean existsDriverEmail(String nif, String name, String surname, String email, UserDTO userLogged);

	public boolean existsPassengerEmail(String nif, String name, String surname, String email, UserDTO userLogged);
	
}
