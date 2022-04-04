#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.constants;

/**
 * @author ${author}
 *
 */
public final class GlobalStatusCodes {

	/***/
	private GlobalStatusCodes() {

	}

	/***/
	public static final int OK = 200;

	/***/
	public static final int CREATED = 201;

	/***/
	public static final int ACCEPTED = 202;

	/***/
	public static final int NO_CONTENT = 204;


	/***/
	public static final int BAD_REQUEST = 400;

	/***/
	public static final int UNAUTHORIZED = 401;

	/***/
	public static final int FORBIDDEN = 403;

	/***/
	public static final int NOT_FOUND = 404;

	/***/
	public static final int METHOD_NOT_ALLOWED = 405;

	/***/
	public static final int NOT_ACCEPTABLE = 406;

	/***/
	public static final int REQUEST_TIMEOUT = 408;


	/***/
	public static final int INTERNAL_SERVER_ERROR = 500;

	/***/
	public static final int NOT_IMPLEMENTED = 501;

	/***/
	public static final int BAD_GATEWAY = 502;

	/***/
	public static final int SERVICE_UNAVAILABLE = 503;

	/***/
	public static final String NOT_FOUND_MESSAGE = "Page not found.";

}
