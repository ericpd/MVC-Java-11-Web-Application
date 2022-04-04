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
import ${package}.ui.util.WebUtil;

/**
 * @author ${author}
 *
 */
@WebServlet(name = "AccountSetting", urlPatterns = {"/settings/user/account"})
@MultipartConfig(fileSizeThreshold = 0, maxFileSize = 209715200, maxRequestSize = 209715200)
public final class AccountSettingController extends AbstractWebController {

	private static final long serialVersionUID = 1L;

	/***/
	public AccountSettingController() {
		super(AccountSettingController.class);
	}

	@Override
	protected void processGet(final DataModel model) throws AppException {
		openAccountPage(model, new UserComponent());
	}

	@Override
	protected void processPost(final DataModel model) throws AppException {
		final String email = model.getRequest().getParameterAsString(WebConst.ATTR_USER_EMAIL, "");
		final String newPassword = model.getRequest().getParameterAsString(WebConst.ATTR_USER_NEW_PASSWORD, "");
		final String currentPassword = model.getRequest().getParameterAsString(WebConst.ATTR_USER_PASSWORD, "");
		final String confirmResetPassword = model.getRequest().getParameterAsString(WebConst.ATTR_USER_CONFIRM_PASSWORD, "");

		final UserComponent comp = new UserComponent();

		// Check required fields
		if (!WebUtil.requireFieldCheck(email, newPassword, currentPassword)) {
			model.getRequest().put(WebConst.ATTR_ERROR_MESSAGE, "Invalid Parameter.");
			openAccountPage(model, comp);
			return;
		}

		if (!newPassword.equals(confirmResetPassword)) {
			model.getRequest().put(WebConst.ATTR_ERROR_MESSAGE, "Password does not match.");
			openAccountPage(model, comp);
			return;
		}

		if (newPassword.equals(currentPassword)) {
			model.getRequest().put(WebConst.ATTR_ERROR_MESSAGE, "Please enter a different password from the previous ones.");
			openAccountPage(model, comp);
			return;
		}

		try {

			final boolean updated = comp.updateAccount(model.getUserId(), email, newPassword, currentPassword);

			model.getRequest().put("isPasswordUpdated", updated);
			model.getRequest().put(WebConst.ATTR_MESSAGE, "Password has been changed. Please login with your new password");
		} catch (final AppException e) {
			model.getRequest().put(WebConst.ATTR_ERROR_MESSAGE, e.getMessage());
		}

		openAccountPage(model, comp);
	}

	/**
	 *
	 * @param model DataModel
	 * @param comp UserComponent
	 * @throws AppException
	 */
	private void openAccountPage(final DataModel model, final UserComponent comp) throws AppException {
		final UserProfileFormBean bean = comp.getUserProfile(model.getUserId());
		model.getRequest().put("user", bean);
		model.getRequest().put(WebConst.ATTR_CURRENT_PAGE, "settings");
		model.setRedirectCommonJsp("useraccount");
	}

}
