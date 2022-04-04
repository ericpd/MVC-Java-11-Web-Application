#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.hibernate.beans.data.users;

import java.io.Serializable;

import javax.persistence.ConstraintMode;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ${package}.hibernate.beans.base.BaseUser;
import ${package}.hibernate.beans.enumeration.UserType;

/**
 * @author ${author}
 *
 */
@Entity
@DiscriminatorValue(UserType.UserTypeConst.MEMBER_VALUE)
public class Member extends BaseUser<Member> implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/***/
	public static final String CLM_ROOT = "m_admin";

	/***/
	@ManyToOne(optional = true)
	@JoinColumn(name = "admin_id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT), nullable = true)
	private Root m_admin;

	public Member() {
	}

	/**
	 * @return the admin
	 */
	public Root getAdmin() {
		return m_admin;
	}

	/**
	 * @param admin the admin to set
	 * @return Member
	 */
	public Member setAdmin(final Root admin) {
		this.m_admin = admin;
		return this;
	}

}
