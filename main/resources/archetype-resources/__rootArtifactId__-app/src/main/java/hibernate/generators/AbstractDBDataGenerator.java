#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.hibernate.generators;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

/**
 * @author ${author}
 *
 */
public abstract class AbstractDBDataGenerator implements IdentifierGenerator {

	/***/
	private final Logger m_logger;

	protected AbstractDBDataGenerator(final Class< ? extends AbstractDBDataGenerator> clazz) {
		this.m_logger = LogManager.getLogger(clazz);
	}

	@Override
	public final Serializable generate(final SharedSessionContractImplementor session, final Object arg1) throws HibernateException {
		final Connection con = session.connection();
		try {
			return getKey(con);
		} catch (final SQLException e) {
			m_logger.error("Failed to generate key.", e);
		}
		return newRandomKey();
	}

	/**
	 *
	 * @param con Connection
	 * @return String
	 * @throws SQLException - sql runtime exception
	 */
	private String getKey(final Connection con) throws SQLException {
		final String newKey = newRandomKey();
		final PreparedStatement pst = con.prepareStatement("select " + getColumnName() + " from " + getTableName() + " where " + getColumnName() + " = ?");
		pst.setString(1, newKey);
		final ResultSet rs = pst.executeQuery();
		if (rs != null && rs.next()) {
			return getKey(con);
		} else {
			return newKey;
		}
	}

	/**
	 *
	 * @return table name
	 */
	protected abstract String getTableName();

	/**
	 *
	 * @return column name
	 */
	protected abstract String getColumnName();

	/**
	 *
	 * @return key
	 */
	protected abstract String newRandomKey();

}
