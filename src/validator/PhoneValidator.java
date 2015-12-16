package validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Validates a phone number which is a serie of nine numbers. 
 *
 */
@FacesValidator("validator.PhoneValidator")
public class PhoneValidator implements Validator {

	private static final String PHONE_PATTERN = "(^$)|(\\d{9})";

	Pattern pattern;
	Matcher matcher;

	public PhoneValidator() {
		this.pattern = Pattern.compile(PHONE_PATTERN);
	}

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		matcher = pattern.matcher(value.toString());

		if (!matcher.matches()) {
			FacesMessage msg = new FacesMessage("Phone format not valid. Ej.: 123456789");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);

			throw new ValidatorException(msg);
		}
	}
}
