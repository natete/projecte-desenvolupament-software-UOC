package ejb;

import java.util.List;

import javax.ejb.Remote;

import jpa.CarJPA;
import jpa.DriverJPA;
import jpa.PassengerJPA;
import jpa.TripJPA;
import jpa.UserDTO;
import jpa.UserJPA;

/**
 * Interface to provide the methods to manage the users
 * 
 * @author Joaquín Paredes Ribera - jparedesr@uoc.edu
 *
 */
@Remote
public interface UserFacadeRemote {

	/**
	 * Add a car in a driver
	 * 
	 * @param carRegistrationId
	 *            id of the car
	 * @param brand
	 *            brand of the car
	 * @param model
	 *            model of the car
	 * @param color
	 *            color of the car
	 */
	void addCar(String nif, String carRegistrationId, String brand, String model, String color);

	/**
	 * List all car of a driver
	 * 
	 * @param nif
	 *            nif of the driver
	 * @return List<{{@link TripJPA}> list of the cars
	 */
	List<CarJPA> listAllCars(String nif);

	/**
	 * Check for trips associated with a car
	 * 
	 * @param carRegistrationId
	 *            id of the car
	 * @return boolean
	 */
	boolean carHasTrips(String carRegistrationId);

	/**
	 * Delete a car of a driver
	 * 
	 * @param carRegistrationId
	 *            id of the car
	 */
	void deleteCar(String carRegistrationId);

	/**
	 * Registers a driver
	 * 
	 * @param nif
	 *            nif of the driver
	 * @param name
	 *            name of the driver
	 * @param surname
	 *            surname of the driver
	 * @param phone
	 *            phone of the driver
	 * @param nif
	 *            password password of the driver
	 * @param nif
	 *            email email of the driver
	 */
	void registerDriver(String nif, String name, String surname, String phone, String password, String email);

	/**
	 * Registers a passenger
	 * 
	 * @param nif
	 *            nif of the passenger
	 * @param name
	 *            name of the passenger
	 * @param surname
	 *            surname of the passenger
	 * @param phone
	 *            phone of the passenger
	 * @param nif
	 *            password password of the passenger
	 * @param nif
	 *            email email of the passenger
	 */
	void registerPassenger(String nif, String name, String surname, String phone, String password, String email);

	/**
	 * Registers a passenger
	 * 
	 * @param nif
	 *            nif of the passenger
	 * @param name
	 *            name of the passenger
	 * @param surname
	 *            surname of the passenger
	 * @param phone
	 *            phone of the passenger
	 * @param nif
	 *            password password of the passenger
	 * @param nif
	 *            email email of the passenger
	 */
	UserDTO login(String email, String password);

	UserJPA findUser(String nif);

	boolean existUser(UserJPA user);

	void updatePersonalData(String nif, String name, String surname, String phone, String email, String password);

	boolean existsCar(String carRegistrationId);

	boolean existDriver(String nif);

	boolean existPassenger(String nif);

	DriverJPA findDriver(String nif);

	PassengerJPA findPassenger(String nif);

	boolean isEmailUsed(String email);
}
