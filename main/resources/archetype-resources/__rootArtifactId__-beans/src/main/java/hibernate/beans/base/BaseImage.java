#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.hibernate.beans.base;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 *
 * @author ${author}
 * @param <T>
 *
 */
@SuppressWarnings("rawtypes")
@Entity
@Table(
	name = "dp_image"
)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type",  discriminatorType = DiscriminatorType.STRING)
public class BaseImage<T extends BaseImage> extends BaseEntity<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/***/
	public static final String CLM_ID = "m_id";

	/***/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "_id")
	private long m_id;

	/***/
	public static final String CLM_VALUE = "m_value";

	/***/
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "data", columnDefinition = "bytea")
	private byte[] m_data;

	/***/
	public BaseImage() {
	}

	/**
	 *
	 * @return long
	 */
	public long getId() {
		return m_id;
	}

	/**
	 *
	 * @param id long
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public T setId(final long id) {
		this.m_id = id;
		return (T) this;
	}

	/**
	 * @return the data
	 */
	public byte[] getData() {
		return m_data;
	}

	/**
	 * @param data the data to set
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public T setData(final byte[] data) {
		this.m_data = data;
		return (T) this;
	}

	@Override
	public final String toString() {
		if (null == m_data) {
			return "";
		}
		return new String(m_data);
	}

}
