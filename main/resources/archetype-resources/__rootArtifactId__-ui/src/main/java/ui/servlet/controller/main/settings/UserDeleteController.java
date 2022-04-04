#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.ui.servlet.controller.main.settings;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;

import ${package}.component.UserComponent;
import ${package}.constants.GlobalStatusCodes;
import ${package}.exception.AppException;
import ${package}.formbeans.ResponseFormBean;
import ${package}.formbeans.UserProfileFormBean;
import ${package}.ui.constants.WebConst;
import ${package}.ui.servlet.common.AbstractWebController;
import ${package}.ui.servlet.common.DataModel;

/**
 * @author ${author}
 *
 */
@WebServlet(name = "UserDelete", urlPatterns = {"/settings/user/delete"})
@MultipartConfig(fileSizeThreshold = 0, maxFileSize = 209715200, maxRequestSize = 209715200)
public final class UserDeleteController extends AbstractWebController {

	private static final long serialVersionUID = 1L;

	/***/
	public UserDeleteController() {
		super(UserDeleteController.class);
	}

	@Override
	protected void processGet(final DataModel model) throws AppException {
		final UserComponent comp = new UserComponent();
		final UserProfileFormBean bean = comp.getUserProfile(model.getUserId());

		model.getRequest().put("user", bean);
		model.getRequest().put(WebConst.ATTR_CURRENT_PAGE, "settings");
		model.setRedirectCommonJsp("userdelete");
	}

	@Override
	protected void processPost(final DataModel model) throws AppException {
		final UserComponent comp = new UserComponent();
		final boolean deleted  = comp.deleteUser(model.getUserId());
		final UserProfileFormBean bean = comp.getUserProfile(model.getUserId());

		model.getRequest().put("user", bean);
		model.out(new ResponseFormBean(GlobalStatusCodes.CREATED, null, deleted));

		model.setRedirectController("Logout", true);
	}

}
