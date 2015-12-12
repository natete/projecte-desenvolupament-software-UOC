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
import jpa.TripJPA;

@ManagedBean(name="findMyTrips")
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
	
	
	public void findMyTrips() throws NamingException {
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		tripAdmFacadeRemote = (TripAdministrationFacadeRemote) ctx
				.lookup("java:app/CAT-PDP-GRUP6.jar/TripAdministrationFacadeBean!ejb.TripAdministrationFacadeRemote");
		trips = tripAdmFacadeRemote.findMyTrips(driver);
		if (trips == null || trips.isEmpty()) {
			searchMessage = EMPTY_LIST_MESSAGE;
		}
	}
}
