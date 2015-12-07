
package managedbean;

import java.io.Serializable;
import java.util.*;

import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.PersistenceException;

import jpa.MessageJPA;
import ejb.CommunicationFacadeRemote;

/**
 * Managed Bean ShowTripCommentsMBean
 */
@ManagedBean(name = "tripcomments")
@SessionScoped
public class ShowTripCommentsMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private CommunicationFacadeRemote tripCommentsRemote;

	// stores the nif driver
	private String driverId;
	// stores the nif passenger
	private String passengerId;
	// stores all the instances of MessageJPA
	private Collection<MessageJPA> tripCommentsList;
	// stores the screen number where the user is
	private int screen = 0;
	// stores ten or fewer MessageJPA instances that the user can see on a screen
	protected Collection<MessageJPA> tripCommentsView;
	// stores the total number of instances of MessageJPA
	protected int numberMessages = 0;
	protected ShowTripMBean t;

	/**
	 * Constructor method
	 * 
	 * @throws Exception
	 */
	public ShowTripCommentsMBean() throws Exception {
		LoginMBean l = new LoginMBean(); 
		t = new ShowTripMBean();
	    this.setPassengerId(l.getPassengerId());
	    this.setDriverId(t.getdriverId());

	}

	/**
	 * Method that returns an instance Collection of 10 or less MessageJPA according
	 * screen where the user is.
	 * 
	 * @return Collection MessageJPA
	 */
	public Collection<MessageJPA> getTripcommentsListView() throws Exception {
		int n = 0;
		tripCommentsView = new ArrayList<MessageJPA>();
		this.tripCommentsList();
		for (Iterator<MessageJPA> iter2 = tripCommentsList.iterator(); iter2.hasNext();) {
			MessageJPA driverComment = (MessageJPA) iter2.next();
			if (n >= screen * 10 && n < (screen * 10 + 10)) {
				this.tripCommentsView.add(driverComment);
			}
			n += 1;
		}
		this.numberMessages = n;
		return tripCommentsView;
	}

	/**
	 * Returns the total number of instances of MessageJPA
	 * 
	 * @return Car number
	 */
	public int getNumberMessages() {
		return this.numberMessages;
	}

	/**
	 * allows forward or backward in user screens
	 */
	public void nextScreen() {
		if (((screen + 1) * 10 < tripCommentsList.size())) {
			screen += 1;
		}
	}

	public void previousScreen() {
		if ((screen > 0)) {
			screen -= 1;
		}
	}

	public String getDriverId() {
		return this.driverId;
	}

	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}
	
	public String getPassengerId() {
		return this.passengerId;
	}

	public void setPassengerId(String passengerId) {
		this.passengerId = passengerId;
	}

	/**
	 * Method used for Facelet to call tripCommentsView Facelet
	 * 
	 * @return Facelet name
	 * @throws Exception
	 */
	public String showTripComments() throws Exception {
		tripCommentsList();
		return "tripCommentsView";
	}

	/**
	 * Method used for Facelet to call askQuestion Facelet
	 * 
	 * @return Facelet name
	 */
	public String askQuestion() {
		return "askQuestionView";
	}

	/**
	 * Method that gets a list of instances all MessageJPA
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void tripCommentsList() throws Exception {
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		screen = 0;
		tripCommentsRemote = (CommunicationFacadeRemote) ctx.lookup("java:app/CAT-PDP-GRUP6.jar/CommunicationFacadeBean!ejb.CommunicationFacadeRemote");
		tripCommentsList = (Collection<MessageJPA>) tripCommentsRemote.showTripComments(t.getTripId());
	}
}
