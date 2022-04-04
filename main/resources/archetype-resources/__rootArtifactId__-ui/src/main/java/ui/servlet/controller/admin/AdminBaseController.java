#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.ui.servlet.controller.admin;

import ${package}.hibernate.beans.enumeration.UserType;
import ${package}.ui.servlet.common.AbstractWebController;

/**
 * @author ${author}
 *
 */
public class AdminBaseController extends AbstractWebController {

	private static final long serialVersionUID = 1L;

	/**
	 * @param clazz Class< ? extends AdminBaseController>
	 */
	protected AdminBaseController(final Class< ? extends AdminBaseController> clazz) {
        super(clazz);
	}

	/**
	 * @param method String
	 * @param userType String
	 * @return boolean
	 */
	@Override
	protected boolean allowedUserType(final String method, final String userType) {
		switch (UserType.getAnnotationType(userType)) {
		case ADMIN: // only admin can access this page.
			return true;
		default:
			return false;
		}
	}

}
