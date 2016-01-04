package validator;

import java.util.Calendar;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Validates any date greater than three days the current day.
 */
@FacesValidator("validator.DateValidator")
public class DateValidator implements Validator {
	//private static final int MINIMUM_DAYS_REQUIRED = 2;
	
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		// TODO Auto-generated method stub
		
		Date date = (Date) value;
		Calendar depDate = Calendar.getInstance();
		depDate.setTime(date);
		Calendar currDate = Calendar.getInstance();
		//currDate.add(Calendar.DATE, MINIMUM_DAYS_REQUIRED);
		if (currDate.after(depDate) || depDate == null) {
			FacesMessage msg = new FacesMessage("This field only accepts a future date");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
	}
}
