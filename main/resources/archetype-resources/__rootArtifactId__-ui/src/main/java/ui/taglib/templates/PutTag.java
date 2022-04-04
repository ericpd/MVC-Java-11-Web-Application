#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.ui.taglib.templates;

import java.util.Hashtable;
import java.util.Stack;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import ${package}.ui.taglib.beans.PageParameter;

/**
 * @author ${author}
 *
 */
public class PutTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	/***/
	private String m_name;

	/***/
	private String m_content;

	/***/
	private String m_direct = "false";

	/**
	 * @param name String
	 */
	public void setName(final String name) {
		this.m_name = name;
	}

	/**
	 * @param content String
	 */
	public void setContent(final String content) {
		this.m_content = content;
	}

	/**
	 * @param direct String
	 */
	public void setDirect(final String direct) {
		this.m_direct = direct;
	}

	@Override
	public final int doStartTag() throws JspException {
		// obtain a reference to enclosing insert tag
		final InsertTag parent = (InsertTag) getAncestor("${package}.ui.taglib.templates.InsertTag");
		// put tags must be enclosed in an insert tag
		if (parent == null) {
			throw new JspException("PutTag.doStartTag(): " + "No InsertTag ancestor");
		}
		// get template stack from insert tag
		final Stack<Hashtable<Object, Object>> templateStack = parent.getStack();
		// template stack should never be null
		if (templateStack == null) {
			throw new JspException("PutTag: no template stack");
		}
		// peek at hashtable on the stack
		final Hashtable<Object, Object> params = templateStack.peek();
		// hashtable should never be null either
		if (params == null) {
			throw new JspException("PutTag: no hashtable");
		}
		// put a new PageParameter in the hashtable
		params.put(m_name, new PageParameter(m_content, m_direct));
		return SKIP_BODY; // not interested in tag body, if present
	}

	@Override
	public final void release() {
		m_name = null;
		m_content = null;
		m_direct = null;
	}

	/**
	 * @param className String
	 * @return TagSupport
	 * @throws JspException e
	 */
	private TagSupport getAncestor(final String className) throws JspException {
		Class< ? > klass = null; // can't name variable "class"
		try {
			klass = Class.forName(className);
		} catch (final ClassNotFoundException ex) {
			throw new JspException(ex.getMessage());
		}
		return (TagSupport) findAncestorWithClass(this, klass);
	}

}
