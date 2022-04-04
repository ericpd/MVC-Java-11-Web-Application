#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.util;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import ${package}.constants.GlobalStatusCodes;
import ${package}.constants.ServiceErrorStatus;
import ${package}.exception.AppException;
import ${package}.hibernate.beans.extension.SystemSettings;
import ${package}.hibernate.dao.GenericDao;

/**
 * @author ${author}
 *
 */
public final class SystemSettingsUtil {

	/***/
	private static final Logger LOGGER = LogManager.getLogger(SystemSettingsUtil.class);

	private SystemSettingsUtil() {}

	/**
	 * @throws AppException
	 **/
	public static void loadSystemSettings() throws AppException {
		final GenericDao<SystemSettings> dao = new GenericDao<>(SystemSettings.class);
		try {
			final List<SystemSettings> list = dao.getAll();
			if (list == null || list.isEmpty()) {
				LOGGER.error("Failed to load initial data into the database...");
			} else if (!"Y".equals(System.getProperty("DB_CREATED", "N"))) { // only show for create database
				LOGGER.info("Initial data successfully loaded...");
			}
			for (final SystemSettings systemSetting : list) {
				if (systemSetting.isEncrypted()) {
					System.setProperty(systemSetting.getName(), Crypt.decryptHexString(systemSetting.getValue()));
				} else {
					System.setProperty(systemSetting.getName(), systemSetting.getValue());
				}
			}
		} catch (final Exception e) {
			LOGGER.error("Failed to load system settings.", e);
			throw new AppException("Failed to load system settings.", GlobalStatusCodes.INTERNAL_SERVER_ERROR, ServiceErrorStatus.INTERNAL_ERROR);
		} finally {
			dao.closeCurrentSession();
		}
	}

	public static int getSettingAsInt(final String name) {
		return getSettingAsInt(name, -1);
	}

	public static int getSettingAsInt(final String name, final int defaultValue) {
		try {
			final String val = System.getProperty(name);
			if (null == val) {
				return defaultValue;
			} else {
				return Integer.parseInt(val);
			}
		} catch (final Exception e) {
			return defaultValue;
		}
	}

	public static boolean getSettingAsBool(final String name) {
		return getSettingAsBool(name, false);
	}

	public static boolean getSettingAsBool(final String name, final boolean defaultValue) {
		try {
			final String val = System.getProperty(name);
			if (null == val || !"true".equalsIgnoreCase(val)) {
				return defaultValue;
			}
			return true;
		} catch (final Exception e) {
			return defaultValue;
		}
	}

	public static String getSetting(final String name) {
		return getSetting(name, null);
	}

	public static String getSetting(final String name, final String defaultValue) {
		return System.getProperty(name, defaultValue);
	}

}
