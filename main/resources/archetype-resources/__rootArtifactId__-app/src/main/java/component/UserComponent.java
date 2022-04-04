#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.component;

import java.util.Date;

import org.hibernate.HibernateException;

import ${package}.constants.GlobalStatusCodes;
import ${package}.constants.ServiceErrorStatus;
import ${package}.exception.AppException;
import ${package}.exception.ComponentException;
import ${package}.formbeans.UserFormBean;
import ${package}.formbeans.UserPhotoFormBean;
import ${package}.formbeans.UserProfileFormBean;
import ${package}.formdatas.UserPhotoFormData;
import ${package}.formdatas.UserProfileFormData;
import ${package}.hibernate.beans.base.BaseUser;
import ${package}.hibernate.beans.data.users.Member;
import ${package}.hibernate.beans.data.users.Root;
import ${package}.hibernate.beans.enumeration.FileType;
import ${package}.hibernate.beans.enumeration.UserType;
import ${package}.hibernate.dao.UserDao;
import ${package}.hibernate.generators.RandomGenerator;
import ${package}.text.Messages;
import ${package}.util.AppUtil;
import ${package}.util.Crypt;
import ${package}.util.SystemSettingsUtil;

/**
 * @author ${author}
 *
 */
public class UserComponent extends BaseComponent {

	/***/
	public UserComponent() {
		super(UserComponent.class);
	}

	/**
	 * @param firstName String
	 * @param lastName String
	 * @param email String
	 * @param password String
	 * @return UserFormBean
	 * @throws AppException e
	 */
	@SuppressWarnings("rawtypes")
	public UserFormBean signupRoot(final String firstName,
			final String lastName,
			final String email,
			final String password) throws AppException {
		getLogger().debug("START: " + this.getClass().getCanonicalName() + "." + new Throwable().getStackTrace()[0].getMethodName());
		final UserDao<BaseUser> dao = new UserDao<>(BaseUser.class);
		try {
			final BaseUser user = dao.findUserByColumnName(BaseUser.CLM_EMAIL, email, false);
			if (null != user) {
				throw new ComponentException(Messages.getString("Error.SignUp.UserExist"), GlobalStatusCodes.BAD_REQUEST, ServiceErrorStatus.DATA_ERROR);
			}

			final UserDao<Root> rootDao = new UserDao<>(Root.class, dao);
			final String confirmationNumber = generateConfirmationNumber(dao);
			final Root rootUser = new Root()
					.setUserName(generateUserName(dao, firstName, lastName))
					.setConfirmationNumber(confirmationNumber)
					.setFirstName(firstName)
					.setLastName(lastName)
					.setEmail(email)
					.setPassword(Crypt.encryptHexString(password))
					.setConfirmationNumber(null)
					.setConfirmed(true)
					.setConfirmedDate(new Date());

			// save user date to database
			rootDao.save(rootUser);

			// if everything is done, commit transaction
			dao.commit();

			return toUserFormBean(rootUser);
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

	/**
	 * @param email String
	 * @param password String
	 * @return UserFormBean
	 * @throws AppException e
	 */
	@SuppressWarnings("rawtypes")
	public UserFormBean login(final String email, final String password) throws AppException {
		getLogger().debug("START: " + this.getClass().getCanonicalName() + "." + new Throwable().getStackTrace()[0].getMethodName());
		final UserDao<BaseUser> dao = new UserDao<>(BaseUser.class);
		try {
			BaseUser user = dao.findUserByColumnName(BaseUser.CLM_EMAIL, email, false);
			if (null == user) {
				user = dao.findUserByColumnName(BaseUser.CLM_USERNAME, email);
			}

			// use not found or user hasn't set their password
			if ((null == user) || SystemSettingsUtil.getSetting("user.default.pass", "@@@@@@").equals(password)) {
				throw new ComponentException(Messages.getString("Error.Login.Invalid"),
						GlobalStatusCodes.BAD_REQUEST,
						ServiceErrorStatus.DATA_ERROR);
			}

			// Is system user
			if (user.isSystemUser()) {
				if (AppUtil.disableSystemUser(user.isSystemUser()) || sustemUserPasswordInvalid(password)) {
					throw new ComponentException(Messages.getString("Error.Login.Invalid"),
							GlobalStatusCodes.BAD_REQUEST,
							ServiceErrorStatus.DATA_ERROR);
				}
			} else
				// regular user
				if (!AppUtil.decryptPassword(user.getPassword()).equals(password) || !user.isConfirmed()) {
					throw new ComponentException(Messages.getString("Error.Login.Invalid"),
							GlobalStatusCodes.BAD_REQUEST,
							ServiceErrorStatus.DATA_ERROR);
				}

			return toUserFormBean(user);
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

	/**
	 *
	 * @param id String
	 * @return UserFormBean
	 * @throws AppException e
	 */
	@SuppressWarnings("rawtypes")
	public UserProfileFormBean getUserProfile(final String id) throws AppException {
		getLogger().debug("START: " + this.getClass().getCanonicalName() + "." + new Throwable().getStackTrace()[0].getMethodName());
		final UserDao<BaseUser> dao = new UserDao<>(BaseUser.class);
		try {
			final BaseUser user = dao.findById(id);
			if (null == user) {
				throw new ComponentException(Messages.getString("Error.System.InvalidParameter"),
						GlobalStatusCodes.BAD_REQUEST,
						ServiceErrorStatus.DATA_ERROR);
			}

			return toUserProfileFormBean(user);
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

	/**
	 *
	 * @param email String
	 * @param newPassword String
	 * @throws AppException e
	 */
	@SuppressWarnings("rawtypes")
	public void changePassword(final String email, final String newPassword) throws AppException {
		getLogger().debug("START: " + this.getClass().getCanonicalName() + "." + new Throwable().getStackTrace()[0].getMethodName());
		final UserDao<BaseUser> dao = new UserDao<>(BaseUser.class);
		try {
			final BaseUser user = dao.findUserByColumnName(BaseUser.CLM_EMAIL, email, false);
			if (null == user) {
				throw new ComponentException(Messages.getString("Error.ChangePassword.InvalidEmail"),
						GlobalStatusCodes.BAD_REQUEST,
						ServiceErrorStatus.DATA_ERROR);
			}

			final String resetKey = generatePwdResetKey(dao);

			user.setChgPwdKey(resetKey);
			user.setChgPwdExpired(AppUtil.addDays(AppUtil.getCurrentDate(), 1));
			user.setPassword(Crypt.encryptHexString(newPassword));

			dao.persist(user);
			dao.commit();
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

	/**
	 *
	 * @param userId String
	 * @return UserFormBean
	 * @throws AppException e
	 */
	@SuppressWarnings("rawtypes")
	public UserFormBean getUserInfo(final String userId) throws AppException {
		getLogger().debug("START: " + this.getClass().getCanonicalName() + "." + new Throwable().getStackTrace()[0].getMethodName());
		final UserDao<BaseUser> dao = new UserDao<>(BaseUser.class);
		try {
			final BaseUser user = dao.findById(userId);
			if (null == user) {
				throw new ComponentException("Invalid user id.",
						GlobalStatusCodes.BAD_REQUEST,
						ServiceErrorStatus.DATA_ERROR);
			}

			return toUserFormBean(user);
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

	/**
	 *
	 * @param data UserProfileFormData
	 * @return UserProfileFormBean
	 * @throws AppException e
	 */
	@SuppressWarnings("rawtypes")
	public UserProfileFormBean updateProfileInfo(final UserProfileFormData data) throws AppException {
		getLogger().debug("START: " + this.getClass().getCanonicalName() + "." + new Throwable().getStackTrace()[0].getMethodName());
		final UserDao<BaseUser> dao = new UserDao<>(BaseUser.class);
		try {
			final BaseUser user = dao.findById(data.getId());
			if (null == user) {
				throw new ComponentException(Messages.getString("Error.User.InvalidUser"),
						GlobalStatusCodes.BAD_REQUEST,
						ServiceErrorStatus.DATA_ERROR);
			}

			if (hasChange(user.getFirstName(), data.getFirstName())) {
				user.setFirstName(data.getFirstName());
			}

			if (hasChange(user.getLastName(), data.getLastName())) {
				user.setLastName(data.getLastName());
			}

			dao.persist(user);
			dao.commit();

			return toUserProfileFormBean(user);
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

	/**
	 *
	 * @param userId String
	 * @return boolean
	 * @throws AppException e
	 */
	@SuppressWarnings("rawtypes")
	public boolean deleteUser(final String userId) throws AppException {
		getLogger().debug("START: " + this.getClass().getCanonicalName() + "." + new Throwable().getStackTrace()[0].getMethodName());
		final UserDao<BaseUser> dao = new UserDao<>(BaseUser.class);
		try {
			final BaseUser user = dao.findById(userId);
			if (null == user) {
				throw new ComponentException(Messages.getString("Error.System.InvalidParameter"), GlobalStatusCodes.BAD_REQUEST, ServiceErrorStatus.DATA_ERROR);
			}

			// If user is corporate, delete Assessee and Members
			if (UserType.ROOT == UserType.getAnnotationType(user.getType())) {
				final UserDao<Root> rootDao = new UserDao<>(Root.class, dao);
				final Root root = rootDao.findById(userId);

				// Delete Members
				final UserDao<Member> membersDao = new UserDao<>(Member.class, dao);
				for (final Member member : root.getMembers()) {
					member.setRetired(true);
					membersDao.persist(member);
				}
			}

			user.setRetired(true);
			dao.persist(user);

			dao.commit();

			return true;
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

	/**
	 * @param userId String
	 * @param email String
	 * @param newPassword String
	 * @param currentPassword String
	 * @return boolean
	 * @throws AppException
	 */
	@SuppressWarnings("rawtypes")
	public boolean updateAccount(final String userId, final String email, final String newPassword, final String currentPassword) throws AppException {
		getLogger().debug("START: " + this.getClass().getCanonicalName() + "." + new Throwable().getStackTrace()[0].getMethodName());
		final UserDao<BaseUser> dao = new UserDao<>(BaseUser.class);
		try {
			final BaseUser user = dao.findById(userId);
			if ((null == user) ||
					AppUtil.disableSystemUser(user.isSystemUser()) ||
					!AppUtil.decryptPassword(user.getPassword()).equals(currentPassword)) {
				throw new ComponentException(Messages.getString("Error.ChangePassword.Invalid"),
						GlobalStatusCodes.BAD_REQUEST,
						ServiceErrorStatus.DATA_ERROR);
			}
			if (!user.isConfirmed()) {
				throw new ComponentException(Messages.getString("Error.ChangePassword.Invalid"),
						GlobalStatusCodes.BAD_REQUEST,
						ServiceErrorStatus.DATA_ERROR);
			}

			if (hasChange(user.getEmail(), email)) {
				user.setEmail(email);
				dao.persist(user);
				dao.commit();
			}

			changePassword(email, newPassword);

			return true;
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

	/**
	 *
	 * @param user
	 * @return UserFormBean
	 */
	@SuppressWarnings("rawtypes")
	private UserFormBean toUserFormBean(final BaseUser user) {
		return new UserFormBean()
				.setId(user.getId())
				.setType(user.getType())
				.setUserName(user.getUserName())
				.setImage(getImage(user.getImage()))
				.setDisplayName(AppUtil.getFullName(user))
				.setSystemUser(user.isSystemUser())
				.setHasPassword(!SystemSettingsUtil.getSetting("user.default.pass", "@@@@@@").equals(Crypt.decryptHexString(user.getPassword())));
	}

	private boolean sustemUserPasswordInvalid(final String password) {
		// password matched
		if (SystemSettingsUtil.getSetting("system.user.password", "${parentArtifactId}").equals(password)) {
			return false;
		}

		return true;
	}

	/**
	 *
	 * @param user BaseUser
	 * @return UserProfileFormBean
	 */
	@SuppressWarnings("rawtypes")
	private UserProfileFormBean toUserProfileFormBean(final BaseUser user) {
		return new UserProfileFormBean(toUserFormBean(user))
				.setEmail(user.getEmail())
				.setFullName(AppUtil.getFullName(user))
				.setFirstName(user.getFirstName())
				.setLastName(user.getLastName());
	}


	/**
	 *
	 * @param dao GenericDao
	 * @param firstName String
	 * @param lastName String
	 * @return String
	 */
	@SuppressWarnings("rawtypes")
	private String generateUserName(final UserDao dao, final String firstName, final String lastName) {
		final String userName = (firstName.toLowerCase() + lastName.toLowerCase()).replaceAll("${symbol_escape}${symbol_escape}s+", "");
		String newUserName = userName;
		int index = 0;
		while (userNameExist(dao, newUserName)) {
			index++;
			newUserName = userName + index;
		}
		return newUserName;
	}

	/**
	 *
	 * @param dao GenericDao
	 * @param newUserName String
	 * @return boolean
	 */
	@SuppressWarnings("rawtypes")
	private boolean userNameExist(final UserDao dao, final String newUserName) {
		return (null != dao.findUserByColumnName(BaseUser.CLM_USERNAME, newUserName));
	}

	/**
	 *
	 * @param dao GenericDao
	 * @return String
	 */
	@SuppressWarnings("rawtypes")
	private String generateConfirmationNumber(final UserDao dao) {
		final String confirmationNumber = RandomGenerator.rand14Char();
		String newConfirmationNumber = confirmationNumber;
		int index = 0;
		while (confirmationNumberExist(dao, newConfirmationNumber)) {
			index++;
			newConfirmationNumber = confirmationNumber + index;
		}
		return newConfirmationNumber;
	}

	/**
	 *
	 * @param dao GenericDao
	 * @param newValue String
	 * @return boolean
	 */
	@SuppressWarnings("rawtypes")
	private boolean confirmationNumberExist(final UserDao dao, final String newValue) {
		return (null != dao.findUserByColumnName(BaseUser.CLM_CONFIRMNUM, newValue));
	}

	/**
	 *
	 * @param dao GenericDao
	 * @return String
	 */
	@SuppressWarnings("rawtypes")
	private String generatePwdResetKey(final UserDao dao) {
		final String pwdResetKey = RandomGenerator.rand14Char() + "_" + RandomGenerator.rand8Char() + "_" + RandomGenerator.rand12Char();
		String newPwdResetKey = pwdResetKey;
		int index = 0;
		while (pwdResetKeyExist(dao, newPwdResetKey)) {
			index++;
			newPwdResetKey = pwdResetKey + index;
		}
		return newPwdResetKey;
	}

	/**
	 *
	 * @param dao GenericDao
	 * @param newValue String
	 * @return boolean
	 */
	@SuppressWarnings("rawtypes")
	private boolean pwdResetKeyExist(final UserDao dao, final String newValue) {
		return (null != dao.findUserByColumnName(BaseUser.CLM_CHGPWDKEY, newValue));
	}

	/**
	 *
	 * @param data UserProfileFormData
	 * @return UserProfileFormBean
	 * @throws AppException e
	 */
	@SuppressWarnings("rawtypes")
	public UserPhotoFormBean updateProfilePhoto(final UserPhotoFormData data) throws AppException {
		getLogger().debug("START: " + this.getClass().getCanonicalName() + "." + new Throwable().getStackTrace()[0].getMethodName());
		final UserDao<BaseUser> dao = new UserDao<>(BaseUser.class);
		try {
			final BaseUser user = dao.findById(data.getId());
			if (null == user) {
				throw new ComponentException(Messages.getString("Error.User.InvalidUser"),
						GlobalStatusCodes.BAD_REQUEST,
						ServiceErrorStatus.DATA_ERROR);
			}

			if (!AppUtil.isNullOrEmpty(data.getImage())) {
				user.setImage(newImage(data.getImage(), FileType.JPEG, dao));
			}

			dao.persist(user);
			dao.commit();

			return (UserPhotoFormBean) new UserPhotoFormBean()
					.setId(user.getId())
					.setImage(getImage(user.getImage()));
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
