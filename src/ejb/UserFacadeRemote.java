package ejb;

import java.util.Collection;

import javax.ejb.Remote;

import jpa.UserDTO;

/**
 * Session EJB Remote Interfaces
 */
@Remote
public interface UserFacadeRemote {
	/**
	 * Remotely invoked method.
	 */
	public void addCar(String carRegistrationId, String brand, String model, String color);

	public Collection<?> listAllCars(String nif);

	public void deleteCar(String carRegistrationId);

	public void registerDriver(String nif, String name, String surname, String phone, String password, String email);

	public void registerPassenger(String nif, String name, String surname, String phone, String password, String email);

	public UserDTO login(String email, String password);

	public boolean existsCar(String carRegistrationId);

	public boolean existsDriver(String nif, String email);

	public boolean existsPassenger(String nif, String email);

}
