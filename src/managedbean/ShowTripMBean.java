
package managedbean;

import java.io.Serializable;
import java.util.*;

import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.naming.Context;
import javax.naming.InitialContext;

import jpa.DriverCommentJPA;
import ejb.CommunicationFacadeRemote;

/**
 * Managed Bean ShowDriverCommentsMBean
 */
@ManagedBean(name = "trip")
@SessionScoped
public class ShowTripMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private CommunicationFacadeRemote driverCommentsRemote;

	// stores the name of the category of cars to be displayed
	private String driverId;
	// stores the name of the category of cars to be displayed
	private String driverName;

	

	/**
	 * Constructor method
	 * 
	 * @throws Exception
	 */
	public ShowTripMBean() throws Exception {
		driverId = "00000000X" ;
		driverName = "Carlos Sainz";

	}

	/**
	 * Method that returns an instance Collection of 10 or less DriverCommentJPA according
	 * screen where the user is.
	 * 
	 * @return Collection DriverCommentJPA
	 */
	
	public String getdriverId() {
		return this.driverId;
	}

	public void setdriverId(String driverId) {
		this.driverId = driverId;
	}
	
	public String getdriverName() {
		return this.driverName;
	}

	public void setdriverName(String driverName) {
		this.driverName = driverName;
	}
	
}
