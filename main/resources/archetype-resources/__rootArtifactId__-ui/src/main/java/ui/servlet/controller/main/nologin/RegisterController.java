#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.ui.servlet.controller.main.nologin;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;

import ${package}.component.UserComponent;
import ${package}.constants.GlobalStatusCodes;
import ${package}.constants.ServiceErrorStatus;
import ${package}.exception.AppException;
import ${package}.ui.constants.WebConst;
import ${package}.ui.servlet.common.DataModel;
import ${package}.ui.util.WebUtil;

/**
 * @author ${author}
 *
 */
@WebServlet(name = "Register", urlPatterns = {"/register"})
@MultipartConfig(fileSizeThreshold = 0, maxFileSize = 209715200, maxRequestSize = 209715200)
public final class RegisterController extends NoLoginBaseController {

	private static final long serialVersionUID = 1L;

	/***/
	public RegisterController() {
		super(RegisterController.class);
	}

	@Override
	protected void processGet(final DataModel model) throws AppException {
		// Remove all error messages in the session
		model.getSession().remove(WebConst.ATTR_ERROR_CODE);
		model.getSession().remove(WebConst.ATTR_ERROR_MESSAGE);

		//set the current page on every request
		model.getRequest().put(WebConst.ATTR_CURRENT_PAGE, "register");
		model.setRedirectContentJsp("register");
	}

	@Override
	protected void processPost(final DataModel model) throws AppException {
		final String firstName = model.getRequest().getParameterAsString(WebConst.ATTR_USER_FIRSTNAME, "");
		final String lastName = model.getRequest().getParameterAsString(WebConst.ATTR_USER_LASTNAME, "");
		final String email = model.getRequest().getParameterAsString(WebConst.ATTR_USER_EMAIL, "");
		final String password = model.getRequest().getParameterAsString(WebConst.ATTR_USER_PASSWORD, "");

		try {
			// Check required fields
			if (!WebUtil.requireFieldCheck(firstName, lastName, email, password)) {
				throw new AppException("Missing required parameter...", GlobalStatusCodes.BAD_REQUEST, ServiceErrorStatus.DATA_ERROR);
			}

			if (!WebUtil.isValidEmailAddress(email)) {
				throw new AppException("Email not valid...", GlobalStatusCodes.BAD_REQUEST, ServiceErrorStatus.DATA_ERROR);
			}

			final UserComponent component = new UserComponent();
			component.signupRoot(firstName, lastName, email, password);
			model.setRedirectController("Login", true, "email=" + email);
			
		} catch (final AppException e) {
			model.getRequest().put(WebConst.ATTR_USER_FIRSTNAME, firstName);
			model.getRequest().put(WebConst.ATTR_USER_LASTNAME, lastName);
			model.getRequest().put(WebConst.ATTR_USER_EMAIL, email);

			model.getRequest().put(WebConst.ATTR_ERROR_MESSAGE, e.getMessage());
			model.setRedirectContentJsp("register");
			throw e;
		}
	}

	@Override
	protected boolean byPassLogin(final String method) {
		if ("BETA".equals(System.getProperty("RUN_ENV", "DEV"))) {
			return false;
		}
		return true;
	}


}
