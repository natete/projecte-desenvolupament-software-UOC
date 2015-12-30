package ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import jpa.CarJPA;
import jpa.DriverJPA;
import jpa.PassengerJPA;
import jpa.UserDTO;
import jpa.UserJPA;

/**
 * Implementation of {@link UserFacadeRemote} that performs user related actions
 * 
 * @author Joaquín Paredes Ribera - jparedesr@uoc.edu
 *
 */
@Stateless
public class UserFacadeBean implements UserFacadeRemote {

	// Persistence Unit Context
	@PersistenceContext(unitName = "CarSharing")
	private EntityManager entman;

	private static final String QUERY_FIND_USER_BY_NIF = "UserJPA.getByNif";
	private static final String QUERY_FIND_USER_BY_EMAIL = "UserJPA.getByEmail";
	private static final String QUERY_FIND_USER_OTHER_USER = "UserJPA.existOtherUser";
	private static final String QUERY_FIND_DRIVER_BY_NIF = "DriverJPA.getByNif";
	private static final String QUERY_FIND_PASSENGER_BY_NIF = "PassengerJPA.getPassengerById";
	private static final String QUERY_INSERT_PASSENGER = "INSERT INTO carsharing.passenger(userid) VALUES (?)";
	private static final String QUERY_INSERT_DRIVER = "INSERT INTO carsharing.driver(userid) VALUES (?)";
	private static final String QUERY_FIND_CARS_BY_DRIVER_ID = "CarJPA.findCarsByDriverId";
	private static final String QUERY_FIND_CAR_BY_ID = "CarJPA.findCarById";
	private static final String QUERY_DRIVER_LOGIN = "DriverJPA.driverLogin";
	private static final String QUERY_PASSENGER_LOGIN = "PassengerJPA.passengerLogin";

	private static final String PARAMETER_NIF = "nif";
	private static final String PARAMETER_EMAIL = "email";
	private static final String PARAMETER_CAR_ID = "carRegistrationId";
	private static final String PARAMETER_PASSWORD = "password";

	/**
	 * @see UserFacadeRemote#addcar(String, String, String, String, String,
	 *      String)
	 */
	public void addCar(String nif, String carRegistrationId, String brand, String model, String color)
			throws PersistenceException {

		CarJPA car = new CarJPA();
		car.setCarRegistrationId(carRegistrationId);
		car.setBrand(brand);
		car.setModel(model);
		car.setColor(color);
		DriverJPA driverJPA = findDriver(nif);
		car.setDriver(driverJPA);
		try {
			entman.persist(car);

		} catch (PersistenceException e) {
			System.out.println(e);
		}
	}

	/**
	 * @see UserFacadeRemote#listAllCars(String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<CarJPA> listAllCars(String nif) throws PersistenceException {
		List<CarJPA> cars = entman.createNamedQuery(QUERY_FIND_CARS_BY_DRIVER_ID).setParameter(PARAMETER_NIF, nif)
				.getResultList();

		return cars;
	}

	/**
	 * @see UserFacadeRemote#carHasTrips(String)
	 */
	public boolean carHasTrips(String carRegistrationId) throws PersistenceException {
		CarJPA car = (CarJPA) entman.createNamedQuery(QUERY_FIND_CAR_BY_ID)
				.setParameter(PARAMETER_CAR_ID, carRegistrationId).getSingleResult();

		return !car.getTrips().isEmpty();
	}

	/**
	 * @see UserFacadeRemote#deleteCar(String)
	 */
	public void deleteCar(String carRegistrationId) throws PersistenceException {

		try {
			CarJPA car = (CarJPA) entman.createNamedQuery(QUERY_FIND_CAR_BY_ID)
					.setParameter(PARAMETER_CAR_ID, carRegistrationId).getSingleResult();
			entman.remove(car);
		} catch (PersistenceException e) {
			System.out.println(e);
		}
	}

	/**
	 * @see UserFacadeRemote#registerDriver(String, String, String, String,
	 *      String, String)
	 */
	public void registerDriver(String nif, String name, String surname, String phone, String password, String email)
			throws PersistenceException {
		UserJPA user = findUser(nif);
		if (user == null) {
			DriverJPA driver = new DriverJPA();
			driver.setNif(nif);
			driver.setName(name);
			driver.setSurname(surname);
			driver.setPhone(phone);
			driver.setPassword(password);
			driver.setEmail(email);
			try {
				entman.persist(driver);

			} catch (PersistenceException e) {
				System.out.println(e);
			}
		} else {
			Query query = entman.createNativeQuery(QUERY_INSERT_DRIVER);
			query.setParameter(1, nif);
			query.executeUpdate();
		}
	}

	/**
	 * @see UserFacadeRemote#reigsterPassenger(String, String, String, String,
	 *      String, String)
	 */
	public void registerPassenger(String nif, String name, String surname, String phone, String password, String email)
			throws PersistenceException {
		UserJPA user = findUser(nif);
		if (user == null) {
			PassengerJPA passenger = new PassengerJPA();
			passenger.setNif(nif);
			passenger.setName(name);
			passenger.setSurname(surname);
			passenger.setPhone(phone);
			passenger.setPassword(password);
			passenger.setEmail(email);
			try {
				entman.persist(passenger);

			} catch (PersistenceException e) {
				System.out.println(e);
			}
		} else {
			Query query = entman.createNativeQuery(QUERY_INSERT_PASSENGER);
			query.setParameter(1, nif);
			query.executeUpdate();
		}
	}

	/**
	 * @see UserFacadeRemote#login(String, String)
	 */
	public UserDTO login(String email, String password) throws PersistenceException {

		UserDTO user = null;

		PassengerJPA passenger = passengerLogin(email, password);
		DriverJPA driver = driverLogin(email, password);

		if (driver != null) {
			user = new UserDTO(driver.getName() + " " + driver.getSurname(), driver.getNif(), driver.getEmail());
			user.addRole(UserDTO.Role.DRIVER);
			if (driver != null && passenger != null) {
				user.addRole(UserDTO.Role.PASSENGER);
			}
		}

		if (driver == null && passenger != null) {
			user = new UserDTO(passenger.getName() + " " + passenger.getSurname(), passenger.getNif(),
					passenger.getEmail());
			user.addRole(UserDTO.Role.PASSENGER);
		}

		return user;
	}

	private DriverJPA driverLogin(String email, String password) {
		try {
			entman.clear();
			Query query = entman.createNamedQuery(QUERY_DRIVER_LOGIN);
			query.setParameter(PARAMETER_EMAIL, email);
			query.setParameter(PARAMETER_PASSWORD, password);
			DriverJPA driver = (DriverJPA) query.getSingleResult();
			return driver;
		} catch (NoResultException e) {
			return null;
		}

	}

	private PassengerJPA passengerLogin(String email, String password) {

		try {
			entman.clear();
			Query query = entman.createNamedQuery(QUERY_PASSENGER_LOGIN);
			query.setParameter(PARAMETER_EMAIL, email);
			query.setParameter(PARAMETER_PASSWORD, password);
			PassengerJPA passenger = (PassengerJPA) query.getSingleResult();
			return passenger;
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * @see UserFacadeRemote#findUser(String)
	 */
	public UserJPA findUser(String nif) {

		Query query = entman.createNamedQuery(QUERY_FIND_USER_BY_NIF);
		query.setParameter(PARAMETER_NIF, nif);
		try {
			return (UserJPA) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * @see UserFacadeRemote#existUser(UserJPA)
	 */
	public boolean existUser(UserJPA user) {
		Query query = entman.createNamedQuery(QUERY_FIND_USER_OTHER_USER);
		query.setParameter(PARAMETER_NIF, user.getNif());
		query.setParameter(PARAMETER_EMAIL, user.getEmail());

		try {
			query.getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}

	/**
	 * @see UserFacadeRemote#updatePersonalData(String, String, String, String,
	 *      String, String)
	 */
	public void updatePersonalData(String nif, String name, String surname, String phone, String email,
			String password) {
		Query query = entman.createNamedQuery(QUERY_FIND_USER_BY_NIF);
		query.setParameter(PARAMETER_NIF, nif);

		UserJPA user = (UserJPA) query.getSingleResult();

		user.setName(name);
		user.setSurname(surname);
		user.setPhone(phone);
		user.setEmail(email);
		user.setPassword(password);
		entman.merge(user);
	}

	/**
	 * @see UserFacadeRemote#existCar(String)
	 */
	public boolean existCar(String carRegistrationId) throws PersistenceException {
		try {
			Query query = entman.createNamedQuery(QUERY_FIND_CAR_BY_ID);
			query.setParameter(PARAMETER_CAR_ID, carRegistrationId);
			query.getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}

	/**
	 * @see UserFacadeRemote#existDriver(String)
	 */
	public boolean existDriver(String nif) throws PersistenceException {
		Query query = entman.createNamedQuery(QUERY_FIND_DRIVER_BY_NIF);
		query.setParameter(PARAMETER_NIF, nif);

		try {
			query.getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}

	/**
	 * @see UserFacadeRemote#existPassenger(String)
	 */
	public boolean existPassenger(String nif) throws PersistenceException {
		Query query = entman.createNamedQuery(QUERY_FIND_PASSENGER_BY_NIF);
		query.setParameter(PARAMETER_NIF, nif);

		try {
			query.getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}

	/**
	 * @see UserFacadeRemote#findDriver(String)
	 */
	public DriverJPA findDriver(String nif) throws PersistenceException {
		Query query = entman.createNamedQuery(QUERY_FIND_DRIVER_BY_NIF);
		query.setParameter(PARAMETER_NIF, nif);

		try {
			return (DriverJPA) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * @see UserFacadeRemote#findPassenger(String)
	 */
	public PassengerJPA findPassenger(String nif) throws PersistenceException {
		Query query = entman.createNamedQuery(QUERY_FIND_PASSENGER_BY_NIF);
		query.setParameter(PARAMETER_NIF, nif);

		try {
			return (PassengerJPA) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * @see UserFacadeRemote#isEmailUsed(String)
	 */
	public boolean isEmailUsed(String email) {
		Query query = entman.createNamedQuery(QUERY_FIND_USER_BY_EMAIL);
		query.setParameter(PARAMETER_EMAIL, email);
		try {
			query.getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}
}
