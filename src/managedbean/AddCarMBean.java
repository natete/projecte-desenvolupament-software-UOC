package managedbean;

import java.io.Serializable;
import java.util.Properties;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.Context;
import javax.naming.InitialContext;

import ejb.UserFacadeRemote;
import jpa.UserDTO;

/**
 * Managed Bean ShowPetMBean
 */
@ManagedBean(name = "addcar")
@SessionScoped
public class AddCarMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private UserFacadeRemote addCarRemote;

	private String carRegistrationId;
	private String brand;
	private String model;
	private String color;
	private UserDTO userLogged = SessionBean.getLoggedUser();

	private String errorMessage;
	private FacesMessage message;

	/**
	 * Constructor method
	 * 
	 * @throws Exception
	 */
	public AddCarMBean() throws Exception {

	}

	public String getCarRegistrationId() {
		return carRegistrationId;
	}

	public void setCarRegistrationId(String carRegistrationId) {
		this.carRegistrationId = carRegistrationId;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String setDataCar() throws Exception {

		String result;

		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		addCarRemote = (UserFacadeRemote) ctx.lookup("java:app/CAT-PDP-GRUP6.jar/UserFacadeBean!ejb.UserFacadeRemote");
		if (addCarRemote.existsCar(carRegistrationId) == true) {
			errorMessage = "Car registration Id already exists";
			result = "errorView";
		} else {
			addCarRemote.addCar(carRegistrationId, brand, model, color, userLogged.getId());
			this.setCarRegistrationId("");
			this.setBrand("");
			this.setModel("");
			this.setColor("");

			result = "carListView";
		}
		return result;
	}
}
