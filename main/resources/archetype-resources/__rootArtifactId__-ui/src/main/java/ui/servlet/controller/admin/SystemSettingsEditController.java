#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.ui.servlet.controller.admin;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;

import ${package}.component.AdminComponent;
import ${package}.constants.GlobalStatusCodes;
import ${package}.constants.MessageTypes;
import ${package}.constants.ServiceErrorStatus;
import ${package}.exception.AppException;
import ${package}.formbeans.ToastMessageFormBean;
import ${package}.ui.constants.WebConst;
import ${package}.ui.servlet.common.DataModel;
import ${package}.ui.util.WebUtil;

/**
 * @author ${author}
 *
 */
@WebServlet(name = "SystemSettingsEdit", urlPatterns = {"/systemsettings/edit"})
@MultipartConfig(fileSizeThreshold = 0, maxFileSize = 209715200, maxRequestSize = 209715200)
public final class SystemSettingsEditController extends AdminBaseController {

	private static final long serialVersionUID = 1L;

	/***/
	public SystemSettingsEditController() {
		super(SystemSettingsEditController.class);
	}

	@Override
	protected void processGet(final DataModel model) throws AppException {
		final int id = model.getRequest().getParameterAsInt(WebConst.ATTR_ID, 0);
		final boolean encrypted = model.getRequest().getParameterAsBoolean(WebConst.ATTR_ENCRYPTED, false);
		final boolean edit = model.getRequest().getParameterAsBoolean(WebConst.ATTR_EDIT, false);

		final AdminComponent comp = new AdminComponent();
		model.getRequest().put("setting", comp.getSystemSetting(id, encrypted));
		model.getRequest().put("edit", edit);

		//set the current page on every request
		model.getRequest().put(WebConst.ATTR_CURRENT_PAGE, "systemsettings");
		model.setRedirectCommonJsp("systemsettingsedit");
	}

	@Override
	protected void processPost(final DataModel model) throws AppException {
		final int id = model.getRequest().getParameterAsInt(WebConst.ATTR_ID, 0);
		final String value = model.getRequest().getParameterAsString(WebConst.ATTR_VALUE, null);
		final boolean encrypted = model.getRequest().getParameterAsBoolean(WebConst.ATTR_ENCRYPTED, false);

		// Check required fields
		if (id == 0) {
			throw new AppException("Invalid access...", GlobalStatusCodes.BAD_REQUEST, ServiceErrorStatus.DATA_ERROR);
		}

		final AdminComponent comp = new AdminComponent();
		if (!WebUtil.requireFieldCheck(value)) {
			throw new AppException("Invalid access...", GlobalStatusCodes.BAD_REQUEST, ServiceErrorStatus.DATA_ERROR);
		}

		model.getRequest().put("setting", comp.editSystemSetting(id, value, encrypted));
		model.getRequest().put("edit", false);

		model.getRequest().addMessage(new ToastMessageFormBean(MessageTypes.SUCCESS, String.format("System Setting ID: %s was successfully updated.", id)));

		model.getRequest().put(WebConst.ATTR_CURRENT_PAGE, "systemsettings");
		model.setRedirectCommonJsp("systemsettingsedit");
	}

}
