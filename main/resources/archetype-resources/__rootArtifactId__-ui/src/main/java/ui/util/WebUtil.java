#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.ui.util;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import ${package}.ui.constants.WebConst;

/**
 * @author ${author}
 *
 */
public final class WebUtil {

	/***/
	private WebUtil() {

	}

	/**
	 * @param request HttpServletRequest
	 * @return boolean
	 */
	public static boolean isAuthenticatedWeb(final HttpServletRequest request) {
		final HttpSession session = request.getSession(false);
		if (null != session) {
			final String userId = (String) session.getAttribute(WebConst.ATTR_USER_USERID);
			if (null != userId) {

				return true;
			}
		}
		return false;
	}

	/**
	 * @param session HttpSession
	 * @param key String
	 * @param defaultVal String
	 * @return String
	 */
	public static String getAttributeAsString(final HttpSession session, final String key, final String defaultVal) {
		if (null != session && null != session.getAttribute(key)) {
			return (String) session.getAttribute(key);
		}
		return defaultVal;
	}

	/**
	 * @param session HttpSession
	 * @param key String
	 * @param defaultVal int
	 * @return int
	 */
	public static int getAttributeAsInt(final HttpSession session, final String key, final int defaultVal) {
		if (null != session && null != session.getAttribute(key)) {
			final String val = session.getAttribute(key).toString();
			try {
				return Integer.parseInt(val);
			} catch (final NumberFormatException e) {
				//
			}
		}
		return defaultVal;
	}

	/**
	 * @param request HttpServletRequest
	 * @param key String
	 * @param defaultVal String
	 * @return String
	 */
	public static String getAttributeAsString(final HttpServletRequest request, final String key, final String defaultVal) {
		if (null != request.getAttribute(key)) {
			return (String) request.getAttribute(key);
		}
		return defaultVal;
	}

	/**
	 * @param request HttpServletRequest
	 * @param key String
	 * @param defaultVal int
	 * @return int
	 */
	public static int getAttributeAsInt(final HttpServletRequest request, final String key, final int defaultVal) {
		if (null != request.getAttribute(key)) {
			final String val = (String) request.getAttribute(key);
			try {
				return Integer.parseInt(val);
			} catch (final NumberFormatException e) {
				//
			}
		}
		return defaultVal;
	}

	/**
	 * @param request HttpServletRequest
	 * @param key String
	 * @return JSONObject
	 */
	public static JSONObject getAttributeAsInt(final HttpServletRequest request, final String key) {
		if (null != request.getAttribute(key)) {
			final String val = (String) request.getAttribute(key);
			try {
				return new JSONObject(val);
			} catch (final JSONException e) {
			}
		}
		return null;
	}

	/**
	 * @param request HttpServletRequest
	 * @param key String
	 * @param defaultVal String
	 * @return String
	 */
	public static String getParameterAsString(final HttpServletRequest request, final String key, final String defaultVal) {
		if (null != request.getParameter(key)) {
			return request.getParameter(key);
		}
		return defaultVal;
	}

	/**
	 * @param request HttpServletRequest
	 * @param key String
	 * @param defaultVal int
	 * @return int
	 */
	public static int getParameterAsInt(final HttpServletRequest request, final String key, final int defaultVal) {
		if (null != request.getParameter(key)) {
			final String val = request.getParameter(key);
			try {
				return Integer.parseInt(val);
			} catch (final NumberFormatException e) {
			}
		}
		return defaultVal;
	}

	/**
	 * @param request HttpServletRequest
	 * @param key String
	 * @param defaultVal int
	 * @return int
	 */
	public static boolean getParameterAsBoolean(final HttpServletRequest request, final String key, final boolean defaultVal) {
		if (null != request.getParameter(key)) {
			final String val = request.getParameter(key);
			try {
				return Boolean.parseBoolean(val);
			} catch (final Exception e) {

			}
		}
		return defaultVal;
	}

	/**
	 * Return true if the field is not empty.
	 *
	 * @param fields to validate
	 * @return valid required field
	 */
	public static boolean requireFieldCheck(final String... fields) {
		for (final String field : fields) {
			if (null == field || "".equals(field.trim())) {
				return false;
			}
		}
		return true;
	}

	/**
	 *
	 * @param text - text data
	 * @param varString - variable to replace in the text
	 * @param varValue - replacement value of the variable
	 * @param args - argument to replace the value
	 * @return modified text
	 */
	public static String replaceAllIfTrue(final String text, final String varString, final String varValue, final boolean args) {
		if (args) {
			return text.replace(varString, varValue);
		}
		return text.replace(varString, ""); //put empty on the variable in exist
	}

	public static boolean isValidEmailAddress(final String email) {
		boolean result = true;
		try {
			final InternetAddress emailAddr = new InternetAddress(email, true);
			emailAddr.validate();
		} catch (final AddressException ex) {
			result = false;
		}
		return result;
	}

}
