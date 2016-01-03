package config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Manages requests according to permissions
 * 
 * @author Ignacio González Bullón - nachoglezbul@uoc.edu
 *
 */
@WebFilter(filterName = "AuthFilter", urlPatterns = { "*.xhtml" })
public class AuthorizationFilter implements Filter {

	private static final String DRIVER_ROLE = "driverRole";
	private static final String PASSENGER_ROLE = "passengerRole";
	private static final String LOGIN_PAGE = "/CAT-PDP-GRUP6/pages/public/login.xhtml";
	private static final String DRIVER_PATH = "/driver/";
	private static final String PASSENGER_PATH = "/passenger/";

	public AuthorizationFilter() {
		super();
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	/**
	 * Filters request according to permissions and path redirecting to login if
	 * the user has not the required permission
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		try {
			HttpServletRequest servletRequest = (HttpServletRequest) request;
			HttpServletResponse servletResponse = (HttpServletResponse) response;
			HttpSession session = servletRequest.getSession(false);

			String reqURI = servletRequest.getRequestURI();
			if (reqURI.indexOf(DRIVER_PATH) >= 0) {
				boolean isDriver = session != null ? (boolean) session.getAttribute(DRIVER_ROLE) : false;
				if (isDriver) {
					chain.doFilter(request, response);
				} else {
					servletResponse.sendRedirect(LOGIN_PAGE);
				}
			} else if (reqURI.indexOf(PASSENGER_PATH) >= 0) {
				boolean isPassenger = session != null ? (boolean) session.getAttribute(PASSENGER_ROLE) : false;
				if (isPassenger) {
					chain.doFilter(request, response);
				} else {
					servletResponse.sendRedirect(LOGIN_PAGE);
				}
			} else {
				chain.doFilter(request, response);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	@Override
	public void destroy() {

	}

}
