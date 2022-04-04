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
@WebServlet(name = "Index", urlPatterns = {"/index"})
@MultipartConfig(fileSizeThreshold = 0, maxFileSize = 209715200, maxRequestSize = 209715200)
public final class IndexController extends NoLoginBaseController {

	private static final long serialVersionUID = 1L;

	/***/
	public IndexController() {
		super(IndexController.class);
	}

	@Override
	protected void processGet(final DataModel model) throws AppException {

		if (null != model.getSession() && null != model.getSession().getAttributeAsString(WebConst.ATTR_USER_USERID)) {
			model.setRedirectController("Home", false);
			return;
		} else {
			model.getSession().setInvalidate(true);
		}

		model.setRedirectContentJsp("index");
	}

}
