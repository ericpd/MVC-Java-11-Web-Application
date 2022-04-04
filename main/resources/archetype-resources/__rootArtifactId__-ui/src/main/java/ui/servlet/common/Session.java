#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.ui.servlet.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.json.JSONObject;

/**
 * @author ${author}
 *
 */
public final class Session {

	/***/
	private final String m_sessionID;

	/***/
	private final Properties m_props;

	/***/
	private final List<String> m_removeProps;

	/***/
	private final boolean m_isLoggedIn;

	/***/
	private boolean m_isNew = false;

	/***/
	private boolean m_invalidate = false;

	/**
	 * @param sessionID String
	 * @param props Properties
	 * @param isLoggedIn boolean
	 */
	public Session(final String sessionID, final Properties props, final boolean isLoggedIn) {
		this.m_sessionID = sessionID;
		this.m_props = props;
		this.m_isLoggedIn = isLoggedIn;
		m_removeProps = new ArrayList<>();
	}

	public boolean isUserLoggedIn() {
		return m_isLoggedIn;
	}

	public String getSessionID() {
		return m_sessionID;
	}

	/**
	 * @param key String
	 * @return String
	 */
	public String getAttributeAsString(final String key) {
		return m_props.getProperty(key, null);
	}

	/**
	 * @param key String
	 * @param defaultVal String
	 * @return String
	 */
	public String getAttributeAsString(final String key, final String defaultVal) {
		return m_props.getProperty(key, defaultVal);
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
			final Object val = m_props.get(key);
			if (null == val) {
				return defaultVal;
			} else {
				return (int) val;
			}
		} catch (final NumberFormatException e) {
			return defaultVal;
		}
	}

	/**
	 * @param key String
	 * @return long
	 */
	public long getAttributeAsLong(final String key) {
		return getAttributeAsLong(key, 0L);
	}

	/**
	 * @param key String
	 * @param defaultVal long
	 * @return long
	 */
	public long getAttributeAsLong(final String key, final long defaultVal) {
		try {
			final Object val = m_props.get(key);
			if (null == val) {
				return defaultVal;
			} else {
				return (long) val;
			}
		} catch (final NumberFormatException e) {
			return defaultVal;
		}
	}

	/**
	 * @param key String
	 * @return JSONObject
	 */
	public JSONObject getJsonAttributeAsInt(final String key) {
		if (null != m_props.get(key)) {
			return new JSONObject(m_props.getProperty(key));
		}
		return null;
	}

	/**
	 * @param key String
	 * @param defaultVal String
	 * @return String
	 */
	public boolean getAttributeAsBool(final String key, final boolean defaultVal) {
		final String val = m_props.getProperty(key, null);
		if (null == val) {
			return defaultVal;
		} else {
			return "true".equals(val);
		}
	}

	/**
	 * @param key String
	 * @return Object
	 */
	public Object get(final String key) {
		return m_props.get(key);
	}

	/**
	 * @param key String
	 * @param value Object
	 * @return Session
	 */
	public Session put(final String key, final Object value) {
		m_props.put(key, value);
		return this;
	}

	/**
	 * @param key String
	 * @param value Object
	 * @return Session
	 */
	public Session put(final String key, final boolean value) {
		if (value) {
			m_props.put(key, "true");
		} else {
			m_props.put(key, "false");
		}
		return this;
	}

	/**
	 * @return Properties
	 */
	Properties get() {
		return m_props;
	}

	/**
	 * @return the isNew
	 */
	public boolean isNew() {
		return m_isNew;
	}

	/**
	 * @param isNew the isNew to set
	 * @return Session
	 */
	public Session setNew(final boolean isNew) {
		this.m_isNew = isNew;
		return this;
	}

	/**
	 * @return the invalidate
	 */
	public boolean isInvalidate() {
		return m_invalidate;
	}

	/**
	 * @param invalidate the invalidate to set
	 * @return Session
	 */
	public Session setInvalidate(final boolean invalidate) {
		this.m_invalidate = invalidate;
		return this;
	}

	/**
	 *
	 * @param key String
	 */
	public void remove(final String key) {
		m_removeProps.add(key);
	}

	public List<String> remAttrs() {
		return m_removeProps;
	}

}
