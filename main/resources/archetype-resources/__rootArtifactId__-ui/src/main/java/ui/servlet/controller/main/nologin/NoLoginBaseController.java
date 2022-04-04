#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.ui.servlet.controller.main.nologin;

import ${package}.ui.servlet.common.AbstractWebController;

/**
 * @author ${author}
 *
 */
public class NoLoginBaseController extends AbstractWebController {

	private static final long serialVersionUID = 1L;

	/**
	 * @param clazz Class< ? extends NoLoginBaseController>
	 */
	protected NoLoginBaseController(final Class< ? extends NoLoginBaseController> clazz) {
        super(clazz);
	}

	/**
	 * @param method
	 * @return boolean
	 */
	@Override
	protected boolean byPassLogin(final String method) {
		return true;
	}

}
