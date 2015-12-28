package jpa;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

@Entity
@Table(name = "trip")
@NamedQueries({ @NamedQuery(name = "TripJPA.getTripById", query = "SELECT t FROM TripJPA t " + "WHERE t.id = :tripId"),
		@NamedQuery(name = "TripJPA.findTripsByDriver", query = "SELECT t FROM TripJPA t "
				+ "WHERE t.driver.nif = :driverNif"),
		@NamedQuery(name = "TripJPA.updateTrip", query = "UPDATE TripJPA t SET "
				+ "t.description = :description, t.departureCity = :departureCity, "
				+ "t.fromPlace = :fromPlace, t.departureDate = :departureDate, "
				+ "t.departureTime = :departureTime, t.arrivalCity = :arrivalCity, "
				+ "t.toPlace = :toPlace, t.availableSeats = :availableSeats, t.price = :price, "
				+ "t.car = :myCar WHERE t.id = :tripId"),
		@NamedQuery(name = "TripJPA.findTripsByDriverDataBasePaging", query = "SELECT t FROM TripJPA t"), })
public class TripJPA implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Column(name = "description")
	@Size(min = 0, max = 250)
	private String description;

	@Column(name = "departureCity", nullable = false)
	@Size(min = 1, max = 50)
	private String departureCity;

	@Column(name = "fromPlace", nullable = false)
	@Size(min = 1, max = 80)
	private String fromPlace;

	@Column(name = "departureDate", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date departureDate;

	@Temporal(TemporalType.TIME)
	@Column(name = "departureTime", nullable = false)
	private Date departureTime;

	@Column(name = "arrivalCity", nullable = false)
	@Size(min = 1, max = 50)
	private String arrivalCity;

	@Column(name = "toPlace", nullable = false)
	@Size(min = 1, max = 80)
	private String toPlace;

	@Column(name = "availableSeats", nullable = false)
	private Integer availableSeats;

	@Column(name = "price", nullable = false)
	private float price;

	@ManyToOne
	@JoinColumn(name = "driver")
	private DriverJPA driver;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "passengers_trips", joinColumns = {
			@JoinColumn(name = "trip_id", nullable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "passenger_id", nullable = false) })
	private List<PassengerJPA> passengers;

	@ManyToOne
	@JoinColumn(name = "car")
	private CarJPA car;

	public TripJPA() {
		super();
	}

	public TripJPA(String description, String departureCity, String fromPlace, Date departureDate, Date departureTime,
			String arrivalCity, String toPlace, Integer availableSeats, float price) {
		super();

		this.description = description;
		this.departureCity = departureCity;
		this.fromPlace = fromPlace;
		this.departureDate = departureDate;
		this.departureTime = departureTime;
		this.arrivalCity = arrivalCity;
		this.toPlace = toPlace;
		this.availableSeats = availableSeats;
		this.price = price;
	}

	public Integer getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDepartureCity() {
		return departureCity;
	}

	public void setDepartureCity(String departureCity) {
		this.departureCity = departureCity;
	}

	public String getFromPlace() {
		return fromPlace;
	}

	public void setFromPlace(String fromPlace) {
		this.fromPlace = fromPlace;
	}

	public Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	public Date getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}

	public String getArrivalCity() {
		return arrivalCity;
	}

	public void setArrivalCity(String arrivalCity) {
		this.arrivalCity = arrivalCity;
	}

	public String getToPlace() {
		return toPlace;
	}

	public void setToPlace(String toPlace) {
		this.toPlace = toPlace;
	}

	public Integer getAvailableSeats() {
		return availableSeats;
	}

	public void setAvailableSeats(Integer availableSeats) {
		this.availableSeats = availableSeats;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public DriverJPA getDriver() {
		return driver;
	}

	public void setDriver(DriverJPA driver) {
		this.driver = driver;
	}

	public List<PassengerJPA> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<PassengerJPA> passengers) {
		this.passengers = passengers;
	}

	public CarJPA getCar() {
		return car;
	}

	public void setCar(CarJPA car) {
		this.car = car;
	}

	@Transient
	public boolean hasPassenger(String passengerId) {
		boolean result = false;
		if (passengers != null && !passengers.isEmpty()) {
			for (PassengerJPA passenger : passengers) {
				if (passenger.getNif().equals(passengerId)) {
					result = true;
					break;
				}
			}
		}
		return result;
	}

	@Transient
	public void addPassenger(PassengerJPA passenger) {
		passengers.add(passenger);
		availableSeats = availableSeats - 1;
	}

	@Transient
	public void removePassenger(PassengerJPA passenger) {
		passengers.remove(passenger);
		availableSeats = availableSeats + 1;
	}
}