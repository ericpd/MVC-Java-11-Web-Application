#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.hibernate.connection;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import ${package}.hibernate.beans.data.images.JPEGImage;
import ${package}.hibernate.beans.data.images.PNGImage;
import ${package}.hibernate.beans.data.users.Admin;
import ${package}.hibernate.beans.data.users.Member;
import ${package}.hibernate.beans.data.users.Root;
import ${package}.hibernate.beans.extension.SystemSettings;

/**
 * @author ${author}
 *
 */
public final class SessionFactoryUtil {

	/***/
	private static final Logger LOGGER = LogManager.getLogger(SessionFactoryUtil.class);

	/***/
	public static final String CFG_CREATE_DROP = "create-drop";

	/***/
	public static final String CFG_CREATE = "create";

	/***/
	public static final String CFG_UPDATE = "update";

	/***/
	private static SessionFactory sessionFactory;

	/***/
	private SessionFactoryUtil() {

	}

	/**
	 *
	 * @return SessionFactory
	 */
	public static SessionFactory getInstance() {
		if (null == sessionFactory || sessionFactory.isClosed()) {
			loadSessionFactory(CFG_UPDATE);
		}
		return sessionFactory;
	}

	/**
	 * @param config String
	 */
	public static void initSessionFactory(final String config) {
		loadSessionFactory(config);
	}

	/***/
	public static void dropDb() {
	}

	/**
	 * @param hbmAuto String
	 */
	private static void loadSessionFactory(final String hbmAuto) {
		try {
			final Configuration configuration = new Configuration();

			configuration

				// Images
				.addAnnotatedClass(JPEGImage.class)
				.addAnnotatedClass(PNGImage.class)

				// User types
				.addAnnotatedClass(Admin.class)
				.addAnnotatedClass(Root.class)
					.addAnnotatedClass(Member.class)
					
				.addAnnotatedClass(SystemSettings.class);

			configuration.setProperty(Environment.DRIVER, "org.postgresql.Driver");
			configuration.setProperty(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");

			configuration.setProperty(Environment.URL, "jdbc:postgresql://" + System.getProperty("DATABASE_HOST_NAME", "localhost") + ":" +
			System.getProperty("DATABASE_PORT ", "5432") + "/" + System.getProperty("DATABASE_NAME", "${parentArtifactId}"));
			configuration.setProperty(Environment.USER, System.getProperty("DATABASE_USERNAME", "${parentArtifactId}adm"));
			configuration.setProperty(Environment.PASS, System.getProperty("DATABASE_PASSWORD", "${parentArtifactId}adm"));

			configuration.setProperty(Environment.CACHE_PROVIDER_CONFIG, "org.hibernate.cache.internal.NoCacheProvider");

			configuration.setProperty("hibernate.connection.provider_class", "org.hibernate.c3p0.internal.C3P0ConnectionProvider");
			configuration.setProperty("hibernate.c3p0.min_size", "1");
			configuration.setProperty("hibernate.c3p0.max_size", "19");
			configuration.setProperty("hibernate.c3p0.timeout", "120");
			configuration.setProperty("hibernate.c3p0.max_statements", "0");
			configuration.setProperty("hibernate.c3p0.acquire_increment", "1");

			configuration.setProperty(Environment.HBM2DDL_AUTO, hbmAuto);
			configuration.setProperty("hibernate.connection.CharSet", "UTF-8");
			configuration.setProperty("hibernate.connection.characterEncoding", "UTF-8");
			configuration.setProperty("hibernate.connection.useUnicode", "true");

			configuration.setProperty(Environment.HBM2DDL_IMPORT_FILES, "/sql/${parentArtifactId}.sql");

			configuration.setProperty(Environment.SHOW_SQL, System.getProperty("SHOW_SQL", "false"));

			final ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			LOGGER.info("Current database connection initialized...");
		} catch (final HibernateException e) {
			LOGGER.error("Initial SessionFactory creation failed.", e);
			sessionFactory = null;
		}
	}

	/***/
	public static void close() {
		if (null != sessionFactory) {
			sessionFactory.close();
		}
		LOGGER.info("Close current session...");
		sessionFactory = null;
	}

}
