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
public class RootFormBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/***/
	private String m_id;

	/***/
	private String m_rootNumber;

	/***/
	private String m_userName;

	/***/
	private String m_email;

	/***/
	private String m_firstName;

	/***/
	private String m_lastName;

	/***/
	private String m_phoneNumber;

	/**
	 *
	 * @return String
	 */
	public String getId() {
		return m_id;
	}

	/**
	 *
	 * @param id String
	 * @return RootFormBean
	 */
	public RootFormBean setId(final String id) {
		this.m_id = id;
		return this;
	}


	/**
	 * @return the rootNumber
	 */
	public String getRootNumber() {
		return m_rootNumber;
	}

	/**
	 * @param rootNumber the rootNumber to set
	 * @return RootFormBean
	 */
	public RootFormBean setRootNumber(final String rootNumber) {
		this.m_rootNumber = rootNumber;
		return this;
	}

	/**
	 *
	 * @return String
	 */
	public String getUserName() {
		return m_userName;
	}

	/**
	 *
	 * @param userName String
	 * @return RootFormBean
	 */
	public RootFormBean setUserName(final String userName) {
		this.m_userName = userName;
		return this;
	}

	/**
	 *
	 * @return String
	 */
	public String getEmail() {
		return m_email;
	}

	/**
	 *
	 * @param email String
	 * @return RootFormBean
	 */
	public RootFormBean setEmail(final String email) {
		this.m_email = email;
		return this;
	}

	/**
	 *
	 * @return String
	 */
	public String getFirstName() {
		return m_firstName;
	}

	/**
	 *
	 * @param firstName String
	 * @return RootFormBean
	 */
	public RootFormBean setFirstName(final String firstName) {
		this.m_firstName = firstName;
		return this;
	}

	/**
	 *
	 * @return String
	 */
	public String getLastName() {
		return m_lastName;
	}

	/**
	 *
	 * @param lastName String
	 * @return RootFormBean
	 */
	public RootFormBean setLastName(final String lastName) {
		this.m_lastName = lastName;
		return this;
	}

	/**
	 *
	 * @return String
	 */
	public String getPhoneNumber() {
		return m_phoneNumber;
	}

	/**
	 *
	 * @param phoneNumber String
	 * @return RootFormBean
	 */
	public RootFormBean setPhoneNumber(final String phoneNumber) {
		this.m_phoneNumber = phoneNumber;
		return this;
	}
}
