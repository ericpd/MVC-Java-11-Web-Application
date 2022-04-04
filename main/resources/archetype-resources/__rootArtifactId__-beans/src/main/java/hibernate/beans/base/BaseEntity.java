#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.hibernate.beans.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author ${author}
 * @param <T> T
 *
 */
@SuppressWarnings("rawtypes")
@MappedSuperclass
public class BaseEntity<T extends BaseEntity> {

	/***/
	public static final String CLM_RETIRED = "m_retired";

	/***/
	@Column(name = "retired", columnDefinition = "boolean default false", updatable = true)
	private boolean m_retired;

	/***/
	public static final String CLM_CREATEDATE = "m_createDate";

	/***/
	@Column(name = "create_date", updatable = false)
	private Date m_createDate;

	/***/
	public static final String CLM_LASTMODIFIEDDATE = "m_lastModifiedDate";

	/***/
	@Column(name = "last_modified_date", updatable = true)
	private Date m_lastModifiedDate;

	/***/
	public static final String CLM_UPDATEDATE = "m_updateDate";

	/***/
	@Column(name = "update_date", updatable = true)
	private Date m_updateDate;

	/***/
	protected BaseEntity() {
		final Date date = new Date();
		m_createDate = date;
		m_lastModifiedDate = date;
		m_updateDate = date;
	}

	/**
	 * @return the retired
	 */
	public boolean getRetired() {
		return m_retired;
	}

	/**
	 * @param retired the retired to set
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public T setRetired(final boolean retired) {
		this.m_retired = retired;
		return (T) this;
	}

	/**
	 *
	 * @return Date
	 */
	public Date getCreateDate() {
		return m_createDate;
	}

	/**
	 *
	 * @return Date
	 */
	public Date getUpdateDate() {
		return m_updateDate;
	}

	/**
	 *
	 * @return Date
	 */
	public Date getLastModifiedDate() {
		return m_lastModifiedDate;
	}

	/**
	 *
	 * @param lastModifiedDate Date
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public T setLastModifiedDate(final Date lastModifiedDate) {
		this.m_lastModifiedDate = lastModifiedDate;
		return (T) this;
	}

	/**
	 *
	 * @param updateDate Date
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public T setUpdateDate(final Date updateDate) {
		this.m_updateDate = updateDate;
		return (T) this;
	}

	/**
	 *
	 * @param createDate Date
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public T setCreateDate(final Date createDate) {
		this.m_createDate = createDate;
		return (T) this;
	}
}
