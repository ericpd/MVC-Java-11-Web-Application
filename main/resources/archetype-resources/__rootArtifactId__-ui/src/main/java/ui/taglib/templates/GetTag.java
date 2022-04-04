#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.ui.taglib.templates;

import java.util.Hashtable;
import java.util.Stack;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import ${package}.ui.taglib.beans.PageParameter;

/**
 * @author ${author}
 *
 */
public class GetTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	/***/
	private String m_name;

	/**
	 * @param name String
	 */
	public void setName(final String name) {
		this.m_name = name;
	}

	@SuppressWarnings("unchecked")
	@Override
	public final int doStartTag() throws JspException {
		final Stack<Hashtable<Object, Object>> stack = (Stack<Hashtable<Object, Object>>)
				pageContext.getAttribute("template-stack", PageContext.REQUEST_SCOPE);
		if (stack == null) {
			throw new JspException("GetTag.doStartTag(): " + "NO STACK");
		}

		final Hashtable<Object, Object> params = stack.peek();
		if (params == null) {
			throw new JspException("GetTag.doStartTag(): " + "NO HASHTABLE");
		}

		final PageParameter param = (PageParameter) params.get(m_name);
		if (param != null) {
			final String content = param.getContent();
			if (param.isDirect()) {
				try {
					pageContext.getOut().print(content);
				} catch (final java.io.IOException ex) {
					throw new JspException(ex.getMessage());
				}
			} else {
				try {
					pageContext.include(content, true);
				} catch (final Exception ex) {
					throw new JspException(ex.getMessage());
				}
			}
		}
		return SKIP_BODY;
	}

	@Override
	public final void release() {
		m_name = null;
	}
}
