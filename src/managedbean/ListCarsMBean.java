
package managedbean;

import java.io.Serializable;
import java.util.*;

import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.naming.Context;
import javax.naming.InitialContext;

import jpa.CarJPA;
import jpa.UserDTO;
import ejb.UserFacadeRemote;

/**
 * Managed Bean ListcarsMBean
 */
@ManagedBean(name = "cars")
@SessionScoped
public class ListCarsMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private UserFacadeRemote carsRemote;

	// stores the nif of the driver of cars to be displayed
	private UserDTO userLogged = SessionBean.getLoggedUser();
	private String nif = userLogged.getId();
	
	// stores all the instances of CarJPA
	private Collection<CarJPA> carsList;

	/**
	 * Constructor method
	 * 
	 * @throws Exception
	 */
	public ListCarsMBean() throws Exception {

	}
	
	public String getNif() {
		return this.nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public Collection<CarJPA> getCarsList() {
		return this.carsList;
	}

	public void setCarsList(Collection<CarJPA> carsList) {
		this.carsList = carsList;
	}

	/**
	 * Method used for Facelet to call addCarView Facelet
	 * 
	 * @return Facelet name
	 */
	public String addCar() {
		return "addCarView";
	}

	/**
	 * Method that gets a list of instances all CarJPA
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void carList() throws Exception {
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		carsRemote = (UserFacadeRemote) ctx.lookup("java:app/CAT-PDP-GRUP6.jar/UserFacadeBean!ejb.UserFacadeRemote");
		carsList = (Collection<CarJPA>) carsRemote.listAllCars(nif);
	}
}
