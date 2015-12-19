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
 * Validates an email address
 *
 */
@FacesValidator("validator.EmailValidator")
public class EmailValidator implements Validator {

	private static final String EMAIL_PATTERN = "^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	Pattern pattern;
	Matcher matcher;

	public EmailValidator() {
		this.pattern = Pattern.compile(EMAIL_PATTERN);
	}

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		matcher = pattern.matcher(value.toString());

		if (!matcher.matches()) {
			FacesMessage msg = new FacesMessage("Email format not valid. (Ej.: example@domain.com)");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);

			throw new ValidatorException(msg);
		}
	}
}
