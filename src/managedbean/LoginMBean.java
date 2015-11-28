
package managedbean;

import java.io.Serializable;
import java.util.*;

import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.naming.Context;
import javax.naming.InitialContext;

import ejb.CommunicationFacadeRemote;

/**
 * Managed Bean ShowpassengerCommentsMBean
 */
@ManagedBean(name = "login")
@SessionScoped
public class LoginMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private CommunicationFacadeRemote passengerCommentsRemote;

	// stores the name of the category of cars to be displayed
	private String passengerId; 
	// stores the name of the category of cars to be displayed
	private String passengerName;

	LoginMBean l;

	/**
	 * Constructor method
	 * 
	 * @throws Exception
	 */
	public LoginMBean() throws Exception {
		passengerId =  "22222222X";
		passengerName = "Luis Moya";
	}

	/**
	 * Method that returns an instance Collection of 10 or less passengerCommentJPA according
	 * screen where the user is.
	 * 
	 * @return Collection passengerCommentJPA
	 */
	
	public String getPassengerId() {
		return this.passengerId;
	}

	public void setPassengerId(String passengerId) {
		this.passengerId = passengerId;
	}
	
	public String getPassengerName() {
		return this.passengerName;
	}

	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}
	
}
