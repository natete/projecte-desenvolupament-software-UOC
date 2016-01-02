
package managedbean;

import java.io.Serializable;
import java.util.Properties;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ejb.CommunicationFacadeRemote;
import jpa.DriverJPA;
import jpa.MessageJPA;
import jpa.TripJPA;
import jpa.UserDTO;

/**
 * Managed Bean ReplyQuestion
 */
@ManagedBean(name = "replyquestion")
@ViewScoped
public class ReplyQuestionMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private CommunicationFacadeRemote replyQuestionRemote;

	private int questionId;
	private int tripId;
	private String answer;
	private TripJPA trip;
	private MessageJPA question;
	private DriverJPA driver;
	private UserDTO loggedUser;

	/**
	 * Constructor method
	 * 
	 * @throws Exception
	 */
	public ReplyQuestionMBean() throws Exception {
		super();
	}

	/**
	 * Method to make the init operations
	 * 
	 * @throws NamingException
	 */
	public void init() throws NamingException {
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		replyQuestionRemote = (CommunicationFacadeRemote) ctx
				.lookup("java:app/CAT-PDP-GRUP6.jar/CommunicationFacadeBean!ejb.CommunicationFacadeRemote");

		loggedUser = SessionBean.getLoggedUser();

		driver = null;
		if (loggedUser != null && loggedUser.isDriver()) {
			driver = (DriverJPA) replyQuestionRemote.findDriver(loggedUser.getId());
		}

		question = (MessageJPA) replyQuestionRemote.findMessage(questionId);
		trip = question.getTrip();
	}

	/**
	 * Method to get the QuestionId
	 * @return questionId
	 */
	public int getQuestionId() {
		return this.questionId;
	}

	/**
	 * Method to set the questionId
	 * @param questionId
	 */
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	/**
	 * Method to get the TripId
	 * @return tripId
	 */
	public int getTripId() {
		return this.tripId;
	}

	/**
	 * Method to set the tripId
	 * @param tripId
	 */
	public void setTripId(int tripId) {
		this.tripId = tripId;
	}

	/**
	 * Method to get the name of the driver
	 * @return name + surname
	 */
	public String getDriver() {
		return this.question.getTrip().getDriver().getName() + " " + this.question.getTrip().getDriver().getSurname();
	}

	/**
	 * Method to get the subject
	 * @return subject
	 */
	public String getSubject() {
		return question.getSubject();
	}

	/**
	 * Method to get the body
	 * @return body
	 */
	public String getBody() {
		return question.getBody();
	}

	/**
	 * Method to get the answer
	 * @return answer
	 */
	public String getAnswer() {
		return answer;
	}

	/**
	 * Method to set the answer
	 * @param answer
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}

	/**
	 * Method to get the author of the question
	 * @return author of the question
	 */
	public String getAuthor() {
		return question.getAuthor();
	}

	/**
	 * Method to set Data Trip Comment
	 * @return to tripCommenstView
	 * @throws Exception
	 */
	public String setDataTripComment() throws Exception {
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		replyQuestionRemote = (CommunicationFacadeRemote) ctx
				.lookup("java:app/CAT-PDP-GRUP6.jar/CommunicationFacadeBean!ejb.CommunicationFacadeRemote");

		replyQuestionRemote.replyQuestion(this.trip.getId(), question.getQuestionId(), driver.getNif(),
				question.getSubject(), answer);
		return "tripCommentsView";
	}
}
