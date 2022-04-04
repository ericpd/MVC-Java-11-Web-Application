#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.hibernate.beans.extension;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

import ${package}.hibernate.beans.base.BaseEntity;

/**
 *
 * @author ${author}
 *
 */

@Entity
@Table(name = "dpx_system_setting")
public class SystemSettings extends BaseEntity<SystemSettings> implements Serializable {

	private static final long serialVersionUID = 1L;

	/***/
	public static final String CLM_ID = "m_id";

	/***/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "_id")
	private long m_id;

	/***/
	public static final String CLM_NAME = "m_name";

	/***/
	@NaturalId(mutable = false)
	@Column(name = "name", nullable = false, unique = true)
	private String m_name;

	/***/
	public static final String CLM_VALUE = "m_value";

	/***/
	@Column(name = "value")
	private String m_value;

	/***/
	public static final String CLM_ENCRYPTED = "m_encypted";

	/***/
	@Column(name = "encrypted", columnDefinition = "boolean default false", updatable = true)
	private boolean m_encrypted;

	/***/
	public SystemSettings() {
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
	public SystemSettings setId(final long id) {
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
	public SystemSettings setName(final String name) {
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
	public SystemSettings setValue(final String value) {
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
	public SystemSettings setEncrypted(final boolean encrypted) {
		this.m_encrypted = encrypted;
		return this;
	}

}
