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
 * Validates a NIF field with the pattern eight numbers followed by a letter
 * 
 */
@FacesValidator("validator.NifValidator")
public class NifValidator implements Validator {

	private static final String NIF_PATTERN = "([0-9]{8})([A-Za-z])";

	Pattern pattern;
	Matcher matcher;

	public NifValidator() {
		this.pattern = Pattern.compile(NIF_PATTERN);
	}

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		matcher = pattern.matcher(value.toString());

		if (!matcher.matches()) {
			FacesMessage msg = new FacesMessage("NIF format not valid. (Ej.: 12345678a or 12345678A)");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);

			throw new ValidatorException(msg);
		}
	}
}
