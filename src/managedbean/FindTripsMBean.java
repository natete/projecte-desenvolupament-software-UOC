package managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ejb.TripFacadeRemote;
import jpa.TripJPA;
import jpa.TripsDTO;

@ManagedBean(name = "findTripsController")
@ViewScoped
public class FindTripsMBean implements Serializable {

	private static final long serialVersionUID = -8313865001184025539L;

	private static final String NO_SEARCH_RESULTS = "<i class='fa fa-times'></i> Sorry we have no trips matching your search criteria right now...";
	private static final int PAGE_SIZE = 10;

	@EJB
	private TripFacadeRemote tripFacadeRemote;

	private String departureCity;
	private Date departureDate;
	private String arrivalCity;
	private List<TripJPA> trips;
	private int currentPage;
	private List<Integer> pages;
	private Long totalResults;
	private boolean isAdvancedSearch;
	private Date initialDate;
	private Date finalDate;
	private boolean hasSeats;
	private float minPrice;
	private float maxPrice;

	private String searchMessage = "";

	/**
	 * Basic constructor
	 */
	public FindTripsMBean() {
		super();
		currentPage = 1;
		pages = new ArrayList<>();
		isAdvancedSearch = false;
	}

	/**
	 * Method that performs basic search
	 * 
	 * @param page
	 *            the number of the required page
	 * @throws NamingException
	 */
	public void findTrips(int page) throws NamingException {
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		tripFacadeRemote = (TripFacadeRemote) ctx
				.lookup("java:app/CAT-PDP-GRUP6.jar/TripFacadeBean!ejb.TripFacadeRemote");
		TripsDTO tripsDto;
		if (isAdvancedSearch) {
			tripsDto = tripFacadeRemote.advancedSearch(departureCity, arrivalCity, initialDate, finalDate, minPrice,
					maxPrice, hasSeats, page - 1);
		} else {
			tripsDto = tripFacadeRemote.findTrips(departureCity, departureDate, arrivalCity, page - 1);
		}
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
	 * Updates the list of pages according the given total of results
	 * 
	 * @param total
	 *            the total of results
	 */
	private void populatePagesList(Long total) {
		pages.clear();
		for (int i = 1; i * PAGE_SIZE <= total + (10 - total % 10); i++) {
			pages.add(i);
		}
	}

	public String getDepartureCity() {
		return departureCity;
	}

	public void setDepartureCity(String departureCity) {
		this.departureCity = departureCity;
	}

	public Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	public String getArrivalCity() {
		return arrivalCity;
	}

	public void setArrivalCity(String arrivalCity) {
		this.arrivalCity = arrivalCity;
	}

	public List<TripJPA> getTrips() {
		return trips;
	}

	public void setTrips(List<TripJPA> trips) {
		this.trips = trips;
	}

	public String getSearchMessage() {
		return searchMessage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public List<Integer> getPages() {
		return pages;
	}

	public Long getTotalResults() {
		return totalResults;
	}

	public boolean getIsAdvancedSearch() {
		return isAdvancedSearch;
	}

	public void setAdvancedSearch(boolean isAdvancedSearch) {
		this.isAdvancedSearch = isAdvancedSearch;
	}

	public Date getInitialDate() {
		return initialDate;
	}

	public void setInitialDate(Date initialDate) {
		this.initialDate = initialDate;
	}

	public Date getFinalDate() {
		return finalDate;
	}

	public void setFinalDate(Date finalDate) {
		this.finalDate = finalDate;
	}

	public boolean getHasSeats() {
		return hasSeats;
	}

	public void setHasSeats(boolean hasSeats) {
		this.hasSeats = hasSeats;
	}

	public float getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(float minPrice) {
		this.minPrice = minPrice;
	}

	public float getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(float maxPrice) {
		this.maxPrice = maxPrice;
	}
}
