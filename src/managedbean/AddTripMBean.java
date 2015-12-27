package managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.Context;
import javax.naming.InitialContext;

import ejb.TripAdministrationFacadeRemote;
import jpa.CarJPA;
import jpa.UserDTO;

/**
 * AddTripMBean
 * 
 * @author GRUP6 jordi-nacho-ximo-joan
 */
@ManagedBean(name = "addTripController")
@ViewScoped
public class AddTripMBean implements Serializable {

	private static final long serialVersionUID = 1L;
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
	private FacesMessage message;

	/**
	 * AddTripMBean Default Constructor.
	 */
	public AddTripMBean() {
		super();
	}

	/**
	 * Returns parameter description.
	 * 
	 * @return description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets parameter description.
	 * 
	 * @param description.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns parameter departureCity
	 * 
	 * @return departureCity.
	 */
	public String getDepartureCity() {
		return departureCity;
	}

	/**
	 * Sets parameter departureCity.
	 * 
	 * @param departureCity.
	 */
	public void setDepartureCity(String departureCity) {
		this.departureCity = departureCity;
	}

	/**
	 * Returns parameter fromPlace.
	 * 
	 * @return fromPlace.
	 */
	public String getFromPlace() {
		return fromPlace;
	}

	/**
	 * Sets parameter fromPlace.
	 * 
	 * @param fromPlace.
	 */
	public void setFromPlace(String fromPlace) {
		this.fromPlace = fromPlace;
	}

	/**
	 * Returns parameter departureDate.
	 * 
	 * @return departureDate.
	 */
	public Date getDepartureDate() {
		return departureDate;
	}

	/**
	 * Sets parameter departureDate.
	 * 
	 * @param departureDate.
	 */
	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	/**
	 * Returns parameter deparutureTime.
	 * 
	 * @return deparutureTime.
	 */
	public Date getDepartureTime() {
		return departureTime;
	}

	/**
	 * Sets parameter departureTime.
	 * 
	 * @param departureTime.
	 */
	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}

	/**
	 * Returns parameter arrivalCity.
	 * 
	 * @return arrivalCity.
	 */
	public String getArrivalCity() {
		return arrivalCity;
	}

	/**
	 * Sets parameter arrivalCity.
	 * 
	 * @param arrivalCity.
	 */
	public void setArrivalCity(String arrivalCity) {
		this.arrivalCity = arrivalCity;
	}

	/**
	 * Returns parameter toPlace.
	 * 
	 * @return toPlace.
	 */
	public String getToPlace() {
		return toPlace;
	}

	/**
	 * Sets parameter toPlace.
	 * 
	 * @param toPlace.
	 */
	public void setToPlace(String toPlace) {
		this.toPlace = toPlace;
	}

	/**
	 * Returns parameter availableSeats.
	 * 
	 * @return availableSeats.
	 */
	public int getAvailableSeats() {
		return availableSeats;
	}

	/**
	 * Sets parameter availableSeats.
	 * 
	 * @param availableSeats.
	 */
	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}

	/**
	 * Returns parameter price.
	 * 
	 * @return price.
	 */
	public float getPrice() {
		return price;
	}

	/**
	 * Sets parameter price.
	 * 
	 * @param price.
	 */
	public void setPrice(float price) {
		this.price = price;
	}

	/**
	 * Returns parameter carSelected, a parameter representing a car in the
	 * database, it is formatted as a String with the brand, model and color of
	 * the car.
	 * 
	 * @return carSelected.
	 */
	public String getCarSelected() {
		return carSelected;
	}

	/**
	 * Sets parameter carSelected, a parameter representing a car in the
	 * database, it is formatted as a String with the brand, model and color of
	 * the car.
	 * 
	 * @param carSelected.
	 */
	public void setCarSelected(String carSelected) {
		this.carSelected = carSelected;
	}

	/**
	 * Adds a trip in carsharing.trip table. This method first validates entry
	 * fields from <b>addTripView.xhtm</b> and then calls addTrip in
	 * TripAdmnistrationFacadeRemote interface to add the trip.
	 * 
	 * @return initial page if everything went find, elsewhere it returns an
	 *         errorMessage.
	 * @throws Exception.
	 */
	public String addTrip() throws Exception {
		String errorMessage = null;

		Properties properties = System.getProperties();
		Context context = new InitialContext(properties);

		tripAdmRemote = (TripAdministrationFacadeRemote) context
				.lookup("java:app/CAT-PDP-GRUP6.jar/TripAdministrationFacadeBean!ejb.TripAdministrationFacadeRemote");
		/*
		 * tripAdmRemote = (TripAdministrationFacadeRemote) context.lookup(
		 * "java:app/CAT-PDP-GRUP6.jar/TripAdministrationFacadeBean!ejb.TripAdministrationFacadeRemote"
		 * ); /* Pattern pattern = Pattern.compile("^[a-zA-Z]+( *[a-zA-Z])*$");
		 * 
		 * Matcher matcher = pattern.matcher(this.departureCity); if
		 * (!matcher.matches()) { errorMessage =
		 * "You must enter a Departure City, without extra characters"; message
		 * = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage,
		 * errorMessage);
		 * FacesContext.getCurrentInstance().addMessage("form:errorView",
		 * message); }
		 * 
		 * matcher = pattern.matcher(this.fromPlace); if (!matcher.matches()) {
		 * errorMessage =
		 * "You must enter a Departure Place, without extra characters"; message
		 * = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage,
		 * errorMessage);
		 * FacesContext.getCurrentInstance().addMessage("form:errorView",
		 * message); }
		 * 
		 * matcher = pattern.matcher(this.arrivalCity); if (!matcher.matches())
		 * { errorMessage =
		 * "You must enter an Arrival City, without extra characters"; message =
		 * new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage,
		 * errorMessage);
		 * FacesContext.getCurrentInstance().addMessage("form:errorView",
		 * message); }
		 * 
		 * matcher = pattern.matcher(this.toPlace); if (!matcher.matches()) {
		 * errorMessage =
		 * "You must enter an Arrival Place, without extra characters"; message
		 * = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage,
		 * errorMessage);
		 * FacesContext.getCurrentInstance().addMessage("form:errorView",
		 * message); }
		 * 
		 * Date date = new Date(); Integer thisDay = date.getDate(); Integer
		 * thisMonth = date.getMonth(); // add 1 to month due to Month starts
		 * counting from 0; thisMonth++; Integer thisYear = date.getYear(); //
		 * add 1900 to year due to Year starts counting since 1900; thisYear +=
		 * 1900;
		 * 
		 * Integer depDay = this.departureDate.getDate(); Integer depMonth =
		 * this.departureDate.getMonth(); depMonth++; Integer depYear =
		 * this.departureDate.getYear(); depYear += 1900;
		 * 
		 * if (thisYear.shortValue() == depYear.shortValue()) { if
		 * (thisMonth.shortValue() < depMonth.shortValue()) ; if
		 * (thisMonth.shortValue() == depMonth.shortValue()) { if
		 * (thisDay.shortValue() < depDay.shortValue()) ; if
		 * (thisDay.shortValue() == depDay.shortValue()) { errorMessage =
		 * "You must enter a Date that matches as from Tomorrow (DAY)"; message
		 * = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage,
		 * errorMessage);
		 * FacesContext.getCurrentInstance().addMessage("form:errorView",
		 * message); } if (thisDay.shortValue() > depDay.shortValue()) {
		 * errorMessage =
		 * "You must enter a Date that matches as from Tomorrow (DAY)"; message
		 * = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage,
		 * errorMessage);
		 * FacesContext.getCurrentInstance().addMessage("form:errorView",
		 * message); } } if (thisMonth.shortValue() > depMonth.shortValue()) {
		 * errorMessage =
		 * "You must enter a Date that matches as from Tomorrow (MONTH)";
		 * message = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage,
		 * errorMessage);
		 * FacesContext.getCurrentInstance().addMessage("form:errorView",
		 * message); } } if (thisYear.shortValue() > depYear.shortValue()) {
		 * errorMessage =
		 * "You must enter a Date that matches as from Tomorrow (YEAR)"; message
		 * = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage,
		 * errorMessage);
		 * FacesContext.getCurrentInstance().addMessage("form:errorView",
		 * message); }
		 * 
		 * SimpleDateFormat sdf = new SimpleDateFormat("hh:mm"); String depTime
		 * = sdf.format(this.departureTime.getTime()); pattern =
		 * Pattern.compile("([01]?[0-9]|2[0-3]):[0-5][0-9]"); matcher =
		 * pattern.matcher(depTime); if (!matcher.matches()) { errorMessage =
		 * "You must enter a Time as like 09:15"; message = new
		 * FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage,
		 * errorMessage);
		 * FacesContext.getCurrentInstance().addMessage("form:errorView",
		 * message); }
		 * 
		 * date = this.departureDate; Integer depHour = date.getHours(); if
		 * (depHour.intValue() < 0 && depHour.intValue() > 23) { errorMessage =
		 * "Hour must be between 00 and 23"; message = new
		 * FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage,
		 * errorMessage);
		 * FacesContext.getCurrentInstance().addMessage("form:errorView",
		 * message); } Integer depMins = date.getMinutes(); if
		 * (depMins.intValue() < 0 && depMins.intValue() > 59) { errorMessage =
		 * "Minuts must be between 00 and 59"; message = new
		 * FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage,
		 * errorMessage);
		 * FacesContext.getCurrentInstance().addMessage("form:errorView",
		 * message); }
		 * 
		 * if (errorMessage != null) { return "errorView";
		 * 
		 * } else {
		 * 
		 */
		UserDTO userDTO = SessionBean.getLoggedUser();
		String nif = userDTO.getId();

		tripAdmRemote.addTrip(description, departureCity, fromPlace, departureDate, departureTime, arrivalCity, toPlace,
				availableSeats, price, nif, carSelected);

		return "/pages/public/findTripsView";
	}

	/**
	 * Returns a list of cars from carsharing.car, representing all cars from a
	 * logged driver. The list is formatted as a String as <b>model brand
	 * color</b>.
	 * 
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
			brands.add(car.getBrand() + " " + car.getModel() + " " + car.getColor());
		}
		return brands;
	}

}
