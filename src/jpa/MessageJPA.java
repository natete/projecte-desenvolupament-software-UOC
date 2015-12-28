package jpa;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * JPA Class MessageJPA
 */
@Entity
@Table(name = "message")
@NamedQueries({
		@NamedQuery(name = "MessageJPA.getQuestions", query = "SELECT m FROM MessageJPA m WHERE m.trip.id = :tripId AND m.question = NULL"),
		@NamedQuery(name = "MessageJPA.getMessageById", query = "SELECT m FROM MessageJPA m WHERE m.questionId = :questionId") })
public class MessageJPA implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int questionId;

	private String subject;

	private String body;

	@ManyToOne
	@JoinColumn(name = "tripId")
	private TripJPA trip;

	@ManyToOne
	@JoinColumn(name = "driverId")
	private DriverJPA driver;

	@ManyToOne
	@JoinColumn(name = "passengerId")
	private PassengerJPA passenger;

	@ManyToOne
	private MessageJPA question;

	@OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
	private List<MessageJPA> answers;

	/**
	 * Class constructor methods
	 */
	public MessageJPA() {
		super();
	}

	public MessageJPA(TripJPA trip, int questionId, DriverJPA driver, PassengerJPA passenger, String subject,
			String body) {
		this.trip = trip;
		this.questionId = questionId;
		this.driver = driver;
		this.passenger = passenger;
		this.subject = subject;
		this.body = body;

	}

	/**
	 * Methods get/set the fields of database
	 */

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
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

	public MessageJPA getQuestion() {
		return question;
	}

	public void setQuestion(MessageJPA question) {
		this.question = question;
	}

	public List<MessageJPA> getAnswers() {
		return answers;
	}

	public void setAnswers(List<MessageJPA> answers) {
		this.answers = answers;
	}

	public TripJPA getTrip() {
		return trip;
	}

	public void setTrip(TripJPA trip) {
		this.trip = trip;
	}

	public DriverJPA getDriver() {
		return driver;
	}

	public void setDriver(DriverJPA driver) {
		this.driver = driver;
	}

	public PassengerJPA getPassenger() {
		return passenger;
	}

	public void setPassenger(PassengerJPA passenger) {
		this.passenger = passenger;
	}

	@Transient
	public String getAuthor() {
		if (question == null) {
			if (passenger == null) {
				return "Unknown";
			} else {
				return passenger.getFullName();
			}
		} else {
			return driver.getFullName();
		}
	}

}
