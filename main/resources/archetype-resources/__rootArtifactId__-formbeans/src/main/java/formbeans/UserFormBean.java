#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.formbeans;

import java.io.Serializable;

/**
 *
 * @author ${author}
 *
 */
public class UserFormBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/***/
	private String m_id;

	/***/
	private String m_type;

	/***/
	private String m_image;

	/***/
	private String m_userName;

	/***/
	private String m_displayName;

	/***/
	private boolean m_systemUser;

	/***/
	private boolean m_hasPassword;


	/**
	 *
	 * @return String
	 */
	public String getId() {
		return this.m_id;
	}

	/**
	 *
	 * @param id String
	 * @return UserFormBean
	 */
	public UserFormBean setId(final String id) {
		this.m_id = id;
		return this;
	}

	/**
	 *
	 * @return String
	 */
	public String getUserName() {
		return this.m_userName;
	}

	/**
	 *
	 * @param userName String
	 * @return UserFormBean
	 */
	public UserFormBean setUserName(final String userName) {
		this.m_userName = userName;
		return this;
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return this.m_displayName;
	}

	/**
	 * @param displayName the displayName to set
	 * @return UserFormBean
	 */
	public UserFormBean setDisplayName(final String displayName) {
		this.m_displayName = displayName;
		return this;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return this.m_type;
	}

	/**
	 * @param type the type to set
	 * @return UserFormBean
	 */
	public UserFormBean setType(final String type) {
		this.m_type = type;
		return this;
	}

	/**
	 * @return the image
	 */
	public String getImage() {
		return this.m_image;
	}

	/**
	 * @param image the image to set
	 * @return UserFormBean
	 */
	public UserFormBean setImage(final String image) {
		this.m_image = image;
		return this;
	}

	/**
	 * @return the systemUser
	 */
	public boolean isSystemUser() {
		return this.m_systemUser;
	}

	/**
	 * @param systemUser the systemUser to set
	 * @return UserFormBean
	 */
	public UserFormBean setSystemUser(final boolean systemUser) {
		this.m_systemUser = systemUser;
		return this;
	}

	/**
	 * @return the hasPassword
	 */
	public boolean isHasPassword() {
		return this.m_hasPassword;
	}

	/**
	 * @param hasPassword the hasPassword to set
	 * @return UserFormBean
	 */
	public UserFormBean setHasPassword(final boolean hasPassword) {
		this.m_hasPassword = hasPassword;
		return this;
	}


}
