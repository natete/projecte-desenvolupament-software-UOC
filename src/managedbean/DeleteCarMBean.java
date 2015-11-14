
package managedbean;

import java.io.Serializable;
import java.util.*;

import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.naming.Context;
import javax.naming.InitialContext;

import ejb.UserFacadeRemote;

/**
 * Managed Bean DeleteCarMBean
 */
@ManagedBean(name = "deletecar")
@SessionScoped
public class DeleteCarMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
		
	@EJB
	private UserFacadeRemote deleteCarRemote;
	
	
	/**
	 * Constructor method
	 * @throws Exception
	 */
	public DeleteCarMBean() throws Exception
	{
		
	}
	
	
	public String deleteCar(String carRegistrationId) throws Exception
	{	
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		deleteCarRemote = (UserFacadeRemote) ctx.lookup("java:app/CarSharing.jar/UserFacadeBean!ejb.UserFacadeRemote");
		deleteCarRemote.deleteCar(carRegistrationId);
		return "carListView";
	}
}
