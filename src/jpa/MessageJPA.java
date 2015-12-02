package jpa;
import java.io.Serializable;

import javax.persistence.*;

/**
 * JPA Class MessageJPA
 */
@Entity
@Table(name="message")
public class MessageJPA implements Serializable {

	private static final long serialVersionUID = 1L;

	private int tripId;
	private String driverId;
	private String passengerId;
	private int questionId;
	private int replyQuestionId;
	private String subject;
	private String body;
	private TripJPA trip;
	private DriverJPA driver;
	private PassengerJPA passenger;
	
		
	/**
	 * Class constructor methods
	 */
	public MessageJPA() {
		super();
	}
	public MessageJPA(TripJPA trip, int questionId, DriverJPA driver, PassengerJPA passenger, int replyQuestionId, String subject, String body) {		
		this.trip= trip;
		this.questionId = questionId;
		this.driver = driver;
		this.passenger = passenger;
		this.replyQuestionId = replyQuestionId;
		this.subject = subject;
		this.body = body;
		
	}
	

	/**
	 *  Methods get/set the fields of database
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public int getReplyQuestionId() {
		return replyQuestionId;
	}
	public void setReplyQuestionId(int replyQuestionId) {
		this.replyQuestionId = replyQuestionId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * Methods get/set persistent relationships
	 */
	@ManyToOne
	@JoinColumn (name="tripId")
	public TripJPA getTrip() {
		return trip;
	}
	public  void setTrip(TripJPA trip) {
		this.trip = trip;
	}
	
	@ManyToOne
	@JoinColumn (name="driverId")
	public DriverJPA getDriver() {
		return driver;
	}
	public  void setDriver(DriverJPA driver) {
		this.driver = driver;
	}
	
	@ManyToOne
	@JoinColumn (name="passengerId")
	public PassengerJPA getPassenger() {
		return passenger;
	}
	public  void setPassenger(PassengerJPA passenger) {
		this.passenger = passenger;
	}
}
