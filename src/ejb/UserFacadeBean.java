package ejb;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import jpa.CarJPA;
import jpa.DriverJPA;
import jpa.PassengerJPA;
import jpa.UserDTO;

/**
 * EJB Session Bean Class
 */
@Stateless
public class UserFacadeBean implements UserFacadeRemote {

	// Persistence Unit Context
	@PersistenceContext(unitName = "CarSharing")
	private EntityManager entman;

	/**
	 * Method that adds a car
	 */
	public void addCar(String carRegistrationId, String brand, String model, String color, UserDTO driver) throws PersistenceException {

		CarJPA car = new CarJPA();
		car.setCarRegistrationId(carRegistrationId);
		car.setBrand(brand);
		car.setModel(model);
		car.setColor(color);
		String nif = driver.getId();
		DriverJPA driverJPA = (DriverJPA) entman.createQuery("FROM DriverJPA b WHERE b.nif = ?1")
				.setParameter(1, nif).getSingleResult();
		car.setDriver(driverJPA);
		try {
			entman.persist(car);

		} catch (PersistenceException e) {
			System.out.println(e);
		}
	}

	/**
	 * Method that returns Collection of all cars
	 */
	public java.util.Collection<?> listAllCars(String nif) throws PersistenceException {
		@SuppressWarnings("unchecked")
		Collection<CarJPA> cars = entman.createQuery("FROM CarJPA b WHERE b.driver.nif = :nif").setParameter("nif", nif)
				.getResultList();

		return cars;
	}

	/**
	 * Method that verify the existences of trips for car
	 */
	public boolean existsTripsByCar(String carRegistrationId) throws PersistenceException {
		@SuppressWarnings("unchecked")
		CarJPA car = (CarJPA) entman.createQuery("FROM CarJPA b WHERE b.carRegistrationId = ?1")
				.setParameter(1, carRegistrationId).getSingleResult();
		if (car.getTrips().isEmpty())
			return false;
		else
			return true;
	}

	/**
	 * Method that delete a instance of the class car
	 */
	public void deleteCar(String carRegistrationId) throws PersistenceException {
		
		try {
			entman.createQuery("DELETE FROM CarJPA b WHERE b.carRegistrationId = ?1").setParameter(1, carRegistrationId)
				.executeUpdate();

		} catch (PersistenceException e) {
			System.out.println(e);
		}
	}

	/**
	 * Method that adds a driver
	 */
	public void registerDriver(String nif, String name, String surname, String phone, String password, String email)
			throws PersistenceException {

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
	}

	/**
	 * Method that adds a passenger
	 */
	public void registerPassenger(String nif, String name, String surname, String phone, String password, String email)
			throws PersistenceException {

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
	}

	private DriverJPA driverLogin (String email, String password) {
		
		try {
			DriverJPA driver = (DriverJPA) entman.createNamedQuery("findDriver").setParameter("email", email)
				.setParameter("password", password).getSingleResult();
			return driver;
		} catch (NoResultException e){
			DriverJPA driver = null;
			return driver;
		}
			
	}

	private PassengerJPA passengerLogin (String email, String password) {
		
		try {
			PassengerJPA passenger = (PassengerJPA) entman.createNamedQuery("findPassenger").setParameter("email", email)
				.setParameter("password", password).getSingleResult();
			return passenger;
		} catch (NoResultException e){
			PassengerJPA passenger = null;
			return passenger;
		}
	}
	
	/**
	 * Method that find the user to login
	 */
	public UserDTO login(String email, String password) throws PersistenceException {

		UserDTO user = null;

		@SuppressWarnings("unchecked")
		
		DriverJPA driver = driverLogin(email, password);
		PassengerJPA passenger = passengerLogin(email, password);
		
		if (driver != null) {
			user = new UserDTO(driver.getName() + " " + driver.getSurname(), driver.getNif());
			user.addRole(UserDTO.Role.DRIVER);
			if (driver != null && passenger != null) {
				user.addRole(UserDTO.Role.PASSENGER);
			}
		}

		if (driver == null && passenger != null) {
			user = new UserDTO(passenger.getName() + " " + passenger.getSurname(), passenger.getNif());
			user.addRole(UserDTO.Role.PASSENGER);
		}

		
		return user;
	}

	/**
	 * Method that verify the existences of as car
	 */
	public boolean existsCar(String carRegistrationId) throws PersistenceException {
		@SuppressWarnings("unchecked")
		Collection<CarJPA> cars = entman.createQuery("FROM CarJPA b WHERE b.carRegistrationId = ?1")
				.setParameter(1, carRegistrationId).getResultList();
		if (cars.isEmpty())
			return false;
		else
			return true;
	}

	/**
	 * Method that verify the existences of as driver
	 */
	public boolean existsDriver(String nif, String email, UserDTO userLogged) throws PersistenceException {
		@SuppressWarnings("unchecked")
		Collection<DriverJPA> driversNif = entman.createQuery("FROM DriverJPA b WHERE b.nif = ?1").setParameter(1, nif)
			.getResultList();
		Collection<DriverJPA> driversEmail = entman.createQuery("FROM DriverJPA b WHERE b.email = ?2").setParameter(2, email)
			.getResultList();

		if (userLogged == null) {
			if (!(driversNif.isEmpty()) || !(driversEmail.isEmpty()))
				return true;
			else
				return false;
		} else {
			if (!(nif.equals(findDriver(nif).getNif())) || !(email.equals(findDriver(nif).getEmail()))) {
				if ((!(driversNif.isEmpty()) && !(nif.equals(findDriver(nif).getNif()))) || 
						(!(driversEmail.isEmpty())) && !(email.equals(findDriver(nif).getEmail())))
					return true;
				else
					return false;
			} else {
				return false;
			}
		}
	}

	/**
	 * Method that find a driver
	 */
	public DriverJPA findDriver(String nif) throws PersistenceException {
		@SuppressWarnings("unchecked")
		DriverJPA driver = (DriverJPA) entman.createQuery("FROM DriverJPA b WHERE b.nif = ?1").setParameter(1, nif)
			.getSingleResult();
		if (driver != null) {
			return driver;
		} else {
			return null;
		}
	}

	/**
	 * Method that verify the existences of as passenger
	 */
	public boolean existsPassenger(String nif, String email, UserDTO userLogged) throws PersistenceException {
		@SuppressWarnings("unchecked")
		Collection<PassengerJPA> passengersNif = entman.createQuery("FROM PassengerJPA b WHERE b.nif = ?1").setParameter(1, nif)
				.getResultList();
		Collection<PassengerJPA> passengersEmail = entman.createQuery("FROM PassengerJPA b WHERE b.email = ?2")
				.setParameter(2, email).getResultList();

		if (userLogged == null) {
			if (!(passengersNif.isEmpty()) || !(passengersEmail.isEmpty()))
				return true;
			else
				return false;
		} else {
			if (!(nif.equals(findPassenger(nif).getNif())) || !(email.equals(findPassenger(nif).getEmail()))) {
				if ((!(passengersNif.isEmpty()) && !(nif.equals(findPassenger(nif).getNif()))) || 
						(!(passengersEmail.isEmpty())) && !(email.equals(findPassenger(nif).getEmail())))
					return true;
				else
					return false;
			} else {
				return false;
			}
		}
	}

	/**
	 * Method that find a passenger
	 */
	public PassengerJPA findPassenger(String nif) throws PersistenceException {
		@SuppressWarnings("unchecked")
		PassengerJPA passenger = (PassengerJPA) entman.createQuery("FROM PassengerJPA b WHERE b.nif = ?1").setParameter(1, nif)
			.getSingleResult();
		if (passenger != null) {
			return passenger;
		} else {
			return null;
		}
	}

	/**
	 * Method that update a driver
	 */
	public void updateDriver(String nif, String name, String surname, String phone, String email, String password)
			throws PersistenceException {

		try {
			DriverJPA driver = entman.find(DriverJPA.class, nif);
			//entman.getTransaction().begin();
			driver.setName(name);
			driver.setSurname(surname);
			driver.setPhone(phone);
			driver.setEmail(email);
			driver.setPassword(password);
			//entman.getTransaction().commit();
			entman.persist(driver);
		} catch (PersistenceException e) {
			System.out.println(e);
		}
	}

	/**
	 * Method that adds a passenger
	 */
	public void updatePassenger(String nif, String name, String surname, String phone, String email, String password)
			throws PersistenceException {

		try {
			PassengerJPA passenger = entman.find(PassengerJPA.class, nif);
			//entman.getTransaction().begin();
			passenger.setName(name);
			passenger.setSurname(surname);
			passenger.setPhone(phone);
			passenger.setEmail(email);
			passenger.setPassword(password);
			//entman.getTransaction().commit();
			entman.persist(passenger);
		} catch (PersistenceException e) {
			System.out.println(e);
		}
	}

	/**
	 * Method that verify the existences of as Driver with some email
	 */
	public boolean existsDriverEmail(String nif, String name, String surname, String email, UserDTO userLogged) throws PersistenceException {
		@SuppressWarnings("unchecked")
		Collection<DriverJPA> driversNif = entman.createQuery("FROM DriverJPA b WHERE b.nif = ?1").setParameter(1, nif)
				.getResultList();
		Collection<DriverJPA> driversEmail = entman.createQuery("FROM DriverJPA b WHERE b.email = ?2").setParameter(2, email)
				.getResultList();

		if (userLogged == null) {
			if (!(driversEmail.isEmpty()) && driversNif.isEmpty()) {
				return true;
			} else {
				if (!(driversNif.isEmpty())) {
					DriverJPA driverNifArray[] = new DriverJPA[driversNif.size()];
					driverNifArray = driversNif.toArray(driverNifArray);
	
					for (DriverJPA driverNif : driverNifArray) {
						if (!(driverNif.getName().equals(name)) || !(driverNif.getSurname().equals(surname))) {
							return true;
						} else {
							return false;
						}
					} 
				} else {
					return false;
				}
			}
		} else {
			if (!(nif.equals(findDriver(nif).getNif())) || !(email.equals(findDriver(nif).getEmail()))) {
				if (!(driversEmail.isEmpty()) && driversNif.isEmpty()) {
					return true;
				} else {
					if (!(driversNif.isEmpty() && (!(name.equals(findDriver(nif).getName())) || !(surname.equals(findDriver(nif).getSurname()))))) {
						DriverJPA driverNifArray[] = new DriverJPA[driversNif.size()];
						driverNifArray = driversNif.toArray(driverNifArray);
		
						for (DriverJPA driverNif : driverNifArray) {
							if (!(driverNif.getName().equals(name)) || !(driverNif.getSurname().equals(surname))) {
								return true;
							} else {
								return false;
							}
						}
					} else {
						return false;
					}
				}
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * Method that verify the existences of as passenger with some email
	 */
	public boolean existsPassengerEmail(String nif, String name, String surname, String email, UserDTO userLogged) throws PersistenceException {
		@SuppressWarnings("unchecked")
		Collection<PassengerJPA> passengersNif = entman.createQuery("FROM PassengerJPA b WHERE b.nif = ?1").setParameter(1, nif)
				.getResultList();
		Collection<PassengerJPA> passengersEmail = entman.createQuery("FROM PassengerJPA b WHERE b.email = ?2").setParameter(2, email)
				.getResultList();

		if (userLogged == null) {
			if (!(passengersEmail.isEmpty()) && passengersNif.isEmpty()) {
				return true;
			} else {
				if (!(passengersNif.isEmpty())) {
					PassengerJPA passengerNifArray[] = new PassengerJPA[passengersNif.size()];
					passengerNifArray = passengersNif.toArray(passengerNifArray);
	
					for (PassengerJPA passengerNif : passengerNifArray) {
						if (!(passengerNif.getName().equals(name)) || !(passengerNif.getSurname().equals(surname))) {
							return true;
						} else {
							return false;
						}
					} 
				} else {
					return false;
				}
			}
		} else {
			if (!(nif.equals(findPassenger(nif).getNif())) || !(email.equals(findPassenger(nif).getEmail()))) {
				if (!(passengersEmail.isEmpty()) && passengersNif.isEmpty()) {
					return true;
				} else {
					if (!(passengersNif.isEmpty() && (!(name.equals(findPassenger(nif).getName())) || !(surname.equals(findPassenger(nif).getSurname()))))) {
						PassengerJPA passengerNifArray[] = new PassengerJPA[passengersNif.size()];
						passengerNifArray = passengersNif.toArray(passengerNifArray);
		
						for (PassengerJPA passengerNif : passengerNifArray) {
							if (!(passengerNif.getName().equals(name)) || !(passengerNif.getSurname().equals(surname))) {
								return true;
							} else {
								return false;
							}
						}
					} else {
						return false;
					}
				}
			} else {
				return false;
			}
		}
		return false;
	}	
}
