#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.ui.servlet.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ${package}.exception.AppException;
import ${package}.hibernate.connection.SessionFactoryUtil;
import ${package}.ui.servlet.common.ServletSettings;
import ${package}.util.SystemSettingsUtil;

/**
 *
 * @author ${author}
 *
 */
@WebListener
public class ContextListener implements ServletContextListener, HttpSessionListener {

	/***/
	private static final Logger LOGGER = LogManager.getLogger(ContextListener.class);

	@Override
	public final void contextInitialized(final ServletContextEvent event) {
		final ServletContext servletContext = event.getServletContext();
		final ServletSettings settings = new ServletSettings(servletContext);
		servletContext.setAttribute("settings", settings);
		servletContext.setAttribute("system", System.getProperties());

		final String env = System.getProperty("RUN_ENV", "DEV");
		try {
			if (!"BETA".equals(env)) {
				//final Initialize Database Connection
				if (!"Y".equals(System.getProperty("DB_CREATED", "Y"))) {
					LOGGER.info("Creating the database...");
					SessionFactoryUtil.initSessionFactory(SessionFactoryUtil.CFG_CREATE);
					SessionFactoryUtil.close();
				} else {
					LOGGER.info("Updating the database...");
					SessionFactoryUtil.initSessionFactory(SessionFactoryUtil.CFG_UPDATE);
				}

				SystemSettingsUtil.loadSystemSettings();
			}
			LOGGER.info("Context initialized...");
		} catch (final AppException e) {
			LOGGER.error(e);
		}

	}

	@Override
	public final void contextDestroyed(final ServletContextEvent event) {
		//Close Dataase Connection
		SessionFactoryUtil.close();
		LOGGER.info("Context destroid...");
	}

	/**
	 *
	 */
	@Override
	public final void sessionCreated(final HttpSessionEvent event) {
		event.getSession().setMaxInactiveInterval(SystemSettingsUtil.getSettingAsInt("system.session.timeout", 2) * 60); // in seconds
	}

	/**
	 *
	 */
	@Override
	public final void sessionDestroyed(final HttpSessionEvent event) {
	}
}
