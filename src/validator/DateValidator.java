package validator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
	
	//private static final String DATE_PATTERN = "^(\d{2}/){2}/\d{4}$";
	//private static final String DATE_PATTERN = "^([0-9]{2}/){2}/[0-9]{4}$";
	private static final String DATE_PATTERN = "^[0-9][0-9]/[0-9][0-9]/[0-9][0-9][0-9][0-9]$";
	Pattern pattern;
	Matcher matcher;
	
	public DateValidator() {
		this.pattern = Pattern.compile(DATE_PATTERN);
	}
	
	
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		// TODO Auto-generated method stub
		
		/*
		@SuppressWarnings("deprecation")
		DateFormat sdf = new SimpleDateFormat("dd/MM/dddd");
		Date date = new Date();
		try {
			date = sdf.parse(value.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    */
		
		String date = value.toString();
		String[] tokens = date.split(" ");
		String month;
        switch (tokens[1]) {
        	case "Jan":	month = "01";
                     	break;
        	case "Feb":	month = "02";
                     	break;
        	case "Mar": month = "03";
                     	break;
        	case "Apr": month = "04";
                     	break;
            case "May": month = "05";
                     	break;
            case "Jun": month = "06";
                     	break;
            case "Jul": month = "07";
            			break;
            case "Aug": month = "08";
                     	break;
            case "Sep": month = "09";
                     	break;
            case "Oct": month = "10";
            			break;
            case "Nov": month = "11";
                     	break;
            case "Dec": month = "12";
                     	break;
            default:	month = "00";
        }
        String day = tokens[2];
        String year = tokens[5];
        date = day + "/" + month + "/" + year;
		
	    matcher = pattern.matcher(date);
		
		if (!matcher.matches()) {
			FacesMessage msg = new FacesMessage("This field only accepts dd/MM/yyyy format");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
		
		Integer thisDay = Calendar.DATE;
		Integer thisMonth = Calendar.MONTH;
		Integer thisYear = Calendar.YEAR;
		
		String d = date;
		tokens = d.split("/");
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
