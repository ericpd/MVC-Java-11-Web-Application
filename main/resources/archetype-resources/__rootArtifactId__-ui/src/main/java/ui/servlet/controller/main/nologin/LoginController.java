#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.ui.servlet.controller.main.nologin;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;

import ${package}.component.UserComponent;
import ${package}.exception.AppException;
import ${package}.formbeans.UserFormBean;
import ${package}.ui.constants.WebConst;
import ${package}.ui.servlet.common.DataModel;
import ${package}.ui.util.WebUtil;

/**
 * @author ${author}
 *
 */
@WebServlet(name = "Login", urlPatterns = {"/login"})
@MultipartConfig(fileSizeThreshold = 0, maxFileSize = 209715200, maxRequestSize = 209715200)
public final class LoginController extends NoLoginBaseController {

	private static final long serialVersionUID = 1L;

	/***/
	public LoginController() {
		super(LoginController.class);
	}

	@Override
	protected void processGet(final DataModel model) throws AppException {

		final String redirectUrl = model.getRequest().getParameterAsString(WebConst.ATTR_REDIRECT_URL, null);
		if (null != redirectUrl) {
			model.getRequest().put(WebConst.ATTR_REDIRECT_URL, redirectUrl);
		}

		if (null != model.getSession() && null != model.getSession().getAttributeAsString(WebConst.ATTR_USER_USERID)) {
			model.setRedirectController("Root", true);
			return;
		}

		final String email = model.getRequest().getParameterAsString(WebConst.ATTR_USER_EMAIL, "");
		model.getRequest().put(WebConst.ATTR_USER_EMAIL, email);

		// Remove all error messages in the session
		model.getSession().remove(WebConst.ATTR_ERROR_CODE);
		model.getSession().remove(WebConst.ATTR_ERROR_MESSAGE);

		//set the current page on every request
		model.getRequest().put(WebConst.ATTR_CURRENT_PAGE, "login");
		model.setRedirectContentJsp("login");
	}

	@Override
	protected void processPost(final DataModel model) throws AppException {
		final String email = model.getRequest().getParameterAsString(WebConst.ATTR_USER_EMAIL, "");
		final String password = model.getRequest().getParameterAsString(WebConst.ATTR_USER_PASSWORD, "");
		// Check required fields
		if (!WebUtil.requireFieldCheck(email, password)) {
			model.getRequest().put(WebConst.ATTR_ERROR_MESSAGE, "Missing required parameter...");
			model.setRedirectContentJsp("login");
			return;
		}

		final String redirectUrl = model.getRequest().getParameterAsString(WebConst.ATTR_REDIRECT_URL, null);


		try {
			final UserComponent component = new UserComponent();
			final UserFormBean bean = component.login(email, password);

			model.getSession().setNew(true)
							  .put(WebConst.ATTR_USER_USERID, bean.getId())
							  .put(WebConst.ATTR_USER_TYPE, bean.getType())
							  .put(WebConst.ATTR_USER_DISPLAY_NAME, bean.getDisplayName());
			if (null != bean.getImage()) {
				model.getSession().put(WebConst.ATTR_USER_IMAGE, bean.getImage());
			}

			if (null != redirectUrl && !redirectUrl.isBlank()) {
				model.setRedirectPath(redirectUrl, true);
			} else {
				model.setRedirectController("Root", true);
			}
		} catch (final AppException e) {
			final int attempt = model.getSession().getAttributeAsInt(WebConst.ATTR_LOGIN_ATTEMPT);
			model.getRequest().put(WebConst.ATTR_USER_EMAIL, email);
			if (attempt < 2) {
				model.getSession().put(WebConst.ATTR_LOGIN_ATTEMPT, attempt + 1);
				model.getRequest().put(WebConst.ATTR_ERROR_CODE, e.getInternalStatus())
								  .put(WebConst.ATTR_ERROR_MESSAGE, e.getMessage());

				model.setRedirectContentJsp("login");
			} else {
				model.getSession().put(WebConst.ATTR_LOGIN_ATTEMPT, 0);
				model.setRedirectContentJsp("errorlogin");
			}
		}
	}

	@Override
	protected boolean byPassLogin(final String method) {
		return true;
	}

}
