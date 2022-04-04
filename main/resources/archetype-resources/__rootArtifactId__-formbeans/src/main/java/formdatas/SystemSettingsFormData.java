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

public class SystemSettingsFormData implements Serializable {

	private static final long serialVersionUID = 1L;

	/***/
	private long id;

	/***/
	private String name;

	/***/
	private String value;

	/***/
	private boolean encrypted;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(final String value) {
		this.value = value;
	}

	/**
	 * @return the encrypted
	 */
	public boolean isEncrypted() {
		return encrypted;
	}

	/**
	 * @param encrypted the encrypted to set
	 */
	public void setEncrypted(final boolean encrypted) {
		this.encrypted = encrypted;
	}

}
