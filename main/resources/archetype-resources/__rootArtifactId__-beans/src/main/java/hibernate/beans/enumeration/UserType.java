#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.hibernate.beans.enumeration;

/**
 * @author ${author}
 *
 */
public enum UserType implements EnumInterface {

	/**
	 * System administrator.
	 */
	ADMIN(UserTypeConst.ROOT_VALUE),

	/**
	 * Root user for the company administrator.
	 */
	ROOT(UserTypeConst.ROOT_VALUE),

	/**
	 * Root member the co-manage the company.
	 */
	MEMBER(UserTypeConst.MEMBER_VALUE),

	/**
	 * UNKNOWN user.
	 */
	UNKNOWN(UserTypeConst.UNKNOWN_VALUE);

	/***/
	private final String m_type;

	/**
	 *
	 * @param type String
	 */
	UserType(final String type) {
		this.m_type = type;
	}

	@Override
	public String getType() {
		return m_type;
	}

	public static UserType getAnnotationType(final String type) {
		switch (type) {
		case UserTypeConst.ADMIN_VALUE:
			return ADMIN;
		case UserTypeConst.ROOT_VALUE:
			return ROOT;
		case UserTypeConst.MEMBER_VALUE:
			return MEMBER;

		default:
			return UNKNOWN;
		}
	}

	public final class UserTypeConst {
		/**
		 * System administrator.
		 */
		public static final String ADMIN_VALUE = "ADMIN";

		/**
		 * Root user for the company administrator.
		 */
		public static final String ROOT_VALUE = "ROOT";

		/**
		 * Root member the co-manage the company.
		 */
		public static final String MEMBER_VALUE = "MEMBER";

		/**
		 * UNKNOWN user.
		 */
		public static final String UNKNOWN_VALUE = "UNKNOWN";

		private UserTypeConst() {

		}

	}
}
