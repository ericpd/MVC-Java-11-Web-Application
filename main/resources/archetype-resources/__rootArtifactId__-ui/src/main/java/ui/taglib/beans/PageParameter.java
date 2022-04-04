#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.ui.taglib.beans;

import ${package}.text.Labels;
import ${package}.text.Messages;
import ${package}.text.Tags;
import ${package}.text.ToolTips;

/**
 * @author ${author}
 *
 */
public class PageParameter {

	/***/
	private String m_content;

	/***/
	private String m_direct;

	/**
	 * @param content String
	 * @param direct String
	 */
	public PageParameter(final String content,
						 final String direct) {
		this.m_content = content;
		this.m_direct = direct;
	}

	/**
	 * @param content String
	 */
	public void setContent(final String content) {
		m_content = content;
	}

	/**
	 * @param direct String
	 */
	public void setDirect(final String direct) {
		m_direct = direct;
	}

	/**
	 * @return String
	 */
	public String getContent() {
		if (isDirect()) {
			if (m_content.contains("::")) {
				final String[] content = m_content.split("::");
				final String type = content[0];
				final String name = content[1];
				switch (type) {
				case "message":
					return Messages.getString(name);
				case "label":
					return Labels.getString(name);
				case "tooltip":
					return ToolTips.getString(name);
				case "tag":
					return Tags.getString(name);
				default:
					break;
				}
			}
		}
		return m_content;
	}

	/**
	 * @return boolean
	 */
	public boolean isDirect() {
		return Boolean.valueOf(m_direct).booleanValue();
	}
}
