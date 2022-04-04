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
import ${package}.formbeans.UserPhotoFormBean;
import ${package}.formbeans.UserProfileFormBean;
import ${package}.formdatas.UserPhotoFormData;
import ${package}.ui.constants.WebConst;
import ${package}.ui.servlet.common.AbstractWebController;
import ${package}.ui.servlet.common.DataModel;

/**
 * @author ${author}
 *
 */
@WebServlet(name = "UserPhoto", urlPatterns = {"/settings/user/photo"})
@MultipartConfig(fileSizeThreshold = 0, maxFileSize = 209715200, maxRequestSize = 209715200)
public final class UserPhotoController extends AbstractWebController {

	private static final long serialVersionUID = 1L;

	/***/
	public UserPhotoController() {
		super(UserPhotoController.class);
	}

	@Override
	protected void processGet(final DataModel model) throws AppException {
		final UserComponent comp = new UserComponent();
		final UserProfileFormBean bean = comp.getUserProfile(model.getUserId());

		model.getRequest().put("user", bean);

		//set the current page on every request
		model.getRequest().put(WebConst.ATTR_CURRENT_PAGE, "settings");
		model.setRedirectCommonJsp("userphoto");
	}


	@Override
	protected void processPost(final DataModel model) throws AppException {
		final UserPhotoFormData data = new UserPhotoFormData();

		try {
			BeanUtils.populate(data, model.getRequest().getParams());
			data.setId(model.getUserId());

			final UserComponent comp = new UserComponent();
			final UserPhotoFormBean bean = comp.updateProfilePhoto(data);

			model.getRequest().put("user", bean); // set the user info in the request
			model.getSession().put(WebConst.ATTR_USER_IMAGE, bean.getImage()); // update the new image in the session

			model.setRedirectController("UserPhoto", true); // redirect to controller
		} catch (final IllegalAccessException e) {
			throw new AppException("Invalid access...", GlobalStatusCodes.BAD_REQUEST, ServiceErrorStatus.DATA_ERROR);
		} catch (final InvocationTargetException e) {
			throw new AppException("Invalid access...", GlobalStatusCodes.BAD_REQUEST, ServiceErrorStatus.DATA_ERROR);
		}

	}

}
