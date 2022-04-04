#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.ui.servlet.common;

import java.io.Serializable;

import javax.servlet.ServletContext;

import ${package}.util.SystemSettingsUtil;

/**
 * @author ${author}
 */
public final class ServletSettings implements Serializable {

	private static final long serialVersionUID = 8161950760514433245L;

	/***/
	private ServletContext m_servletContext;

	/***/
	private String m_outputEncoding;

	/***/
	private int m_sessionTimeout;

	/**
	 * @param servletContext ServletContext
	 */
	public ServletSettings(final ServletContext servletContext) {
		m_servletContext = servletContext;
		init();
	}

	/**
	 * @return ServletContext
	 */
	public ServletContext getServletContext() {
		return m_servletContext;
	}

	/**
	 * @return String
	 */
	public String getOutputEncoding() {
		return m_outputEncoding;
	}

	/**
	 * @return int
	 */
	public int getSessionTimeout() {
		return m_sessionTimeout;
	}

	/***/
	private void init() {
		setDefaultEncoding();
		setDefaultTimeout();
	}

	/**
	 * @param key String
	 * @param defaultValue String
	 * @return String
	 */
	public String getApplicationProperty(final String key, final String defaultValue) {
		final String value = m_servletContext.getInitParameter(key);
		if (value != null) {
			return value;
		} else {
			return defaultValue;
		}
	}

	/**
	 * @param key String
	 * @param defaultValue int
	 * @return int
	 */
	public int getApplicationProperty(final String key, final int defaultValue) {
		final String value = m_servletContext.getInitParameter(key);
		if (value != null) {
			try {
				return Integer.parseInt(value);
			} catch (final NumberFormatException e) {
				// Do nothing
			}
		}
		return defaultValue;
	}

	/***/
	private void setDefaultEncoding() {
		m_outputEncoding = getApplicationProperty("output-encoding", "UTF-8");
	}

	/***/
	private void setDefaultTimeout() {
		m_sessionTimeout = getApplicationProperty("session-timeout", SystemSettingsUtil.getSettingAsInt("system.session.timeout", 2) * 60);
	}

}
