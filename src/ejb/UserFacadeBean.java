package ejb;

import java.util.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import jpa.CarJPA;
import ejb.UserFacadeRemote;

/**
 * EJB Session Bean Class 
 */
@Stateless
public class UserFacadeBean implements UserFacadeRemote {
	
	//Persistence Unit Context
	@PersistenceContext(unitName="CarSharing") 
	private EntityManager entman;
   
	/**
	 * Method that adds a car
	 */
	public void addCar(String carRegistrationId, String brand, String model, String color) throws PersistenceException {

		CarJPA car = new CarJPA();
		car.setCarRegistrationId(carRegistrationId);
		car.setBrand(brand);
		car.setModel(model);
		car.setColor(color);
		try
		{
			entman.persist(car);
			
		}catch (PersistenceException e) {
			System.out.println(e);
		} 
	}	
	  
	
	/**
	 * Method that returns Collection of all cars
	 */
	public java.util.Collection<?> listAllCars(String nif) throws PersistenceException {
		@SuppressWarnings("unchecked")
		Collection<CarJPA> cars = entman.createQuery("FROM CarJPA b WHERE b.nif = :nif").setParameter("nif", nif).getResultList();
		
	    return cars;
	}
	  
	/**
	 * Method that delete a instance of the class car
	 */
	public void deleteCar(String carRegistrationId)throws PersistenceException {
		try
		{
			entman.createQuery("DELETE FROM CarJPA b WHERE b.carRegistrationId = ?1").setParameter(1, carRegistrationId).executeUpdate();
			
		}catch (PersistenceException e) {
			System.out.println(e);
		} 
	}	
	
	/**
	 * Method that verify the existences of as car
	 */
	public boolean existsCar(String carRegistrationId)throws PersistenceException {
		@SuppressWarnings("unchecked")
		Collection<CarJPA> cars = entman.createQuery("FROM CarJPA b WHERE b.carRegistrationId = ?1").setParameter(1, carRegistrationId).getResultList();
		if (cars.isEmpty()) return false; 
		else return true;
	}	
}
