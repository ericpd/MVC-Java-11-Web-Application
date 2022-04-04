#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.ui.servlet.controller.main.settings;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;

import ${package}.component.UserComponent;
import ${package}.exception.AppException;
import ${package}.formbeans.UserProfileFormBean;
import ${package}.ui.constants.WebConst;
import ${package}.ui.servlet.common.AbstractWebController;
import ${package}.ui.servlet.common.DataModel;

/**
 * @author ${author}
 *
 */
@WebServlet(name = "Settings", urlPatterns = {"/settings"})
@MultipartConfig(fileSizeThreshold = 0, maxFileSize = 209715200, maxRequestSize = 209715200)
public final class SettingsController extends AbstractWebController {

	private static final long serialVersionUID = 1L;

	/***/
	public SettingsController() {
		super(SettingsController.class);
	}

	@Override
	protected void processGet(final DataModel model) throws AppException {
		final UserComponent comp = new UserComponent();
		final UserProfileFormBean bean = comp.getUserProfile(model.getUserId());

		model.getRequest().put("user", bean);

		//set the current page on every request
		model.getRequest().put(WebConst.ATTR_CURRENT_PAGE, "settings");
		model.setRedirectCommonJsp("settings");
	}

}
