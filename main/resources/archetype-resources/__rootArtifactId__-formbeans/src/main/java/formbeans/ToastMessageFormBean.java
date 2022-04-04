#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.formbeans;

import java.io.Serializable;

/**
 * @author ${author}
 *
 */
public class ToastMessageFormBean implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/***/
	private int m_type;

	/***/
	private String m_message;

	/**
	 * @param type int
	 * @param message String
	 */
	public ToastMessageFormBean(final int type, final String message) {
		this.m_type = type;
		this.m_message = message;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return m_type;
	}

	/**
	 * @param type the type to set
	 * @return {@link ToastMessageFormBean}
	 */
	public ToastMessageFormBean setType(final int type) {
		this.m_type = type;
		return this;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return m_message;
	}

	/**
	 * @param message the message to set
	 * @return {@link ToastMessageFormBean}
	 */
	public ToastMessageFormBean setMessage(final String message) {
		this.m_message = message;
		return this;
	}

}
