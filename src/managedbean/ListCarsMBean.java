
package managedbean;

import java.io.Serializable;
import java.util.Collection;
import java.util.Properties;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.Context;
import javax.naming.InitialContext;

import ejb.UserFacadeRemote;
import jpa.CarJPA;
import jpa.UserDTO;

/**
 * Managed Bean ListCarsMBean
 *
 * @author Joaquín Paredes Ribera - jparedesr@uoc.edu
 *
 */
@ManagedBean(name = "cars")
@ViewScoped
public class ListCarsMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private UserFacadeRemote carsRemote;

	private UserDTO userLogged = SessionBean.getLoggedUser();
	private String nif = userLogged.getId();

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

	public Collection<CarJPA> getCarsList() throws Exception {
		return this.carList();
	}

	public void setCarsList(Collection<CarJPA> carsList) {
		this.carsList = carsList;
	}

	public String addCar() {
		return "addCarView";
	}

	/**
	 * Method that gets a list of instances all CarJPA
	 * 
	 * @return Collection<CarJPA>
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private Collection<CarJPA> carList() throws Exception {
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		carsRemote = (UserFacadeRemote) ctx.lookup("java:app/CAT-PDP-GRUP6.jar/UserFacadeBean!ejb.UserFacadeRemote");
		carsList = (Collection<CarJPA>) carsRemote.listAllCars(nif);
		return carsList;
	}
}
