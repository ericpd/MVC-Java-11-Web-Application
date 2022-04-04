#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.ui.servlet.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.json.JSONObject;

import com.google.gson.Gson;
import ${package}.formbeans.ToastMessageFormBean;
import ${package}.ui.constants.WebConst;

/**
 * @author ${author}
 *
 */
public final class Request {

	/***/
	private final Properties m_newAttr;

	/***/
	private final Properties m_attr;

	/***/
	private final Properties m_param;

	/***/
	private final String m_serverScheme;

	/***/
	private final String m_serverName;

	/***/
	private final int m_port;

	/***/
	private final String m_contextPath;

	/***/
	private List<ToastMessageFormBean> m_messages = new ArrayList<>();

	/**
	 * @param attr Properties
	 * @param param Properties
	 * @param serverScheme String
	 * @param serverName String
	 * @param port String
	 * @param contextPath String
	 */
	public Request(final Properties attr,
					final Properties param,
					final String serverScheme,
					final String serverName,
					final int port,
					final String contextPath) {
		m_newAttr = new Properties();
		m_attr = attr;
		m_param = param;
		m_serverScheme = serverScheme;
		m_serverName = serverName;
		m_port = port;
		m_contextPath = contextPath;
	}

	/**
	 * @return the serverScheme
	 */
	public String getServerScheme() {
		return m_serverScheme;
	}

	/**
	 * @return the serverName
	 */
	public String getServerName() {
		return m_serverName;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return m_port;
	}

	/**
	 * @return the contextPath
	 */
	public String getContextPath() {
		if (null == m_contextPath) {
			return "";
		}
		return m_contextPath;
	}

	/**
	 * @return server URL path
	 */
	public String getServerURL() {
		return m_serverScheme + "://" + m_serverName + ":" + m_port + m_contextPath;
	}

	/**
	 * @return server URL path
	 */
	public String getLocalServer() {
		if (("http".equalsIgnoreCase(m_serverScheme) && 80 == m_port) ||
				"https".equalsIgnoreCase(m_serverScheme) && 443 == m_port) {
			return "http://localhost:8080" + m_contextPath;
		}
		return m_serverScheme + "://localhost:" + m_port + m_contextPath;
	}

	/**
	 * @param key String
	 * @return String
	 */
	public String getAttributeAsString(final String key) {
		return getAttributeAsString(key, null);
	}

	/**
	 * @param key String
	 * @param defaultVal String
	 * @return String
	 */
	public String getAttributeAsString(final String key, final String defaultVal) {
		return m_attr.getProperty(key, defaultVal);
	}

	/**
	 * @param key String
	 * @return int
	 */
	public int getAttributeAsInt(final String key) {
		return getAttributeAsInt(key, 0);
	}

	/**
	 * @param key String
	 * @param defaultVal int
	 * @return int
	 */
	public int getAttributeAsInt(final String key, final int defaultVal) {
		try {
			final Object val = m_attr.get(key);
			if (null == val) {
				return defaultVal;
			} else {
				return (int) val;
			}
		} catch (final Exception e) {
			return defaultVal;
		}
	}

	/**
	 * @param key String
	 * @return int
	 */
	public boolean getAttributeAsBoolean(final String key) {
		return getAttributeAsBoolean(key, false);
	}

	/**
	 * @param key String
	 * @param defaultVal int
	 * @return int
	 */
	public boolean getAttributeAsBoolean(final String key, final boolean defaultVal) {
		try {
			final Object val = m_attr.get(key);
			if (null == val) {
				return defaultVal;
			} else {
				return (boolean) val;
			}
		} catch (final Exception e) {
			return defaultVal;
		}
	}

	/**
	 * @param key String
	 * @return JSONObject
	 */
	public JSONObject getJsonAttributeAsInt(final String key) {
		if (null != m_attr.get(key)) {
			return new JSONObject(m_attr.getProperty(key));
		}
		return null;
	}

	/**
	 * @param key String
	 * @return Object
	 */
	public Object get(final String key) {
		return m_attr.get(key);
	}

	/**
	 * @param key String
	 * @return String
	 */
	public String getParameterAsString(final String key) {
		return getParameterAsString(key, null);
	}

	/**
	 * @param key String
	 * @param defaultVal String
	 * @return String
	 */
	public String getParameterAsString(final String key, final String defaultVal) {
		return m_param.getProperty(key, defaultVal);
	}

	/**
	 * @param key String
	 * @return int
	 */
	public int getParameterAsInt(final String key) {
		return getParameterAsInt(key, 0);
	}

	/**
	 * @param key String
	 * @param defaultVal int
	 * @return int
	 */
	public int getParameterAsInt(final String key, final int defaultVal) {
		try {
			final String val = m_param.getProperty(key);
			if (null == val) {
				return defaultVal;
			} else {
				return Integer.parseInt(val);
			}
		} catch (final Exception e) {
			return defaultVal;
		}
	}

	/**
	 * @param key String
	 * @return boolean
	 */
	public boolean getParameterAsBoolean(final String key) {
		return getParameterAsBoolean(key, false);
	}

	/**
	 * @param key String
	 * @param defaultVal int
	 * @return boolean
	 */
	public boolean getParameterAsBoolean(final String key, final boolean defaultVal) {
		try {
			final String val = m_param.getProperty(key);
			if (null == val) {
				return defaultVal;
			} else {
				return Boolean.parseBoolean(val);
			}
		} catch (final Exception e) {
			return defaultVal;
		}
	}

	/**
	 * @param key String
	 * @param value Object
	 * @return Request
	 */
	public Request put(final String key, final Object value) {
		m_newAttr.put(key, value);
		return this;
	}

	/**
	 * @return Properties
	 */
	public Properties get() {
		addMessagesToRequest();
		return m_newAttr;
	}

	private void addMessagesToRequest() {
		final Gson gson = new Gson();
		final String messages = gson.toJson(this.m_messages);
		m_newAttr.put(WebConst.ATTR_TOAST_MESSAGE, messages); // add messages as json string
	}

	/**
	 * @return Properties
	 */
	public Properties getParams() {
		return m_param;
	}

	/**
	 *
	 * @param message {@link ToastMessageFormBean}
	 */
	public void addMessage(final ToastMessageFormBean message) {
		this.m_messages.add(message);
	}

	/**
	 *
	 * @return list of {@link ToastMessageFormBean}
	 */
	public List<ToastMessageFormBean> getMessages() {
		return this.m_messages;
	}

}
