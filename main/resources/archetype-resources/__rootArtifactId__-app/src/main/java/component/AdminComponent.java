#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.hibernate.HibernateException;

import ${package}.constants.GlobalStatusCodes;
import ${package}.constants.ServiceErrorStatus;
import ${package}.exception.AppException;
import ${package}.exception.ComponentException;
import ${package}.formbeans.SystemSettingsFormBean;
import ${package}.formdatas.SystemSettingsFormData;
import ${package}.hibernate.beans.extension.SystemSettings;
import ${package}.hibernate.dao.GenericDao;
import ${package}.text.Messages;
import ${package}.util.AppUtil;
import ${package}.util.Crypt;

/**
 * @author ${author}
 *
 */
public class AdminComponent extends BaseComponent {

	/***/
	public AdminComponent() {
		super(AdminComponent.class);
	}

	/**
	 *
	 * @return List of SystemSettingsFormBean
	 * @throws AppException e
	 */
	public List<SystemSettingsFormBean> getAllSystemSettings() throws AppException {
		getLogger().debug("START: " + this.getClass().getCanonicalName() + "." + new Throwable().getStackTrace()[0].getMethodName());
		final GenericDao<SystemSettings> dao = new GenericDao<>(SystemSettings.class);
		try {
			final List<SystemSettingsFormBean> listBean = new ArrayList<>();
			final List<SystemSettings> list = dao.getAll();
			list.sort(new Comparator<SystemSettings>() {

				@Override
				public int compare(final SystemSettings o1, final SystemSettings o2) {
					return (int) (o1.getId() - o2.getId());
				}
			});
			for (final SystemSettings setting : list) {
				listBean.add(
						new SystemSettingsFormBean()
						.setId(setting.getId())
						.setName(setting.getName())
						.setValue(setting.getValue())
						.setEncrypted(setting.isEncrypted())
						);
			}
			return listBean;
		} catch (final HibernateException e) {
			getLogger().error(e);
			dao.rollback();
			throw new AppException(Messages.getString("Error.System.Common"), GlobalStatusCodes.INTERNAL_SERVER_ERROR);
		} finally {
			dao.closeCurrentSession();
			getLogger().debug("END: " + this.getClass().getCanonicalName() + "." + new Throwable().getStackTrace()[0].getMethodName());
		}
	}

	/**
	 *
	 * @param id long
	 * @param encrypted boolean
	 * @return SystemSettingsFormBean
	 * @throws AppException e
	 */
	public SystemSettingsFormBean getSystemSetting(final long id, final boolean encrypted) throws AppException {
		getLogger().debug("START: " + this.getClass().getCanonicalName() + "." + new Throwable().getStackTrace()[0].getMethodName());
		final GenericDao<SystemSettings> dao = new GenericDao<>(SystemSettings.class);
		try {
			final SystemSettings setting = dao.findById(id);
			if (encrypted != setting.isEncrypted()) {
				String value = "";
				if (encrypted) {
					value = Crypt.encryptHexString(setting.getValue());
				} else {
					value = Crypt.decryptHexString(setting.getValue());
				}
				setting.setValue(value);
				setting.setEncrypted(encrypted);
				dao.persist(setting);
				dao.commit();

				String settingVal = value;
				if (encrypted) {
					settingVal = Crypt.decryptHexString(settingVal);
				}
				System.setProperty(setting.getName(), settingVal);
			}
			return new SystemSettingsFormBean()
					.setId(setting.getId())
					.setName(setting.getName())
					.setValue(setting.getValue())
					.setEncrypted(setting.isEncrypted());
		} catch (final HibernateException e) {
			getLogger().error(e);
			dao.rollback();
			throw new AppException(Messages.getString("Error.System.Common"), GlobalStatusCodes.INTERNAL_SERVER_ERROR);
		} finally {
			dao.closeCurrentSession();
			getLogger().debug("END: " + this.getClass().getCanonicalName() + "." + new Throwable().getStackTrace()[0].getMethodName());
		}
	}

	/**
	 *
	 * @param id long
	 * @param value String
	 * @param encrypted boolean
	 * @return SystemSettingsFormBean
	 * @throws AppException e
	 */
	public SystemSettingsFormBean editSystemSetting(final long id, final String value, final boolean encrypted) throws AppException {
		getLogger().debug("START: " + this.getClass().getCanonicalName() + "." + new Throwable().getStackTrace()[0].getMethodName());
		final GenericDao<SystemSettings> dao = new GenericDao<>(SystemSettings.class);
		try {
			final SystemSettings setting = dao.findById(id);
			String newValue = value;
			if (encrypted) {
				newValue = Crypt.encryptHexString(value);
			}
			setting.setValue(newValue);
			setting.setEncrypted(encrypted);
			dao.persist(setting);
			dao.commit();

			System.setProperty(setting.getName(), value);

			return new SystemSettingsFormBean()
					.setId(setting.getId())
					.setName(setting.getName())
					.setValue(setting.getValue())
					.setEncrypted(setting.isEncrypted());
		} catch (final HibernateException e) {
			getLogger().error(e);
			dao.rollback();
			throw new AppException(Messages.getString("Error.System.Common"), GlobalStatusCodes.INTERNAL_SERVER_ERROR);
		} finally {
			dao.closeCurrentSession();
			getLogger().debug("END: " + this.getClass().getCanonicalName() + "." + new Throwable().getStackTrace()[0].getMethodName());
		}
	}

	/**
	 *
	 * @param data {@link SystemSettingsFormData}
	 * @return {@link SystemSettingsFormBean}
	 * @throws AppException e
	 */
	public SystemSettingsFormBean addSystemSetting(final SystemSettingsFormData data) throws AppException {
		getLogger().debug("START: " + this.getClass().getCanonicalName() + "." + new Throwable().getStackTrace()[0].getMethodName());
		final GenericDao<SystemSettings> dao = new GenericDao<>(SystemSettings.class);
		try {
			final List<SystemSettings> settings = dao.findByColumnName(SystemSettings.CLM_NAME, data.getName());
			if (!AppUtil.isNullOrEmpty(settings)) {
				throw new ComponentException("System setting already exists...", GlobalStatusCodes.BAD_REQUEST, ServiceErrorStatus.DATA_ERROR);
			}

			String value = data.getValue();
			if (data.isEncrypted()) {
				value = Crypt.encryptHexString(value);
			}

			final SystemSettings setting = new SystemSettings()
					.setName(data.getName())
					.setValue(value)
					.setEncrypted(data.isEncrypted());

			dao.save(setting);
			dao.commit();

			return new SystemSettingsFormBean()
					.setId(setting.getId())
					.setName(setting.getName())
					.setValue(setting.getValue())
					.setEncrypted(setting.isEncrypted());
		} catch (final HibernateException e) {
			getLogger().error(e);
			dao.rollback();
			throw new AppException(Messages.getString("Error.System.Common"), GlobalStatusCodes.INTERNAL_SERVER_ERROR);
		} catch (final ComponentException e) {
			getLogger().error(e);
			dao.rollback();
			throw e;
		} finally {
			dao.closeCurrentSession();
			getLogger().debug("END: " + this.getClass().getCanonicalName() + "." + new Throwable().getStackTrace()[0].getMethodName());
		}
	}
}
