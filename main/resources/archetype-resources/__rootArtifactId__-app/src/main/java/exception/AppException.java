#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.exception;

import ${package}.constants.GlobalStatusCodes;
import ${package}.constants.ServiceErrorStatus;

/**
 * @author ${author}
 *
 */
public class AppException extends Exception {

	private static final long serialVersionUID = 1L;

	/***/
	private final int m_globalStatus;

	/***/
	private int m_internalStatus = ServiceErrorStatus.INTERNAL_ERROR;

	/**
	 * @param message String
	 */
	public AppException(final String message) {
		this(message, GlobalStatusCodes.INTERNAL_SERVER_ERROR);
	}

	/**
	 * @param message String
	 * @param globalStatus int
	 */
	public AppException(final String message,
						   final int globalStatus) {
		super(message);
		this.m_globalStatus = globalStatus;
	}

	/**
	 * @param message String
	 * @param globalStatus int
	 * @param internalStatus int
	 */
	public AppException(final String message,
						   final int globalStatus,
						   final int internalStatus) {
		super(message);
		this.m_globalStatus = globalStatus;
		this.m_internalStatus = internalStatus;
	}

	/**
	 * @return int
	 */
	public int getGlobalStatus() {
		return m_globalStatus;
	}

	/**
	 * @return int
	 */
	public int getInternalStatus() {
		return m_internalStatus;
	}

}
