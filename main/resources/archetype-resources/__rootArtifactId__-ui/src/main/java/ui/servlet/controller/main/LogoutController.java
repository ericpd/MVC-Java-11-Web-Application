#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.ui.servlet.controller.main;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;

import ${package}.exception.AppException;
import ${package}.ui.constants.WebConst;
import ${package}.ui.servlet.common.AbstractWebController;
import ${package}.ui.servlet.common.DataModel;

/**
 * @author ${author}
 *
 */
@WebServlet(name = "Logout", urlPatterns = {"/logout"})
@MultipartConfig(fileSizeThreshold = 0, maxFileSize = 209715200, maxRequestSize = 209715200)
public final class LogoutController extends AbstractWebController {

	private static final long serialVersionUID = 1L;

	/***/
	public LogoutController() {
		super(LogoutController.class);
	}

	@Override
	protected void processGet(final DataModel model) throws AppException {
		//set the current page on every request
		model.getRequest().put(WebConst.ATTR_CURRENT_PAGE, "logout");
		logout(model);
	}

	/**
	 *
	 * @param model DataModel
	 * @throws AppException e
	 */
	private void logout(final DataModel model) throws AppException {
		model.getSession().setInvalidate(true);
		model.setRedirectController("Root", true);
	}

}
