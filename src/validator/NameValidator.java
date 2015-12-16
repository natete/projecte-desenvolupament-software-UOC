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
 * Validates a name which is any number of words (group of letters) separated by a whitespace or a dash character
 *
 */
@FacesValidator("validator.NameValidator")
public class NameValidator implements Validator {

	private static final String NAME_PATTERN = "^(([a-zA-ZçñáàäâéèëêíìïîóòöôúùüûÇÑÁÀÄÂÉÈËÊÍÌÏÎÓÒÖÔÚÙÜÛ])+(([\\s-]?)([a-zA-ZçñáàäâéèëêíìïîóòöôúùüûÇÑÁÀÄÂÉÈËÊÍÌÏÎÓÒÖÔÚÙÜÛ])+)*)*$";

	Pattern pattern;
	Matcher matcher;

	public NameValidator() {
		this.pattern = Pattern.compile(NAME_PATTERN);
	}

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		matcher = pattern.matcher(value.toString());

		if (!matcher.matches()) {
			FacesMessage msg = new FacesMessage("This field only accepts letters, spaces and dash characters");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);

			throw new ValidatorException(msg);
		}
	}
}
