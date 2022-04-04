#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.hibernate.beans.data.users;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Where;

import ${package}.hibernate.beans.base.BaseUser;
import ${package}.hibernate.beans.enumeration.UserType;

/**
 * @author ${author}
 *
 */
@Entity
@DiscriminatorValue(UserType.UserTypeConst.ROOT_VALUE)
public class Root extends BaseUser<Root> implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/***/
    @OneToMany(mappedBy = "m_admin", fetch = FetchType.LAZY)
    @Where(clause = "retired = false")
	private List<Member> m_members = new ArrayList<>();

	public Root() {
	}

	/**
	 * @return the members
	 */
	public List<Member> getMembers() {
		return m_members;
	}

	/**
	 * @param members the members to set
	 * @return Root
	 */
	public Root setMembers(final List<Member> members) {
		this.m_members = members;
		return this;
	}

}
