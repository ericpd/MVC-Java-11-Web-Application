#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.ui.taglib.templates;

import java.util.Hashtable;
import java.util.Stack;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author ${author}
 *
 */
public class InsertTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	/***/
	private String m_template;

	/***/
	private Stack<Hashtable<Object, Object>> m_stack;

	/**
	 * @param template String
	 */
	public void setTemplate(final String template) {
		this.m_template = template;
	}

	@Override
	public final int doStartTag() throws JspException {
		m_stack = getStack(); // obtain a reference to the template stack
		m_stack.push(new Hashtable<>()); // push new hashtable onto stack
		return EVAL_BODY_INCLUDE; // pass tag body through unchanged
	}

	@Override
	public final int doEndTag() throws JspException {
		try {
			pageContext.include(m_template); // include template
		} catch (final Exception ex) { // IOException or ServletException
			throw new JspException(ex.getMessage()); // recast exception
		}
		m_stack.pop(); // pop hashtable off stack
		return EVAL_PAGE; // evaluate the rest of the page after the tag
	}

	@Override
	public final void release() {
		m_template = null;
		m_stack = null;
	}

	/**
	 * @return Stack
	 */
	@SuppressWarnings("unchecked")
	public Stack<Hashtable<Object, Object>> getStack() {
		Stack<Hashtable<Object, Object>> s = (Stack<Hashtable<Object, Object>>) pageContext.getAttribute("template-stack", PageContext.REQUEST_SCOPE);
		if (s == null) {
			s = new Stack<>();
			pageContext.setAttribute("template-stack", s, PageContext.REQUEST_SCOPE);
		}
		return s;
	}

}
