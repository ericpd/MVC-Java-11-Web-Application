#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.formdatas;

import java.io.Serializable;

/**
 *
 * @author ${author}
 *
 */
public class UserProfileFormData implements Serializable {

	private static final long serialVersionUID = 1L;

	/***/
	private String m_id;

	/***/
	private String m_firstName;

	/***/
	private String m_lastName;

	/**
	 *
	 * @return String
	 */
	public String getId() {
		return m_id;
	}

	/**
	 *
	 * @param id String
	 */
	public void setId(final String id) {
		this.m_id = id;
	}

	/**
	 *
	 * @return String
	 */
	public String getFirstName() {
		return m_firstName;
	}

	/**
	 *
	 * @param firstName String
	 */
	public void setFirstName(final String firstName) {
		this.m_firstName = firstName;
	}

	/**
	 *
	 * @return String
	 */
	public String getLastName() {
		return m_lastName;
	}

	/**
	 *
	 * @param lastName String
	 */
	public void setLastName(final String lastName) {
		this.m_lastName = lastName;
	}

}
