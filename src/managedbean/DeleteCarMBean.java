
package managedbean;

import java.io.Serializable;
import java.util.Properties;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.Context;
import javax.naming.InitialContext;

import ejb.UserFacadeRemote;

/**
 * Managed Bean DeleteCarMBean
 * 
 * @author Joaquín Paredes Ribera - jparedesr@uoc.edu
 *
 */
@ManagedBean(name = "deletecar")
@SessionScoped
public class DeleteCarMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private UserFacadeRemote deleteCarRemote;

	private String errorMessage;
	private FacesMessage message;

	/**
	 * Constructor method
	 * 
	 * @throws Exception
	 */
	public DeleteCarMBean() throws Exception {

	}

	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * Method that delete a car of the driver
	 * 
	 * @param carRegistrationId
	 *            id of the car
	 * @return String
	 * 
	 */
	public String deleteCar(String carRegistrationId) throws Exception {

		String result;

		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		deleteCarRemote = (UserFacadeRemote) ctx
				.lookup("java:app/CAT-PDP-GRUP6.jar/UserFacadeBean!ejb.UserFacadeRemote");
		if (deleteCarRemote.carHasTrips(carRegistrationId)) {
			errorMessage = "This car has associated trips";
			result = "errorView";
		} else {
			deleteCarRemote.deleteCar(carRegistrationId);
			result = "carListView";
		}

		return result;
	}
}
