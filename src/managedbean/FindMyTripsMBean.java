package managedbean;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import ejb.TripAdministrationFacadeRemote;
import ejb.TripFacadeRemote;
import jpa.DriverJPA;
import jpa.TripJPA;
import jpa.UserDTO;

@ManagedBean(name="myTrips")
@ViewScoped

public class FindMyTripsMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private static final String EMPTY_LIST_MESSAGE = "<i class='fa fa-times'></i> Sorry we have no trips matching the driver right now...";
	private String searchMessage = "";
	
	@EJB
	private TripAdministrationFacadeRemote tripAdmFacadeRemote;
	
	private String driver;
	private Collection<TripJPA> trips;
	
	
	public FindMyTripsMBean() {
		super();
	}
	
	
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public Collection<TripJPA> getTrips() {
		return trips;
	}
	public void setTrips(Collection<TripJPA> trips) {
		this.trips = trips;
	}
	
	
	public Collection<TripJPA> findMyTrips() throws NamingException {
		
		UserDTO userDTO = SessionBean.getLoggedUser();
		driver = userDTO.getId();
		
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		tripAdmFacadeRemote = (TripAdministrationFacadeRemote) ctx
				.lookup("java:app/CAT-PDP-GRUP6.jar/TripAdministrationFacadeBean!ejb.TripAdministrationFacadeRemote");
		trips = tripAdmFacadeRemote.findMyTrips(driver);
		if (trips == null || trips.isEmpty()) {
			searchMessage = EMPTY_LIST_MESSAGE;
		}
		return trips;
	}
}
