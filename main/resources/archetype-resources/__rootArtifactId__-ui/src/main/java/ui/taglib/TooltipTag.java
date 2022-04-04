#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.ui.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import ${package}.text.ToolTips;

/**
 * @author ${author}
 */
public class TooltipTag extends SimpleTagSupport {

    /***/
    private String m_key;

    @Override
	public final void doTag() throws JspException, IOException {
        getJspContext().getOut().print(ToolTips.getString(m_key));
    }

    /**
     * @param key String
     */
    public void setKey(final String key) {
        m_key = key;
    }

}
