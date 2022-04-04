#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.ui.servlet.controller.root;

import ${package}.hibernate.beans.enumeration.UserType;
import ${package}.ui.servlet.common.AbstractWebController;

/**
 * @author ${author}
 *
 */
public class RootBaseController extends AbstractWebController {

	private static final long serialVersionUID = 1L;

	/**
	 * @param clazz Class< ? extends RootBaseController>
	 */
	protected RootBaseController(final Class< ? extends RootBaseController> clazz) {
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
		case ROOT: // only root and members can access this page.
		case MEMBER:
			return true;
		default:
			return false;
		}
	}

}
