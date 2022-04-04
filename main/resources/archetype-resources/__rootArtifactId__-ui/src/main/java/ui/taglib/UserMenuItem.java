#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.ui.taglib;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import ${package}.hibernate.beans.enumeration.UserType;
import ${package}.ui.util.MenuAccessUtil;
import ${package}.ui.constants.WebConst;

/**
 * @author ${author}
 *
 */
public class UserMenuItem extends SimpleTagSupport {

	@Override
	public final void doTag() throws JspException, IOException {
		final String userType = (String) getJspContext().getAttribute(WebConst.ATTR_USER_TYPE, PageContext.SESSION_SCOPE);
		final String currentPage = (String) getJspContext().getAttribute(WebConst.ATTR_CURRENT_PAGE, PageContext.REQUEST_SCOPE);
		final ServletContext servletContext = ((PageContext) getJspContext()).getServletContext();
		String contextPath = "";
		if (null != servletContext.getContextPath()) {
			contextPath = servletContext.getContextPath();
		}

		// For admin users
		addSettings(contextPath, currentPage, userType);
	}

	/**
	 *
	 * @param contextPath String
	 * @param currentPage String
	 * @param userType String
	 * @throws IOException e
	 */
	private void addSettings(final String contextPath, final String currentPage, final String userType) throws IOException {
		if (MenuAccessUtil.userHasAccessToSettings(userType)) {
			if (UserType.ADMIN == UserType.getAnnotationType(userType)) {
				print("<a href=\"systemsettings\" class=\"list-group-item list-group-item-action bg-light\">System Settings</a>");
			} else {
				print("<a href=\"settings\" class=\"list-group-item list-group-item-action bg-light\">Settings</a>");
			}
		}
	}

	/**
	 *
	 * @param text String
	 * @throws IOException
	 */
	private void print(final String text) throws IOException {
		getJspContext().getOut().print(text);
	}

}
