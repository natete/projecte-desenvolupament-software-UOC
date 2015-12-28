package validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("validator.SeatsValidator")
public class SeatsValidator implements Validator {
	
	private static final String SEATS_PATTERN = "^[1-4]$";

	Pattern pattern;
	Matcher matcher;
	
	public SeatsValidator() {
		this.pattern = Pattern.compile(SEATS_PATTERN);
	}
	
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		// TODO Auto-generated method stub
		
		matcher = pattern.matcher(value.toString());

		if (!matcher.matches()) {
			FacesMessage msg = new FacesMessage("This field only accepts from 1 up to 4 seats");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
	}
}
