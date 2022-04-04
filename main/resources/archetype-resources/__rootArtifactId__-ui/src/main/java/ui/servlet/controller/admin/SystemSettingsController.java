#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.ui.servlet.controller.admin;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;

import org.apache.commons.beanutils.BeanUtils;

import ${package}.component.AdminComponent;
import ${package}.constants.GlobalStatusCodes;
import ${package}.constants.MessageTypes;
import ${package}.constants.ServiceErrorStatus;
import ${package}.exception.AppException;
import ${package}.formbeans.ToastMessageFormBean;
import ${package}.formdatas.SystemSettingsFormData;
import ${package}.ui.constants.WebConst;
import ${package}.ui.servlet.common.DataModel;
import ${package}.ui.util.WebUtil;

/**
 * @author ${author}
 *
 */
@WebServlet(name = "SystemSettings", urlPatterns = {"/systemsettings"})
@MultipartConfig(fileSizeThreshold = 0, maxFileSize = 209715200, maxRequestSize = 209715200)
public final class SystemSettingsController extends AdminBaseController {

	private static final long serialVersionUID = 1L;

	/***/
	public SystemSettingsController() {
		super(SystemSettingsController.class);
	}

	@Override
	protected void processGet(final DataModel model) throws AppException {
		final AdminComponent comp = new AdminComponent();
		model.getRequest().put("settings", comp.getAllSystemSettings());
		//set the current page on every request
		model.getRequest().put(WebConst.ATTR_CURRENT_PAGE, "systemsettings");
		model.setRedirectCommonJsp("systemsettings");
	}

	@Override
	protected void processPost(final DataModel model) throws AppException {
		final SystemSettingsFormData data = new SystemSettingsFormData();

		try {
			BeanUtils.populate(data, model.getRequest().getParams());
			if (!WebUtil.requireFieldCheck(
					data.getName(),
					data.getValue())) {
				throw new AppException("Invalid access...", GlobalStatusCodes.BAD_REQUEST, ServiceErrorStatus.DATA_ERROR);
			}

			final AdminComponent comp = new AdminComponent();
			comp.addSystemSetting(data);

			model.getRequest().addMessage(new ToastMessageFormBean(MessageTypes.SUCCESS, String.format("%s was added successfully.", data.getName())));
			model.setRedirectController("SystemSettings");
		} catch (final IllegalAccessException e) {
			throw new AppException("Internal error...", GlobalStatusCodes.INTERNAL_SERVER_ERROR, ServiceErrorStatus.INTERNAL_ERROR);
		} catch (final InvocationTargetException e) {
			throw new AppException("Internal error...", GlobalStatusCodes.INTERNAL_SERVER_ERROR, ServiceErrorStatus.INTERNAL_ERROR);
		}
	}

}
