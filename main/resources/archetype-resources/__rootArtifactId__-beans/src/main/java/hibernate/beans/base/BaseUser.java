#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.hibernate.beans.base;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;

/**
 *
 * @author ${author}
 * @param <T>
 *
 */
@SuppressWarnings("rawtypes")
@Entity
@Table(
	name = "dp_user",
	indexes = {
		@Index(columnList = "type", name = "dp_user_type_idx"),
		@Index(columnList = "email", name = "dp_user_email_idx"),
		@Index(columnList = "confirmation_number", name = "dp_user_confirmation_number_idx"),
		@Index(columnList = "chg_pwd_key", name = "dp_user_chg_pwd_key_idx"),
	}
)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type",  discriminatorType = DiscriminatorType.STRING)
public class BaseUser<T extends BaseUser> extends BaseEntity<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/***/
	public static final String CLM_ID = "m_id";

	/***/
	@Id
	@GenericGenerator(
		name = "sequence_user_id",
		strategy = "${package}.hibernate.generators.UserIdGenerator"
	)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_user_id")
	@Column(name = "_id")
	private String m_id;

	/***/
	public static final String CLM_USERTYPE = "m_type";

	/***/
	@Column(name = "type", insertable = false, updatable = false)
    private String m_type;

	/***/
	public static final String CLM_USERNAME = "m_userName";

	/***/
	@NaturalId(mutable = false)
	@Column(name = "user_name", nullable = false)
	private String m_userName;

	/***/
	public static final String CLM_EMAIL = "m_email";

	/***/
	@Column(name = "email", unique = true)
	private String m_email;

	/***/
	public static final String CLM_IMAGE = "m_image";

	/***/
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "image", foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT))
	private BaseImage m_image;

	/***/
	public static final String CLM_CONFIRMNUM = "m_confirmationNumber";

	/***/
	@Column(name = "confirmation_number")
	private String m_confirmationNumber;

	/***/
	public static final String CLM_CONFIRMED = "m_confirmed";

	/***/
	@Column(name = "confirmed", columnDefinition = "boolean default false", updatable = true)
	private boolean m_confirmed;

	/***/
	public static final String CLM_CONFIRMEDDATE = "m_confirmedDate";

	/***/
	@Column(name = "confirmed_date", updatable = true)
	private Date m_confirmedDate;

	/***/
	public static final String CLM_PASSWORD = "m_password";

	/***/
	@Column(name = "password", nullable = false)
	private String m_password;

	/***/
	public static final String CLM_CHGPWDKEY = "m_chgPwdKey";

	/***/
	@Column(name = "chg_pwd_key", nullable = true)
	private String m_chgPwdKey;

	/***/
	public static final String CLM_CHGPWDEXPIRED = "m_chgPwdExpired";

	/***/
	@Column(name = "chg_pwd_exp_date", updatable = true, nullable = true)
	private Date m_chgPwdExpired;

	/***/
	public static final String CLM_FIRSTNAME = "m_firstName";

	/***/
	@Column(name = "first_name", nullable = false)
	private String m_firstName;

	/***/
	public static final String CLM_LASTNAME = "m_lastName";

	/***/
	@Column(name = "last_name", nullable = false)
	private String m_lastName;

	/***/
	public static final String CLM_SYSTEM_USER = "m_systemUser";

	/***/
	@Column(name = "system_user", columnDefinition = "boolean default false", nullable = false, updatable = false)
	private boolean m_systemUser;

	/***/
	protected BaseUser() {
		super();
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return m_id;
	}

	/**
	 * @param id the id to set
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public T setId(final String id) {
		this.m_id = id;
		return (T) this;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return m_type;
	}

	/**
	 * @param type the type to set
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public T setType(final String type) {
		this.m_type = type;
		return (T) this;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return m_userName;
	}

	/**
	 * @param userName the userName to set
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public T setUserName(final String userName) {
		this.m_userName = userName;
		return (T) this;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return m_email;
	}

	/**
	 * @param email the email to set
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public T setEmail(final String email) {
		this.m_email = email;
		return (T) this;
	}

	/**
	 * @return the image
	 */
	public BaseImage getImage() {
		return m_image;
	}

	/**
	 * @param image the image to set
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public T setImage(final BaseImage image) {
		this.m_image = image;
		return (T) this;
	}

	/**
	 * @return the confirmationNumber
	 */
	public String getConfirmationNumber() {
		return m_confirmationNumber;
	}

	/**
	 * @param confirmationNumber the confirmationNumber to set
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public T setConfirmationNumber(final String confirmationNumber) {
		this.m_confirmationNumber = confirmationNumber;
		return (T) this;
	}

	/**
	 * @return the confirmed
	 */
	public boolean isConfirmed() {
		return m_confirmed;
	}

	/**
	 * @param confirmed the confirmed to set
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public T setConfirmed(final boolean confirmed) {
		this.m_confirmed = confirmed;
		return (T) this;
	}

	/**
	 * @return the confirmedDate
	 */
	public Date getConfirmedDate() {
		return m_confirmedDate;
	}

	/**
	 * @param confirmedDate the confirmedDate to set
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public T setConfirmedDate(final Date confirmedDate) {
		this.m_confirmedDate = confirmedDate;
		return (T) this;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return m_password;
	}

	/**
	 * @param password the password to set
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public T setPassword(final String password) {
		this.m_password = password;
		return (T) this;
	}

	/**
	 * @return the chgPwdKey
	 */
	public String getChgPwdKey() {
		return m_chgPwdKey;
	}

	/**
	 * @param chgPwdKey the chgPwdKey to set
	 */
	public void setChgPwdKey(final String chgPwdKey) {
		this.m_chgPwdKey = chgPwdKey;
	}

	/**
	 * @return the chgPwdExpired
	 */
	public Date getChgPwdExpired() {
		return m_chgPwdExpired;
	}

	/**
	 * @param chgPwdExpired the chgPwdExpired to set
	 */
	public void setChgPwdExpired(final Date chgPwdExpired) {
		this.m_chgPwdExpired = chgPwdExpired;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return m_firstName;
	}

	/**
	 * @param firstName the firstName to set
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public T setFirstName(final String firstName) {
		this.m_firstName = firstName;
		return (T) this;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return m_lastName;
	}

	/**
	 * @param lastName the lastName to set
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public T setLastName(final String lastName) {
		this.m_lastName = lastName;
		return (T) this;
	}

	/**
	 * @return the systemUser
	 */
	public boolean isSystemUser() {
		return m_systemUser;
	}

	/**
	 * @param systemUser the systemUser to set
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public T setSystemUser(final boolean systemUser) {
		this.m_systemUser = systemUser;
		return (T) this;
	}
}
