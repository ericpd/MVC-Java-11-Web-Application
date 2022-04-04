#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.ui.util;

import ${package}.hibernate.beans.enumeration.UserType;

/**
 * @author ${author}
 *
 */
public final class MenuAccessUtil {

	private MenuAccessUtil() {

	}

	/**
	 *
	 * @param userType String
	 * @return boolean
	 */
	public static boolean userHasAccessToSettings(final String userType) {
		switch(UserType.getAnnotationType(userType)) {
		case ADMIN:
		case ROOT:
		case MEMBER:
			return true;
		default:
			return false;
		}
	}
}
