#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.constants;

/**
 * Created by denmark.hinagpis on 2/6/2017.
 */
public final class ServiceErrorStatus {

	/***/
    private ServiceErrorStatus() {

    }

	/***/
    public static final int NETWORK_ERROR = -0x00000001;

	/***/
    public static final int SYSTEM_ERROR = -0x00000002;

	/***/
    public static final int INTERNAL_ERROR = -0x00000003;

	/***/
    public static final int TIMEOUT_ERROR = -0x00000004;

	/***/
    public static final int DATA_ERROR = -0x00000005;

	/***/
    public static final int GUEST_USER = -0x00000006;
}
