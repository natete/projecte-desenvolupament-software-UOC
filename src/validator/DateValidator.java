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
 * Validates any date greater than the current day.
 */
@FacesValidator("validator.DateValidator")
public class DateValidator implements Validator {
	
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		// TODO Auto-generated method stub
		
		Date date = (Date) value;
		Calendar depDate = Calendar.getInstance();
		depDate.setTime(date);
		Calendar currDate = Calendar.getInstance();
		
		if (currDate.after(depDate)) {
			FacesMessage msg = new FacesMessage("This field only accepts future dates");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
	}
}
