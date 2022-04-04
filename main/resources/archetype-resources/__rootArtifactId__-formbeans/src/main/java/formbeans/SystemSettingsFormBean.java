#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.formbeans;

import java.io.Serializable;

/**
 *
 * @author ${author}
 *
 */

public class SystemSettingsFormBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/***/
	private long m_id;

	/***/
	private String m_name;

	/***/
	private String m_value;

	/***/
	private boolean m_encrypted;

	/***/
	public SystemSettingsFormBean() {
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return m_id;
	}

	/**
	 * @param id the id to set
	 * @return SystemSettings
	 */
	public SystemSettingsFormBean setId(final long id) {
		this.m_id = id;
		return this;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return m_name;
	}

	/**
	 * @param name the name to set
	 * @return SystemSettings
	 */
	public SystemSettingsFormBean setName(final String name) {
		this.m_name = name;
		return this;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return m_value;
	}

	/**
	 * @param value the value to set
	 * @return SystemSettings
	 */
	public SystemSettingsFormBean setValue(final String value) {
		this.m_value = value;
		return this;
	}

	/**
	 * @return the encrypted
	 */
	public boolean isEncrypted() {
		return m_encrypted;
	}

	/**
	 * @param encrypted the encrypted to set
	 * @return SystemSettings
	 */
	public SystemSettingsFormBean setEncrypted(final boolean encrypted) {
		this.m_encrypted = encrypted;
		return this;
	}

}
