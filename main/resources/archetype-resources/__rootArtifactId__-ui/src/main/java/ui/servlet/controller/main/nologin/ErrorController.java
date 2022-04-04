#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.ui.servlet.controller.main.nologin;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;

import ${package}.exception.AppException;
import ${package}.ui.constants.WebConst;
import ${package}.ui.servlet.common.DataModel;

/**
 * @author ${author}
 *
 */
@WebServlet(name = "Error", urlPatterns = {"/error"})
@MultipartConfig(fileSizeThreshold = 0, maxFileSize = 209715200, maxRequestSize = 209715200)
public final class ErrorController extends NoLoginBaseController {

	private static final long serialVersionUID = 1L;

	/***/
	public ErrorController() {
		super(ErrorController.class);
	}

	@Override
	protected void processGet(final DataModel model) throws AppException {
		final int errorCode = model.getRequest().getParameterAsInt("code", -1);
		if (errorCode > 0) {
			model.getSession().put(WebConst.ATTR_STATUS, errorCode);
		}

		model.getRequest().put(WebConst.ATTR_CURRENT_PAGE, "error");
		if (!model.getSession().isUserLoggedIn()) {
			model.setRedirectContentJsp("error");
		} else {
			model.setRedirectCommonJsp("error");
		}
	}

}
