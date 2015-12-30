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
	 * @param password
	 *            password of the driver
	 * @param email
	 *            e-mail of the driver
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
	 * @param password
	 *            password of the passenger
	 * @param email
	 *            e-mail of the passenger
	 */
	void registerPassenger(String nif, String name, String surname, String phone, String password, String email);

	/**
	 * Login a user
	 * 
	 * @param email
	 *            e-mail of the user
	 * @param password
	 *            password of the user
	 * @return UserDTO data of the user
	 */
	UserDTO login(String email, String password);

	/**
	 * Find the user that match the given conditions
	 * 
	 * @param nif
	 *            nif of the user
	 * @return UserJPA data of the user
	 */
	UserJPA findUser(String nif);

	/**
	 * Check if exist the user that match the given conditions
	 * 
	 * @param user
	 *            user to check
	 * @return boolean
	 */
	boolean existUser(UserJPA user);

	/**
	 * update the personal data of a user
	 * 
	 * @param nif
	 *            nif of the user
	 * @param name
	 *            name of the user
	 * @param surname
	 *            surname of the user
	 * @param phone
	 *            phone of the user
	 * @param password
	 *            password of the user
	 * @param email
	 *            e-mail of the passenger
	 */
	void updatePersonalData(String nif, String name, String surname, String phone, String email, String password);

	/**
	 * Check if exist the car that match the given conditions
	 * 
	 * @param carRegistrationId
	 *            id of the car to check
	 * @return boolean
	 */
	boolean existCar(String carRegistrationId);

	/**
	 * Check if exist the driver that match the given conditions
	 * 
	 * @param nif
	 *            nif of driver to check
	 * @return boolean
	 */
	boolean existDriver(String nif);

	/**
	 * Check if exist the passenger that match the given conditions
	 * 
	 * @param nif
	 *            nif of passenger to check
	 * @return boolean
	 */
	boolean existPassenger(String nif);

	/**
	 * Find the driver that match the given conditions
	 * 
	 * @param nif
	 *            nif of the driver
	 * @return DriverJPA data of the driver
	 */
	DriverJPA findDriver(String nif);

	/**
	 * Find the passenger that match the given conditions
	 * 
	 * @param nif
	 *            nif of the passenger
	 * @return PassengerJPA data of the passenger
	 */
	PassengerJPA findPassenger(String nif);

	/**
	 * Check if the email is used
	 * 
	 * @param email
	 *            email to check
	 * @return boolean
	 */
	boolean isEmailUsed(String email);
}
