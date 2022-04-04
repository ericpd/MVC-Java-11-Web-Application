#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.hibernate.beans.data.users;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import ${package}.hibernate.beans.base.BaseUser;
import ${package}.hibernate.beans.enumeration.UserType;

/**
 * @author ${author}
 *
 */
@Entity
@DiscriminatorValue(UserType.UserTypeConst.ADMIN_VALUE)
public class Admin extends BaseUser<Admin> implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

}
