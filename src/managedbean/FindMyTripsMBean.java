package managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ejb.TripAdministrationFacadeRemote;
import jpa.TripJPA;
import jpa.TripsDTO;
import jpa.UserDTO;

/**
 * FindMyTripsMBean
 * 
 * @author GRUP6 jordi-nacho-ximo-joan
 */
@ManagedBean(name = "findMyTripsController")
@ViewScoped
public class FindMyTripsMBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final int PAGE_SIZE = 10;
	private static final String NO_SEARCH_RESULTS = "<i class='fa fa-times'></i>&nbsp;You have no trips assigned.";

	@EJB
	private TripAdministrationFacadeRemote tripAdmFacadeRemote;

	private UserDTO logedUser;
	private String driverId;
	private String driverName;
	private Integer tripId;
	private int currentPage;
	private List<Integer> pages;
	private List<TripJPA> trips;
	private Long totalResults;
	private String searchMessage = "";

	/**
	 * FindMyTripsMBean Default Constructor.
	 * 
	 * @throws NamingException
	 */
	public FindMyTripsMBean() throws NamingException {
		super();
		currentPage = 1;
		totalResults = 0L;
		pages = new ArrayList<Integer>();

		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		tripAdmFacadeRemote = (TripAdministrationFacadeRemote) ctx
				.lookup("java:app/CAT-PDP-GRUP6.jar/TripAdministrationFacadeBean!ejb.TripAdministrationFacadeRemote");
		findTrips(currentPage);
	}

	/**
	 * Returns parameter logedUser via SessionBean.
	 * 
	 * @return logedUser.
	 */
	public UserDTO getLogedUser() {
		logedUser = SessionBean.getLoggedUser();
		return logedUser;
	}

	/**
	 * Returns parameter tripId.
	 * 
	 * @return tripId.
	 */
	public Integer getTripId() {
		return tripId;
	}

	/**
	 * Sets parameter tripId.
	 * 
	 * @param tripId.
	 */
	public void setTripId(Integer tripId) {
		this.tripId = tripId;
	}

	/**
	 * Sets parameter logeUser via SessionBean.
	 * 
	 * @param logedUser.
	 */
	public void setLogedUser(UserDTO logedUser) {
		this.logedUser = logedUser;
	}

	/**
	 * Returns parameter driverId.
	 * 
	 * @return driverId.
	 */
	public String getDriverId() {
		driverId = getLogedUser().getId();
		return driverId;
	}

	/**
	 * Sets parameter driverId.
	 * 
	 * @param driverId.
	 */
	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}

	/**
	 * Returns parameter driverName.
	 * 
	 * @return driverName.
	 */
	public String getDriverName() {

		return driverName;
	}

	/**
	 * Sets parameter driverName.
	 * 
	 * @param driverName.
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	/**
	 * Returns a list of trips.
	 * 
	 * @return List&lt;TripJPA&gt;.
	 */
	public List<TripJPA> getTrips() {
		return trips;
	}

	/**
	 * Sets a list of trips.
	 * 
	 * @param List&lt;trips&gt;.
	 */
	public void setTrips(List<TripJPA> trips) {
		this.trips = trips;
	}

	/**
	 * Returns a searchMessage in case of error.
	 * 
	 * @return String searchMessage.
	 */
	public String getSearchMessage() {
		return searchMessage;
	}

	/**
	 * Returns the current page.
	 * 
	 * @return currentPage.
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * Sets the current page.
	 * 
	 * @param page.
	 */
	public void setCurrentPage(int page) {
		this.currentPage = page;
	}

	/**
	 * Returns the list of pages.
	 * 
	 * @return List&lt;Integer&gt;.
	 */
	public List<Integer> getPages() {
		return pages;
	}

	/**
	 * Returns the number of trips.
	 * 
	 * @return totalResults.
	 */
	public Long getTotalResults() {
		return totalResults;
	}

	/**
	 * Gets all trips owned by a logged user from carsharing.trip table. This
	 * method is used by getTripListView() to show on screen the passengers.
	 * Against the former method, this one makes queries to database by paging
	 * tables 10 10. It get the page number from view to know what page query to
	 * make.
	 * 
	 * @param int.
	 */
	public void findTrips(int page) throws NamingException {
		TripsDTO tripsDto = tripAdmFacadeRemote.findMyTrips(getDriverId(), page - 1);
		trips = tripsDto.getTrips();
		currentPage = page;
		if (trips == null || trips.isEmpty()) {
			searchMessage = NO_SEARCH_RESULTS;
		} else if (tripsDto.getTotal() != totalResults) {
			totalResults = tripsDto.getTotal();
			populatePagesList(tripsDto.getTotal());
		}
	}

	/**
	 * Auxiliary method to findTrips that adds pages. The method gets the number
	 * of fages according the mount of trips in the database.
	 * 
	 * @param total.
	 */
	private void populatePagesList(Long total) {
		pages.clear();
		for (int i = 0; i * PAGE_SIZE < total; i++) {
			pages.add(i + 1);
		}
	}
}