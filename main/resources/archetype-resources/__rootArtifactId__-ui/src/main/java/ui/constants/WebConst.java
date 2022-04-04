#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.ui.constants;

/**
 * @author ${author}
 *
 */
public final class WebConst {

	/***/
	private WebConst() {
	}

	/**
	 * Pattern:.
	 * 	Path   - PATH_<PATH_NAME>
	 *  Mathod - METHOD_<PATH_NAME>_xxx
	 */

	/** All JSP page will be using the page template after login. **/
	public static final String JSP_COMMON_PAGE = "/pages/pagetemplate.jsp";

	/** This will load the page on a separate window using the whole page template. **/
	public static final String JSP_CONTENT_PAGE = "/pages/contentonlytemplate.jsp";

	/** This will load the page within the modal through ajax call. **/
	public static final String JSP_AJAX_PAGE = "/pages/ajaxcontenttemplate.jsp";

	//START: Attributes

	/***/
	public static final String ATTR_TOAST_MESSAGE = "toastMessage";

	/***/
	public static final String ATTR_RESPONSE = "response";
	/***/

	public static final String ATTR_STATUS = "status";

	/***/
	public static final String ATTR_MESSAGE = "message";

	/***/
	public static final String ATTR_DATA = "data";

	/***/
	public static final String ATTR_PREV_PAGE = "previousPage";

	/***/
	public static final String ATTR_REDIRECT_URL = "redirectUrl";

	/***/
	public static final String ATTR_LOGIN_ATTEMPT = "login_attempt";

	/***/
	public static final String ATTR_ERROR_CODE = "error_code";

	/***/
	public static final String ATTR_ERROR_MESSAGE = "error_message";


	/***/
	public static final String ATTR_CURRENT_PAGE = "current_page";



	/***/
	public static final String ATTR_ID = "id";

	/***/
	public static final String ATTR_ENCRYPTED = "encrypted";

	/***/
	public static final String ATTR_EDIT = "edit";

	/***/
	public static final String ATTR_VALUE = "value";

	/***/
	public static final String ATTR_USER = "user";

	/***/
	public static final String ATTR_USER_TYPE = "account_type";

	/***/
	public static final String ATTR_USER_USERID = "user_id";

	/***/
	public static final String ATTR_USER_EMAIL = "email";

	/***/
	public static final String ATTR_USER_DISPLAY_NAME = "user_display_name";

	/***/
	public static final String ATTR_CORP_NAME = "corporate_name";

	/***/
	public static final String ATTR_CORP_EMAIL = "corp_email";

	/***/
	public static final String ATTR_USER_USERNAME = "user_name";

	/***/
	public static final String ATTR_USER_PASSWORD = "password";

	/***/
	public static final String ATTR_USER_NEW_PASSWORD = "new_password";

	/***/
	public static final String ATTR_USER_CONFIRM_PASSWORD = "confirm_password";

	/***/
	public static final String ATTR_USER_FIRSTNAME = "first_name";

	/***/
	public static final String ATTR_USER_LASTNAME = "last_name";

	/***/
	public static final String ATTR_USER_FULLNAME = "full_name";

	/***/
	public static final String ATTR_USER_PHONENUMBER = "phone_number";

	/***/
	public static final String ATTR_USER_MESSAGE = "user_message";

	/***/
	public static final String ATTR_USER_IMAGE = "image";

	/***/
	public static final String ATTR_COMPANY = "company";

	/***/
	public static final String ATTR_ENABLED = "enabled";
	//END: Attributes

	// New Line for output response
	/***/
	public static final String NEW_LINE = "<new-line>";

}
