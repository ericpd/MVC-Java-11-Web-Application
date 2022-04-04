#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.ui.servlet.controller.main.settings;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;

import org.apache.commons.beanutils.BeanUtils;

import ${package}.component.UserComponent;
import ${package}.constants.GlobalStatusCodes;
import ${package}.constants.ServiceErrorStatus;
import ${package}.exception.AppException;
import ${package}.formbeans.UserProfileFormBean;
import ${package}.formdatas.UserProfileFormData;
import ${package}.ui.constants.WebConst;
import ${package}.ui.servlet.common.AbstractWebController;
import ${package}.ui.servlet.common.DataModel;

/**
 * @author ${author}
 *
 */
@WebServlet(name = "UserProfile", urlPatterns = {"/settings/user/profile"})
@MultipartConfig(fileSizeThreshold = 0, maxFileSize = 209715200, maxRequestSize = 209715200)
public final class UserProfileController extends AbstractWebController {

	private static final long serialVersionUID = 1L;

	/***/
	public UserProfileController() {
		super(UserProfileController.class);
	}

	@Override
	protected void processGet(final DataModel model) throws AppException {
		final UserComponent comp = new UserComponent();
		final UserProfileFormBean bean = comp.getUserProfile(model.getUserId());

		model.getRequest().put("user", bean);

		//set the current page on every request
		model.getRequest().put(WebConst.ATTR_CURRENT_PAGE, "settings");
		model.setRedirectCommonJsp("userprofile");
	}

	@Override
	protected void processPost(final DataModel model) throws AppException {
		final UserProfileFormData data = new UserProfileFormData();

		try {
			BeanUtils.populate(data, model.getRequest().getParams());
			data.setId(model.getUserId());

			final UserComponent comp = new UserComponent();
			final UserProfileFormBean bean = comp.updateProfileInfo(data);

			model.getRequest().put("user", bean);
			model.getRequest().put(WebConst.ATTR_CURRENT_PAGE, "settings");
			model.setRedirectCommonJsp("userprofile");
		} catch (final IllegalAccessException e) {
			throw new AppException("Invalid access...", GlobalStatusCodes.BAD_REQUEST, ServiceErrorStatus.DATA_ERROR);
		} catch (final InvocationTargetException e) {
			throw new AppException("Invalid access...", GlobalStatusCodes.BAD_REQUEST, ServiceErrorStatus.DATA_ERROR);
		}

	}

}
