#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.ui.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import ${package}.ui.constants.WebConst;

/**
 * @author ${author}
 *
 */
public class UserTag extends SimpleTagSupport {

    @Override
	public final void doTag() throws JspException, IOException {
    	final String username = (String) getJspContext().getAttribute(WebConst.ATTR_USER_USERID, PageContext.SESSION_SCOPE);
		if (username != null) {
			 getJspContext().getOut().print(username);
		}
    }

}
