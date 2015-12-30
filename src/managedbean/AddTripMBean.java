package managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Iterator;
import ejb.TripAdministrationFacadeRemote;
import jpa.UserDTO;
import jpa.CarJPA;

/**
 * AddTripMBean
 * @author GRUP6 jordi-nacho-ximo-joan
 */
@ManagedBean(name="addTripController")
@SessionScoped
public class AddTripMBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private static final String EMPTY_LIST_MESSAGE = "<i class='fa fa-times'></i>"
			+ "No cars available. Please, first register a car";
	@EJB
	private TripAdministrationFacadeRemote tripAdmRemote;
	private String description;
	private String departureCity;
	private String fromPlace;
	private Date departureDate;
	private Date departureTime;
	private String arrivalCity;
	private String toPlace;
	private int availableSeats;
	private float price;
	private Collection<CarJPA> cars;
	private String carSelected;
	private String searchMessage = "";
	private Collection<CarJPA> myCars;
	
	/**
	 * AddTripMBean Default Constructor.
	 * @throws NamingException 
	 */
	public AddTripMBean() {
		super();
	}
	
	/**
	 * Returns parameter description.
	 * @return description.
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Sets parameter description.
	 * @param description.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Returns parameter departureCity
	 * @return departureCity.
	 */
	public String getDepartureCity() {
		return departureCity;
	}
	
	/**
	 * Sets parameter departureCity.
	 * @param departureCity.
	 */
	public void setDepartureCity(String departureCity) {
		this.departureCity = departureCity;
	}
	
	/**
	 * Returns parameter fromPlace.
	 * @return fromPlace.
	 */
	public String getFromPlace() {
		return fromPlace;
	}
	
	/**
	 * Sets parameter fromPlace.
	 * @param fromPlace.
	 */
	public void setFromPlace(String fromPlace) {
		this.fromPlace = fromPlace;
	}
	
	/**
	 * Returns parameter departureDate.
	 * @return departureDate.
	 */
	public Date getDepartureDate() {
		return departureDate;
	}
	
	/**
	 * Sets parameter departureDate.
	 * @param departureDate.
	 */
	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}
	
	/**
	 * Returns parameter deparutureTime.
	 * @return deparutureTime.
	 */
	public Date getDepartureTime() {
		return departureTime;
	}
	
	/**
	 * Sets parameter departureTime.
	 * @param departureTime.
	 */
	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}
	
	/**
	 * Returns parameter arrivalCity.
	 * @return arrivalCity.
	 */
	public String getArrivalCity() {
		return arrivalCity;
	}
	
	/**
	 * Sets parameter arrivalCity.
	 * @param arrivalCity.
	 */
	public void setArrivalCity(String arrivalCity) {
		this.arrivalCity = arrivalCity;
	}
	
	/**
	 * Returns parameter toPlace.
	 * @return toPlace.
	 */
	public String getToPlace() {
		return toPlace;
	}
	
	/**
	 * Sets parameter toPlace.
	 * @param toPlace.
	 */
	public void setToPlace(String toPlace) {
		this.toPlace = toPlace;
	}
	
	/**
	 * Returns parameter availableSeats.
	 * @return availableSeats.
	 */
	public int getAvailableSeats() {
		return availableSeats;
	}
	
	/**
	 * Sets parameter availableSeats.
	 * @param availableSeats.
	 */
	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}
	
	/**
	 * Returns parameter price.
	 * @return price.
	 */
	public float getPrice() {
		return price;
	}
	
	/**
	 * Sets parameter price.
	 * @param price.
	 */
	public void setPrice(float price) {
		this.price = price;
	}
	
	/**
	 * Sets parameter cars.
	 * @param cars
	 */
	public void setCars(Collection<CarJPA> cars) {
		this.cars = cars;
	}
	
	/**
	 * Returns parameter myCars.
	 * @return myCars.
	 * @throws NamingException 
	 */
	public Collection<CarJPA> getMyCars() throws NamingException {
		myCars = null;
		init();
		return myCars;
	}
	
	/**
	 * Sets parameter myCars.
	 * @param myCars.
	 */
	public void setMyCars(Collection<CarJPA> myCars) {
		this.myCars = myCars;
	}
	
	/**
	 * Returns parameter searchMessage.
	 * @return getSearchMessage.
	 */
	public String getSearchMessage() {
		return searchMessage;
	}

	/**
	 * Returns parameter carSelected,
	 * a parameter representing a car in
	 * the database, it is formatted as a
	 * String with the brand, model and
	 * color of the car.
	 * @return carSelected.
	 */
	public String getCarSelected() {
		return carSelected;
	}
	
	/**
	 * Sets parameter carSelected,
	 * a parameter representing a car in
	 * the database, it is formatted as a
	 * String with the brand, model and
	 * color of the car.
	 * @param carSelected.
	 */
	public void setCarSelected(String carSelected) {
		this.carSelected = carSelected;
	}
	
	/**
	 * Adds a trip in carsharing.trip table.
	 * This method first validates entry fields 
	 * from <b>addTripView.xhtm</b> and then 
	 * calls addTrip in TripAdmnistrationFacadeRemote 
	 * interface to add the trip. 
	 * @return initial page if everything went
	 * find, elsewhere it returns an errorMessage.
	 * @throws Exception.
	 */
	public String addTrip() throws Exception {
		
		Properties properties = System.getProperties();
		Context context = new InitialContext(properties);
		tripAdmRemote = (TripAdministrationFacadeRemote) context.lookup("java:app/CAT-PDP-GRUP6.jar/TripAdministrationFacadeBean!ejb.TripAdministrationFacadeRemote");
			
		UserDTO userDTO = SessionBean.getLoggedUser();
		String nif = userDTO.getId();
		
		tripAdmRemote.addTrip(description, departureCity, fromPlace, departureDate, departureTime, arrivalCity, toPlace, availableSeats, price, nif, carSelected);
		
		this.setDepartureCity("");
		this.setFromPlace("");
		this.setDepartureDate(null);
		this.setDepartureTime(null);
		this.setArrivalCity("");
		this.setToPlace("");
		this.setAvailableSeats(0);
		this.setPrice(0);
		
		return "/pages/public/findTripsView";
	}
	
	/**
	 * Returns a list of cars from carsharing.car,
	 * representing all cars from a logged driver.
	 * The list is formatted as a String as <b>model 
	 * brand color</b>.
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getCars() {
		UserDTO userDTO = SessionBean.getLoggedUser();
		String driverId = userDTO.getId();
		cars = null;
		cars = tripAdmRemote.getMyCars(driverId);
		ArrayList<String> brands = new ArrayList<String>();
		Iterator<CarJPA> it = cars.iterator();
		while (it.hasNext()) {
			CarJPA car = it.next();
			brands.add(car.getBrand()+" "+car.getModel()+" "+car.getColor());
		}
		return brands;
	}
	
	/**
	 * Methods needed to know if the logged 
	 * driver has a registered car. Thus is 
	 * called by the constructor.
	 * @throws NamingException
	 */
	private void init() throws NamingException {
		Properties properties = System.getProperties();
		Context context = new InitialContext(properties);
		tripAdmRemote = (TripAdministrationFacadeRemote) context.lookup("java:app/CAT-PDP-GRUP6.jar/TripAdministrationFacadeBean!ejb.TripAdministrationFacadeRemote");
		
		UserDTO userDTO = SessionBean.getLoggedUser();
		String nif = userDTO.getId();
		
		myCars = tripAdmRemote.getMyCars(nif);
		if (myCars.isEmpty() || myCars==null) {
			searchMessage = EMPTY_LIST_MESSAGE;
		}
	}
}