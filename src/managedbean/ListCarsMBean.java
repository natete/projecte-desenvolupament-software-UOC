
package managedbean;

import java.io.Serializable;
import java.util.*;

import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.naming.Context;
import javax.naming.InitialContext;

import jpa.CarJPA;
import ejb.UserFacadeRemote;

/**
 * Managed Bean ListcarsMBean
 */
@ManagedBean(name = "cars")
@SessionScoped
public class ListCarsMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;	

	@EJB
	private UserFacadeRemote carsRemote;
	
	//stores the name of the category of cars to be displayed
	private String nif = "00000000X";
	//stores all the instances of CarJPA
	private Collection<CarJPA> carsList;
	//stores the screen number where the user is 
	private int screen = 0;
	//stores ten or fewer CarJPA instances that the user can see on a screen
	protected Collection<CarJPA> carsListView;
	//stores the total number of instances of CarJPA
	protected int numberCars = 0;
	
	/**
	 * Constructor method
	 * @throws Exception
	 */
	public ListCarsMBean() throws Exception
	{
		
	}
	
	
	/**
	 * Method that returns an instance Collection of 10 or less CarJPA according screen 
	 * where the user is.
	 * @return Collection CarJPA
	 */
	public Collection<CarJPA> getCarListView() throws Exception
	{
		int n =0;
		carsListView = new ArrayList<CarJPA>();
		this.carList();
		for (Iterator<CarJPA> iter2 = carsList.iterator(); iter2.hasNext();)
		{
			CarJPA car = (CarJPA) iter2.next();
			if (n >= screen*10 && n< (screen*10+10))
			{				
				this.carsListView.add(car);
			}
			n +=1;
		}
		this.numberCars = n;
		return carsListView;
	}
	
	/**
	 * Returns the total number of instances of CarJPA
	 * @return Car number
	 */
	public int getNumbercars()
	{ 
		return this.numberCars;
	}
	
	/**
	 * allows forward or backward in user screens
	 */
	public void nextScreen()
	{
		if (((screen+1)*10 < carsList.size()))
		{
			screen +=1;
		}
	}
	public void previousScreen()
	{
		if ((screen > 0))
		{
			screen -=1;
		}
	}

	
	public String getNif()
	{
		return this.nif;
	}
	public void setNif(String nif)
	{
		this.nif = nif;
	}
	
		
	/**
	 * Method used for Facelet to call listcarsView Facelet
	 * @return Facelet name
	 * @throws Exception
	 */
	public String listCars() throws Exception
	{
		carList();
		return "listcarsView";
	}
	
	/**
	 * Method used for Facelet to call addCarView Facelet
	 * @return Facelet name
	 */
	public String addCar()
	{
		return "addCarView";
	}
	
	/**
	 * Method that gets a list of instances all CarJPA
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void carList() throws Exception
	{	
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		screen = 0;
		carsRemote = (UserFacadeRemote) ctx.lookup("java:app/CarSharing.jar/UserFacadeBean!ejb.UserFacadeRemote");
		carsList = (Collection<CarJPA>)carsRemote.listAllCars(nif);
	}
}
