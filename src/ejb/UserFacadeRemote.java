package ejb;

import java.util.Collection;

import javax.ejb.Remote;

import jpa.CarJPA;
import jpa.DriverJPA;
import jpa.PassengerJPA;
import jpa.UserDTO;
import jpa.UserJPA;

/**
 * Session EJB Remote Interfaces
 */
@Remote
public interface UserFacadeRemote {
	/**
	 * Remotely invoked method.
	 * 
	 * @param userLogged
	 */
	void addCar(String nif, String carRegistrationId, String brand, String model, String color);

	Collection<CarJPA> listAllCars(String nif);

	boolean carHasTrips(String carRegistrationId);

	void deleteCar(String carRegistrationId);

	void registerDriver(String nif, String name, String surname, String phone, String password, String email);

	void registerPassenger(String nif, String name, String surname, String phone, String password, String email);

	UserDTO login(String email, String password);

	UserJPA findUser(String nif);

	boolean existUser(UserJPA user);

	void updatePersonalData(String nif, String name, String surname, String phone, String email, String password);

	boolean existsCar(String carRegistrationId);

	boolean existDriver(String nif);

	boolean existPassenger(String nif);

	DriverJPA findDriver(String nif);

	PassengerJPA findPassenger(String nif);
}
