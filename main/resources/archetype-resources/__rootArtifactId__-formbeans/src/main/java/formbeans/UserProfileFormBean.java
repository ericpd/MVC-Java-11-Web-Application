#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.formbeans;

/**
 *
 * @author ${author}
 *
 */
public class UserProfileFormBean extends UserFormBean {

	private static final long serialVersionUID = 1L;

	/***/
	private String m_email;

	/***/
	private String m_fullName;

	/***/
	private String m_firstName;

	/***/
	private String m_lastName;

	public UserProfileFormBean(final UserFormBean user) {
		setId(user.getId()).
		setType(user.getType()).
		setUserName(user.getUserName()).
		setImage(user.getImage()).
		setDisplayName(user.getDisplayName());
		setSystemUser(user.isSystemUser());
		setHasPassword(user.isHasPassword());
	}

	/**
	 *
	 * @return String
	 */
	public String getEmail() {
		return this.m_email;
	}

	/**
	 *
	 * @param email String
	 * @return UserProfileFormBean
	 */
	public UserProfileFormBean setEmail(final String email) {
		this.m_email = email;
		return this;
	}

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return m_fullName;
	}

	/**
	 * @param fullName the fullName to set
	 * @return UserProfileFormBean
	 */
	public UserProfileFormBean setFullName(final String fullName) {
		this.m_fullName = fullName;
		return this;
	}

	/**
	 *
	 * @return String
	 */
	public String getFirstName() {
		return this.m_firstName;
	}

	/**
	 *
	 * @param firstName String
	 * @return UserProfileFormBean
	 */
	public UserProfileFormBean setFirstName(final String firstName) {
		this.m_firstName = firstName;
		return this;
	}

	/**
	 *
	 * @return String
	 */
	public String getLastName() {
		return this.m_lastName;
	}

	/**
	 *
	 * @param lastName String
	 * @return UserProfileFormBean
	 */
	public UserProfileFormBean setLastName(final String lastName) {
		this.m_lastName = lastName;
		return this;
	}

}
