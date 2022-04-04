#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.ui.servlet.controller.main;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;

import ${package}.component.UserComponent;
import ${package}.exception.AppException;
import ${package}.formbeans.UserFormBean;
import ${package}.hibernate.beans.enumeration.UserType;
import ${package}.ui.constants.WebConst;
import ${package}.ui.servlet.common.AbstractWebController;
import ${package}.ui.servlet.common.DataModel;

/**
 * @author ${author}
 *
 */
@WebServlet(name = "Home", urlPatterns = {"/home"})
@MultipartConfig(fileSizeThreshold = 0, maxFileSize = 209715200, maxRequestSize = 209715200)
public final class HomeController extends AbstractWebController {

	private static final long serialVersionUID = 1L;

	/***/
	public HomeController() {
		super(HomeController.class);
	}

	@Override
	protected void processGet(final DataModel model) throws AppException {

		final UserComponent userComponent = new UserComponent();
		switch (UserType.getAnnotationType(model.getUserType())) {
		case ADMIN:
			//set the current page on every request
			model.setRedirectController("SystemSettings", true);
			break;
		case ROOT:
		case MEMBER:
			//set the current page on every request
			model.setRedirectController("Settings", true);
			break;

		default:
			final UserFormBean user = userComponent.getUserInfo(model.getUserId());
			model.getRequest().put(WebConst.ATTR_USER, user);

			//set the current page on every request
			model.getRequest().put(WebConst.ATTR_CURRENT_PAGE, "home");
			model.setRedirectCommonJsp("home");
		}
	}

	@Override
	protected boolean allowedUserType(final String method, final String userType) {
		switch (UserType.getAnnotationType(userType)) {
		case ADMIN:
		case ROOT:
		case MEMBER:
			return true;
		default:
			return false;
		}
	}

}
