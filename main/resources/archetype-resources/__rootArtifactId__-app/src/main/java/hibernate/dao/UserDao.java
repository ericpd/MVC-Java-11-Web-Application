#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.hibernate.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.query.Query;

import ${package}.hibernate.beans.base.BaseEntity;
import ${package}.hibernate.beans.base.BaseUser;

/**
 * @author ${author}
 * @param <T>
 *
 */
@SuppressWarnings("rawtypes")
public class UserDao<T extends BaseUser> extends GenericDao<T> {

	/**
	 *
	 * @param entityClass Class of T
	 */
	public UserDao(final Class<T> entityClass) {
		this(entityClass, null);
	}

	/**
	 * @param entityClass Class of T
	 * @param dao GenericDao
	 */
	public UserDao(final Class<T> entityClass,
				   final GenericDao dao) {
		super(UserDao.class, entityClass, dao);
	}

	/**
	 *
	 * @param column String
	 * @param value String
	 * @return Object
	 */
	public T findUserByColumnName(final String column, final String value) {
		return findUserByColumnName(column, value, true, false);
	}

	/**
	 *
	 * @param column String
	 * @param value String
	 * @param keySensitive boolean
	 * @return Object
	 */
	public T findUserByColumnName(final String column, final String value, final boolean keySensitive) {
		return findUserByColumnName(column, value, keySensitive, false);
	}

	/**
	 *
	 * @param column String
	 * @param value String
	 * @param keySensitive boolean
	 * @param includeRetired boolean
	 * @return Object
	 */
	@SuppressWarnings({ "unchecked" })
	public T findUserByColumnName(final String column, final String value, final boolean keySensitive, final boolean includeRetired) {
		final CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
		final CriteriaQuery query = builder.createQuery(getEntityClass());
        final Root root = query.from(getEntityClass());
        query.select(root);
        if (includeRetired) { // remove retired filter when addRetire is true
            query.where(builder.equal(root.get(column), value));
        } else {
        	Predicate predicate = builder.equal(root.get(column), value);
        	if (!keySensitive) {
        		predicate = builder.like(builder.upper(root.get(column)), value.toUpperCase());
        	}

            query.where(builder.and(
            			 builder.isFalse(root.get(BaseEntity.CLM_RETIRED)),
            			 predicate
       	 		 ));
        }
        final Query q = getCurrentSession().createQuery(query);
        final List<T> results = q.getResultList();
        if (null != results && !results.isEmpty()) {
        	return results.get(0);
        }
        return null;
	}



}
