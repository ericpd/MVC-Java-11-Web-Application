#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.exception;

/**
 * @author ${author}
 *
 */
public class ComponentException extends AppException {

	private static final long serialVersionUID = 1L;


	/**
	 * @param message String
	 */
	public ComponentException(final String message) {
		super(message);
	}

	/**
	 * @param message String
	 * @param globalStatus int
	 */
	public ComponentException(final String message,
						   final int globalStatus) {
		super(message, globalStatus);
	}

	/**
	 * @param message String
	 * @param globalStatus int
	 * @param internalStatus int
	 */
	public ComponentException(final String message,
						   final int globalStatus,
						   final int internalStatus) {
		super(message, globalStatus, internalStatus);
	}

}
