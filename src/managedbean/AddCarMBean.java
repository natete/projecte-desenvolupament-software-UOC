
package managedbean;

import java.io.Serializable;
import java.util.*;

import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;

import ejb.UserFacadeRemote;

/**
 * Managed Bean ShowPetMBean
 */
@ManagedBean(name = "addcar")
@SessionScoped
public class AddCarMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
		
	@EJB
	private UserFacadeRemote addCarRemote;
	
	private String nif;
	private String carRegistrationId;
	private String brand;
	private String model;
	private String color;
	
	private FacesMessage message;
	
	/**
	 * Constructor method
	 * @throws Exception
	 */
	public AddCarMBean() throws Exception
	{
		this.setNif("00000000X");
		
		
	}
	
	public String getNif()
	{
		return nif;
	}
	public void setNif(String nif) 
	{
		this.nif = nif;
		
	}
	public String getCarRegistrationId()
	{
		return carRegistrationId;
	}
	public void setCarRegistrationId(String carRegistrationId) 
	{
		this.carRegistrationId = carRegistrationId;
		
	}
	public String getBrand()
	{
		return brand;
	}
	public void setBrand(String brand) 
	{
		this.brand = brand;
		
	}
	public String getModel()
	{
		return model;
	}
	public void setModel(String model) 
	{
		this.model = model;
	}
	public String getColor()
	{
		return color;
	}
	public void setColor(String color) 
	{
		this.color = color;
	}
	public String setDataCar() throws Exception
	{	
		String errorMessage=null;
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		addCarRemote = (UserFacadeRemote) ctx.lookup("java:app/CarSharing.jar/UserFacadeBean!ejb.UserFacadeRemote");
		if (this.carRegistrationId.equals("")){
		    // Bring the error message using the Faces Context
			errorMessage = "Car Registration Id is missing";
			// Add View Faces Message
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
			            errorMessage, errorMessage);
			// Add the message into context for a specific component
			FacesContext.getCurrentInstance().addMessage("form:errorView", message);
		}
		if (this.brand.equals("")){
		    // Bring the error message using the Faces Context
			errorMessage = "Brand is missing";
			// Add View Faces Message
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
			            errorMessage, errorMessage);
			// Add the message into context for a specific component
			FacesContext.getCurrentInstance().addMessage("form:errorView", message);
		}
		if (addCarRemote.existsCar(carRegistrationId) == true) {
			 // Bring the error message using the Faces Context
			errorMessage = "Car registration Id already exists";
			// Add View Faces Message
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
			            errorMessage, errorMessage);
			// Add the message into context for a specific component
			FacesContext.getCurrentInstance().addMessage("form:errorView", message);
		}
		
		if(errorMessage!=null){
			return "errorView";
		}
		else {	
			addCarRemote.addCar(nif, carRegistrationId, brand, model, color);
			this.setCarRegistrationId("");
			this.setBrand("");
			this.setModel("");
			this.setColor("");
			
			return "carListView";
		}
	}
		
}
