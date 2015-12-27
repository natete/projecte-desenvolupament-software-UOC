package validator;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
	
	private static final String DATE_PATTERN = "^([0-9]{2}/){2}/[0-9]{4}$";
	
	Pattern pattern;
	Matcher matcher;

	public DateValidator() {
		this.pattern = Pattern.compile(DATE_PATTERN);
	}
	
	
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		// TODO Auto-generated method stub
		
		if (!matcher.matches()) {
			FacesMessage msg = new FacesMessage("This field only accepts dd/MM/yyyy format");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
		
		Integer thisDay = Calendar.DATE;
		Integer thisMonth = Calendar.MONTH;
		Integer thisYear = Calendar.YEAR;
		
		String d = value.toString();
		String[] tokens = d.split(d);
		Integer depDay = Integer.decode(tokens[0]);
		Integer depMonth = Integer.decode(tokens[1]);
		Integer depYear = Integer.decode(tokens[2]);
        
        if (thisYear.equals(depYear)) {
        	if (thisMonth < depMonth);
        	if (thisMonth.equals(depMonth)) {
        		if (thisDay < depDay);
        		if (thisDay.equals(depDay)) {
        			FacesMessage msg = new FacesMessage("This field only accepts future dates");
        			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        			throw new ValidatorException(msg);
        		}
        		if (thisDay > depDay) {
        			FacesMessage msg = new FacesMessage("This field only accepts future dates");
        			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        			throw new ValidatorException(msg);
        		}
        	}
        	if (thisMonth > depMonth) {
        		FacesMessage msg = new FacesMessage("This field only accepts future dates");
    			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
    			throw new ValidatorException(msg);
        	}
        }
        if (thisYear > depYear) {
        	FacesMessage msg = new FacesMessage("This field only accepts future dates");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
        }
	}
}
