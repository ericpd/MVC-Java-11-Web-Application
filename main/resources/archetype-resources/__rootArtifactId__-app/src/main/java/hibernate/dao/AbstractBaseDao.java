#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.hibernate.dao;

import java.io.Serializable;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import ${package}.hibernate.connection.SessionFactoryUtil;

/**
 *
 * @author ${author}
 * @param <T>
 *
 */
abstract class AbstractBaseDao<T extends Serializable> {

	/***/
	private final Logger m_logger;

	/***/
	private Session m_currentSession;

	/***/
	private Transaction m_currentTransaction;

	/**
	 * @param clazz Class< ? extends AbstractBaseDao>
	 * @param dao AbstractBaseDao of ? that extends AbstractBaseDao
	 */
	@SuppressWarnings("rawtypes")
	protected AbstractBaseDao(final Class< ? extends AbstractBaseDao> clazz,
							  final AbstractBaseDao< ? extends AbstractBaseDao> dao) {
		this.m_logger = LogManager.getLogger(clazz);
		if (null != dao) {
			this.m_currentSession = dao.getCurrentSession();
			this.m_currentTransaction = dao.getCurrentTransaction();
		} else {
			this.m_currentSession = null;
			this.m_currentTransaction = null;
		}
	}

	/***/
	private void openNewSessionwithTransaction() {
		m_currentSession = SessionFactoryUtil.getInstance().openSession();
		m_currentTransaction = m_currentSession.beginTransaction();
	}

	/***/
	public void commit() {
		if (null != m_currentTransaction && m_currentTransaction.isActive()) {
			m_currentTransaction.commit();
		}
	}

	/***/
	public void flush() {
		if (null != m_currentSession && m_currentSession.isConnected()) {
			m_currentSession.flush();
		}
	}

	/***/
	public void rollback() {
		if (null != m_currentTransaction) {
			m_currentTransaction.rollback();
		}
	}

	/***/
	public void closeCurrentSession() {
		if (null != m_currentSession) {
			m_currentSession.close();
			m_currentSession = null;
		}
	}

	/**
	 *
	 * @return Session
	 */
	public Session getCurrentSession() {
		if (null == m_currentSession || !m_currentSession.isConnected()) {
			openNewSessionwithTransaction();
		}
		return m_currentSession;
	}

	/**
	 *
	 * @return Transaction
	 */
	public Transaction getCurrentTransaction() {
		return m_currentTransaction;
	}

	/**
	 * @param value String
	 * @return boolean
	 */
	protected boolean isNullOrEmpty(final String value) {
		if (null == value || value.trim().isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * @param clazz Class
	 * @param id Serializable
	 * @return Object
	 */
	protected T findEntityById(final Class<T> clazz,
									final Serializable id) {
		return getCurrentSession().get(clazz, id);
	}

	/**
	 *
	 * @param obj Object
	 * @return Serializable
	 */
	@SuppressWarnings("unchecked")
	public T saveEntity(final T obj) {
		return (T) getCurrentSession().save(obj);
	}

	/**
	 * @param obj Object
	 */
	protected void persistEntity(final T obj) {
		getCurrentSession().update(obj);
	}

	/**
	 * @param obj Object
	 */
	protected void deleteEntity(final T obj) {
		getCurrentSession().delete(obj);
	}

	/**
	 * @param message String
	 */
	protected void debug(final String message) {
		m_logger.debug(message);
	}

	/**
	 * @param message String
	 * @param t Throwable
	 */
	protected void debug(final String message, final Throwable t) {
		m_logger.debug(message, t);
	}

	/**
	 * @param message String
	 */
	protected void info(final String message) {
		m_logger.info(message);
	}

	/**
	 * @param message String
	 * @param t Throwable
	 */
	protected void info(final String message, final Throwable t) {
		m_logger.info(message, t);
	}

	/**
	 * @param message String
	 */
	protected void warn(final String message) {
		m_logger.warn(message);
	}

	/**
	 * @param message String
	 * @param t Throwable
	 */
	protected void warn(final String message, final Throwable t) {
		m_logger.warn(message, t);
	}

	/**
	 * @param message String
	 */
	protected void error(final String message) {
		m_logger.error(message);
	}

	/**
	 * @param message String
	 * @param t Throwable
	 */
	protected void error(final String message, final Throwable t) {
		m_logger.error(message, t);
	}
}
